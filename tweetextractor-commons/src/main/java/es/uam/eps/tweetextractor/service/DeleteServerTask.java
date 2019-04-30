/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.DeleteServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.DeleteServerTaskSei;

/**
 * @author jgarciadelsaz
 *
 */
public class DeleteServerTask extends TweetExtractorCXFService implements DeleteServerTaskSei {
	private DeleteServerTaskSei client;
	
	/**
	 * @param endpoint the server endpoint to set
	 */
	public DeleteServerTask(String endpoint) {
		super(endpoint);
		factory.setServiceClass(DeleteServerTaskSei.class); 
		factory.setAddress(endpoint+Constants.DELETE_SERVER_TASK_ENDPOINT);
		client= (DeleteServerTaskSei) factory.create(); 
	}

	@Override
	public DeleteServerTaskResponse deleteServerTask(int id) {
		if(client!=null) {
			return client.deleteServerTask(id);
		}
		return null;
	}

}
