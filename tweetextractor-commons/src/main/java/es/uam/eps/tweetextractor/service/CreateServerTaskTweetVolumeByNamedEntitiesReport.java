/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import java.util.List;

import org.postgresql.util.LruCache.CreateAction;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTweetVolumeNLPReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTweetVolumeByNERTopicsReportSei;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTweetVolumeByNamedEntitiesReportSei;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public class CreateServerTaskTweetVolumeByNamedEntitiesReport extends TweetExtractorCXFService
		implements CreateServerTaskTweetVolumeByNamedEntitiesReportSei{
	private CreateServerTaskTweetVolumeByNamedEntitiesReportSei client;
	/**
	 * @param endpoint
	 */
	public CreateServerTaskTweetVolumeByNamedEntitiesReport(String endpoint) {
		super(endpoint);
		factory.setServiceClass(CreateServerTaskTweetVolumeByNamedEntitiesReportSei.class); 
		factory.setAddress(endpoint+Constants.CREATE_SERVER_TASK_TWEET_VOLUME_BY_NAMED_ENTITY_REPORT);
		client = (CreateServerTaskTweetVolumeByNamedEntitiesReportSei) factory.create(); 
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTweetVolumeByNamedEntitiesReportSei#createServerTaskTweetVolumeByNamedEntitiesReport(int, java.util.List, int, java.lang.String)
	 */
	@Override
	public CreateServerTaskTweetVolumeNLPReportResponse createServerTaskTweetVolumeByNamedEntitiesReport(int userId,
			List<Integer> extractions, int languageID, String preferencesName) {
		return client.createServerTaskTweetVolumeByNamedEntitiesReport(userId, extractions, languageID, preferencesName);
	}

}
