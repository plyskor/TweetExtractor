/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTopNHashtagsReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTimelineTopNHashtagsReportSei;

/**
 * @author jose
 *
 */
public class CreateServerTaskTimelineTopNHashtagsReport extends TweetExtractorCXFService implements CreateServerTaskTimelineTopNHashtagsReportSei {
	public CreateServerTaskTimelineTopNHashtagsReport(String endpoint) {
		super(endpoint);
		factory.setServiceClass(CreateServerTaskTimelineTopNHashtagsReportSei.class); 
		factory.setAddress(endpoint+Constants.CREATE_TIMELINE_TOP_N_HASHTAGS_REPORT_SERVER_TASK_ENDPOINT);
		client = (CreateServerTaskTimelineTopNHashtagsReportSei) factory.create(); 
	}

	private CreateServerTaskTimelineTopNHashtagsReportSei client ;

	@Override
	public CreateServerTaskTopNHashtagsReportResponse createServerTaskTopNHashtagsReport(int nHashtags, int userId) {
		if(client!=null) {
			return client.createServerTaskTopNHashtagsReport(nHashtags, userId);
		}
		return null;
	}

}
