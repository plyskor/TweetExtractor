/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskUpdateExtractionIndefResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskUpdateExtractionIndefSei;

/**
 * @author jgarciadelsaz
 *
 */
public class CreateServerTaskUpdateExtractionIndef extends TweetExtractorCXFService implements CreateServerTaskUpdateExtractionIndefSei {
	private CreateServerTaskUpdateExtractionIndefSei client ;
	/**
	 * @param endpoint the server endpoint to set
	 */
	public CreateServerTaskUpdateExtractionIndef(String endpoint) {
		super(endpoint);
		factory.setServiceClass(CreateServerTaskUpdateExtractionIndefSei.class); 
		factory.setAddress(endpoint+Constants.CREATE_UPDATE_EXTRACTION_INDEF_SERVER_TASK_ENDPOINT);
		client = (CreateServerTaskUpdateExtractionIndefSei) factory.create(); 
	}

	@Override
	public CreateServerTaskUpdateExtractionIndefResponse createServerTaskUpdateExtractionIndef(int id) {
		if(client!=null) {
			return client.createServerTaskUpdateExtractionIndef(id);
		}
		return null;
	}

}
