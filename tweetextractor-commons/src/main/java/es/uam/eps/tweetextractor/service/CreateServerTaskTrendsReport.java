/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import java.util.List;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTrendsReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTimelineVolumeReportSei;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTrendsReportSei;

/**
 * @author jose
 *
 */
public class CreateServerTaskTrendsReport extends TweetExtractorCXFService
		implements CreateServerTaskTrendsReportSei {
	private CreateServerTaskTrendsReportSei client ;

	public CreateServerTaskTrendsReport(String endpoint) {
		super(endpoint);
		factory.setServiceClass(CreateServerTaskTrendsReportSei.class); 
		factory.setAddress(endpoint+Constants.CREATE_TRENDS_REPORT_SERVER_TASK_ENDPOINT);
		client = (CreateServerTaskTrendsReportSei) factory.create(); 
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTrendsReportSei#createServerTaskTrendsReport(int, es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes, java.util.List)
	 */
	@Override
	public CreateServerTaskTrendsReportResponse createServerTaskTrendsReport(int userId,
			AnalyticsReportTypes reportType,int limit, List<Integer> extractions,List<String> filter) {
		if(client!=null) {
			return client.createServerTaskTrendsReport(userId, reportType,limit, extractions,filter);
		}
		return null;
	}

}
