/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import java.util.List;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTweetVolumeNLPReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTweetVolumeByNERTopicsReportSei;

/**
 * @author jgarciadelsaz
 *
 */
public class CreateServerTaskTweetVolumeByNERTopicsReport extends TweetExtractorCXFService implements CreateServerTaskTweetVolumeByNERTopicsReportSei {
	private CreateServerTaskTweetVolumeByNERTopicsReportSei client ;
	public CreateServerTaskTweetVolumeByNERTopicsReport(String endpoint) {
		super(endpoint);
		factory.setServiceClass(CreateServerTaskTweetVolumeByNERTopicsReportSei.class); 
		factory.setAddress(endpoint+Constants.CREATE_SERVER_TASK_TWEET_VOLUME_BY_NER_TOPICS_REPORT);
		client = (CreateServerTaskTweetVolumeByNERTopicsReportSei) factory.create(); 
	}

	@Override
	public CreateServerTaskTweetVolumeNLPReportResponse createServerTaskTweetVolumeByNERTopicsReport(
			int userId, List<Integer> extractions, int languageID, String preferencesName) {
		if(client!=null) {
			return client.createServerTaskTweetVolumeByNERTopicsReport(userId, extractions, languageID, preferencesName);
		}
		return null;
	}

}
