/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportRegisterServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.analytics.nlp.TweetExtractorNaturalTextProcessor;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingWordsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingWordsReportRegister;
import es.uam.eps.tweetextractorserver.model.servertask.AnalyticsServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractorserver.model.servertask.response.TrendsReportResponse;

/**
 * @author jose
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = TaskTypes.Values.TYPE_TASK_TRENDS_REPORT)
@XmlRootElement(name = "serverTaskTrendsReport")
public class ServerTaskTrendsReport extends AnalyticsServerTask {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 5380977367340886500L;
	
	/**
	 * 
	 */
	public ServerTaskTrendsReport() {
		super();
		setLogger(LoggerFactory.getLogger(ServerTaskTrendsReport.class));
	}
	/**
	 * @param id
	 * @param user
	 */
	public ServerTaskTrendsReport(int id, User user) {
		super(id, user);
		setLogger(LoggerFactory.getLogger(ServerTaskTrendsReport.class));
	}
	@Override
	public ServerTaskResponse call() {
		return new TrendsReportResponse(super.call());
	}
	@Override
	public void initialize(AnnotationConfigApplicationContext context) {
		this.springContext=context;
		tServ=springContext.getBean(TweetServiceInterface.class);
		arServ=springContext.getBean(AnalyticsReportServiceInterface.class);
		regServ=springContext.getBean(AnalyticsReportRegisterServiceInterface.class);
		eServ=springContext.getBean(ExtractionServiceInterface.class);
	}
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorserver.model.servertask.ServerTask#implementation()
	 */
	@Override
	public void implementation () throws Exception {
		getLogger().info("Generating trends report...");
		TrendsReport trendsReport = (TrendsReport) getReport();
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
			getLogger().info("Server task "+this.getId()+" has generated an empty trending report. It hasn't been saved.");
			finish();
			return;
		}
		report.setLastUpdatedDate(new Date());
		arServ.update(trendsReport);
		getLogger().info("Trending "+word+" report succesfully saved to database with id: "+report.getId());
		finish();
	}

}
