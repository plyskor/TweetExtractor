/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTrendsReportResponse;
import es.uam.eps.tweetextractor.service.CreateServerTaskTrendsReport;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;

/**
 * @author jose
 *
 */
public class CreateTrendsReportTask extends TwitterExtractorFXTask<CreateServerTaskTrendsReportResponse> {
	private int userId;
	private AnalyticsReportTypes reportType;
	private int limit;
	private List<Integer>extractions;
	private List<String> filter;
	public CreateTrendsReportTask(AnnotationConfigApplicationContext springContext) {
		super(springContext);
	}

	/**
	 * @param springContext
	 * @param userId
	 * @param reportType
	 * @param limit
	 * @param extractions
	 * @param filter
	 */
	public CreateTrendsReportTask(AnnotationConfigApplicationContext springContext, int userId,
			AnalyticsReportTypes reportType, int limit, List<Integer> extractions, List<String> filter) {
		super(springContext);
		this.userId = userId;
		this.reportType = reportType;
		this.limit = limit;
		this.extractions = extractions;
		this.filter = filter;
	}

	@Override
	protected CreateServerTaskTrendsReportResponse call() throws Exception {
		if(reportType==null||extractions==null||filter==null) {
			return null;
		}
		CreateServerTaskTrendsReport service = new CreateServerTaskTrendsReport(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		return service.createServerTaskTrendsReport(userId, reportType, limit, extractions, filter);
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the reportType
	 */
	public AnalyticsReportTypes getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(AnalyticsReportTypes reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the extractions
	 */
	public List<Integer> getExtractions() {
		return extractions;
	}

	/**
	 * @param extractions the extractions to set
	 */
	public void setExtractions(List<Integer> extractions) {
		this.extractions = extractions;
	}

	/**
	 * @return the filter
	 */
	public List<String> getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(List<String> filter) {
		this.filter = filter;
	}

}
