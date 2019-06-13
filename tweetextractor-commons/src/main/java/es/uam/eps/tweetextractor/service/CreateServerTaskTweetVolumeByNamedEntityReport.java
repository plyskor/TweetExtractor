/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import java.util.List;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTweetVolumeByNamedEntityReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTweetVolumeByNamedEntityReportSei;

/**
 * @author jgarciadelsaz
 *
 */
public class CreateServerTaskTweetVolumeByNamedEntityReport extends TweetExtractorCXFService implements CreateServerTaskTweetVolumeByNamedEntityReportSei {
	private CreateServerTaskTweetVolumeByNamedEntityReportSei client ;
	public CreateServerTaskTweetVolumeByNamedEntityReport(String endpoint) {
		super(endpoint);
		factory.setServiceClass(CreateServerTaskTweetVolumeByNamedEntityReportSei.class); 
		factory.setAddress(endpoint+Constants.CREATE_SERVER_TASK_TWEET_VOLUME_BY_NAMED_ENTITY_REPORT);
		client = (CreateServerTaskTweetVolumeByNamedEntityReportSei) factory.create(); 
	}

	@Override
	public CreateServerTaskTweetVolumeByNamedEntityReportResponse createServerTaskTweetVolumeByNamedEntityReport(
			int userId, List<Integer> extractions, int languageID, String preferencesName) {
		if(client!=null) {
			return client.createServerTaskTweetVolumeByNamedEntityReport(userId, extractions, languageID, preferencesName);
		}
		return null;
	}

}
