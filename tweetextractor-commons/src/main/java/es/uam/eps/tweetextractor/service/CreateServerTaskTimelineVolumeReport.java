/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTimelineVolumeReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTimelineVolumeReportSei;

/**
 * @author jose
 *
 */
public class CreateServerTaskTimelineVolumeReport extends TweetExtractorCXFService implements CreateServerTaskTimelineVolumeReportSei {
	private CreateServerTaskTimelineVolumeReportSei client ;
	/**
	 * @param endpoint the server endpoint to set
	 */
	public CreateServerTaskTimelineVolumeReport(String endpoint) {
		super(endpoint);
		factory.setServiceClass(CreateServerTaskTimelineVolumeReportSei.class); 
		factory.setAddress(endpoint+Constants.CREATE_TIMELINE_VOLUME_REPORT_SERVER_TASK_ENDPOINT);
		client = (CreateServerTaskTimelineVolumeReportSei) factory.create(); 
	}

	@Override
	public CreateServerTaskTimelineVolumeReportResponse createServerTaskTimelineVolumeReport(int userId) {
		if(client!=null) {
			return client.createServerTaskTimelineVolumeReport(userId);
		}
		return null;
	}

}
