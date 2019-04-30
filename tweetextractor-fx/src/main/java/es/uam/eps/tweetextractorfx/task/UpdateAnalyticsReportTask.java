/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportRegisterServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.analytics.nlp.TweetExtractorNaturalTextProcessor;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineTopNHashtagsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineVolumeReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingWordsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingWordsReportRegister;


/**
 * @author jgarciadelsaz
 *
 */
public class UpdateAnalyticsReportTask extends TwitterExtractorFXTask<String> {

	private AnalyticsCategoryReport report;

	private AnalyticsReportRegisterServiceInterface regServ;

	private AnalyticsReportServiceInterface arServ;

	private TweetServiceInterface tServ;

	private ExtractionServiceInterface eServ;
	private Logger logger = LoggerFactory.getLogger(UpdateAnalyticsReportTask.class);
	/**
	 * @param springContext
	 * @param report
	 */
	public UpdateAnalyticsReportTask(AnnotationConfigApplicationContext springContext, AnalyticsCategoryReport report) {
		super(springContext);
		this.report = report;
		regServ=this.springContext.getBean(AnalyticsReportRegisterServiceInterface.class);
		arServ=this.springContext.getBean(AnalyticsReportServiceInterface.class);
		tServ=this.springContext.getBean(TweetServiceInterface.class);
		eServ=this.springContext.getBean(ExtractionServiceInterface.class);
	}
	public UpdateAnalyticsReportTask(AnnotationConfigApplicationContext springContext) {
		super(springContext);
		regServ=this.springContext.getBean(AnalyticsReportRegisterServiceInterface.class);
		arServ=this.springContext.getBean(AnalyticsReportServiceInterface.class);
		tServ=this.springContext.getBean(TweetServiceInterface.class);
		eServ=this.springContext.getBean(ExtractionServiceInterface.class);
	}
	@Override
	protected String call() throws Exception {
		if(report==null) {
			return "Report is null";
		}
		initializeReport();
		switch (report.reportType) {
		case TVR:
			return updateTimelineVolumeReport();
		case TTNHR:
			return updateTopNHashtagsReport();
		case TRHR:
		case TRUR:
		case TRUMR:
		case TRWR:
			return updateTrendsReport();
		default:
			break;
		}
		return "Report type is unknown";
	}
	private void initializeReport() {
		if(this.report!=null) {
			arServ.refresh(report);
		}
	}

	private void permanentClearReport() {
		if (report != null) {
			for (AnalyticsReportCategory category : ((AnalyticsCategoryReport) report).getCategories()) {
				for (AnalyticsReportRegister register : category.getResult()) {
					regServ.delete(register);
				}
				category.getResult().clear();
			}
			arServ.saveOrUpdate(report);
		}
	}
	private String updateTimelineVolumeReport() {
		logger.info("Generating timeline tweet volume report...");
		List<TimelineReportVolumeRegister> list=tServ.extractGlobalTimelineVolumeReport(this.report.getUser());
		if(list==null) {
			logger.warn("There was an error querying the database while generating the report.");
			return "ERROR";
		}
		if (list.isEmpty()) {
			logger.info("An empty timeline volume report has been generated. It hasn't been saved.");
			return "ERROR";
		}
		permanentClearReport();
		AnalyticsReportCategory nTweetsCategory= ((TimelineVolumeReport)report).getCategories().get(0);
		for(TimelineReportVolumeRegister register: list) {
			register.setCategory(nTweetsCategory);
		}
		nTweetsCategory.getResult().clear();
		nTweetsCategory.getResult().addAll(list);
		report.setLastUpdatedDate(new Date());
		arServ.saveOrUpdate((AnalyticsCategoryReport)report);
		logger.info("Timeline tweet volume report succesfully saved to database with id: "+report.getId());
		return "SUCCESS";
	}
	private String updateTrendsReport() throws Exception {
		logger.info("Generating trends report...");
		TrendsReport trendsReport = (TrendsReport) report;
		trendsReport.setExtractions(eServ.findListByReport(trendsReport));
		trendsReport.setStringFilterList(arServ.findStringFilterListByReport(trendsReport));
		List<Integer> extractionIDList= new ArrayList<>();
		for(Extraction e : trendsReport.getExtractions()) {
			extractionIDList.add(e.getIdDB());
		}
		boolean emptyReport=true;
		String word="";
		permanentClearReport();
		switch (trendsReport.getReportType()) {
		case TRHR:
			word ="hashtags";
			List<TrendingReportRegister> listHashtags = (trendsReport.getStringFilterList().isEmpty()) ? tServ.findTopNHashtagsByExtraction(trendsReport.getN(), extractionIDList):tServ.findTopNHashtagsByExtractionFiltered(trendsReport.getN(), trendsReport.getStringFilterList(), extractionIDList);
			if(!listHashtags.isEmpty()) {
				emptyReport=false;
			}
			for (TrendingReportRegister register : listHashtags) {
				register.setCategory(trendsReport.getCategories().get(0));
				trendsReport.getCategories().get(0).getResult().add(register);
			}
			break;
		case TRUR:
			word ="users";
			List<TrendingReportRegister> listUsers = (trendsReport.getStringFilterList().isEmpty()) ? tServ.findTopNUsersByExtraction(trendsReport.getN(), extractionIDList):tServ.findTopNUsersByExtractionFiltered(trendsReport.getN(), trendsReport.getStringFilterList(), extractionIDList);
			if(!listUsers.isEmpty()) {
				emptyReport=false;
			}
			for (TrendingReportRegister register : listUsers) {
				register.setCategory(trendsReport.getCategories().get(0));
				trendsReport.getCategories().get(0).getResult().add(register);
			}
			break;
		case TRUMR:
			word ="user mentions";
			List<TrendingReportRegister> listUserMentions = (trendsReport.getStringFilterList().isEmpty()) ? tServ.findTopNUserMentionsByExtraction(trendsReport.getN(), extractionIDList):tServ.findTopNUserMentionsByExtractionFiltered(trendsReport.getN(), trendsReport.getStringFilterList(), extractionIDList);
			if(!listUserMentions.isEmpty()) {
				emptyReport=false;
			}
			for (TrendingReportRegister register : listUserMentions) {
				register.setCategory(trendsReport.getCategories().get(0));
				trendsReport.getCategories().get(0).getResult().add(register);
			}
			break;
		case TRWR:
			word ="words";
			TweetExtractorNaturalTextProcessor textProcessor = new TweetExtractorNaturalTextProcessor(springContext);
			List<TrendingWordsReportRegister> listWords = textProcessor.createWordFrequencyReport((TrendingWordsReport) report);
			if(!listWords.isEmpty()) {
				emptyReport=false;
			}
			for(TrendingWordsReportRegister register : listWords) {
				register.setCategory(trendsReport.getCategories().get(0));
				trendsReport.getCategories().get(0).getResult().add(register);
			}
			break;
		default:
			break;
		}
		if (emptyReport) {
			logger.info("An empty trending report has been generated. It hasn't been saved.");
			return "An empty trending report has been generated. It hasn't been saved.";
		}
		report.setLastUpdatedDate(new Date());
		arServ.update(trendsReport);
		logger.info("Trending "+word+" report succesfully saved to database with id: "+report.getId());
		return "SUCCESS";
	}
	private String updateTopNHashtagsReport() {
		logger.info("Generating timeline Top "+((TimelineTopNHashtagsReport)report).getnHashtags()+" hashtags volume report...");
		TimelineTopNHashtagsReport castedReport = (TimelineTopNHashtagsReport) report;
		boolean emptyReport=true;
		permanentClearReport();
		for(AnalyticsReportCategory category: castedReport.getCategories()) {
			List<TimelineReportVolumeRegister> list = tServ.extractHashtagTimelineVolumeReport(report.getUser(), category.getCategoryName());
			if(list==null) {
				String msg = "There was an error querying the database while generating the report.";
				logger.warn(msg);
				return msg;
			}
			if(!list.isEmpty()){
				emptyReport=false;
			}
			category.getResult().clear();
			for(TimelineReportVolumeRegister register: list) {
				register.setCategory(category);
				category.getResult().add(register);
			}
		}
		if (emptyReport) {
			String msg = "An empty timeline volume report. It hasn't been saved.";
			logger.info(msg);
			return msg;
		}
		report.setLastUpdatedDate(new Date());
		arServ.update(castedReport);
		logger.info("Timeline Top "+castedReport.getnHashtags()+" hashtags report succesfully saved to database with id: "+report.getId());
		return "SUCCESS";
	}
}
