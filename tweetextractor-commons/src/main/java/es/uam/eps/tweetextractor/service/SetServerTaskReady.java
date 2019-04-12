/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.SetServerTaskReadyResponse;
import es.uam.eps.tweetextractor.model.service.sei.SetServerTaskReadySei;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class SetServerTaskReady extends TweetExtractorCXFService implements SetServerTaskReadySei {
	private SetServerTaskReadySei client;
	/**
	 * @param endpoint the server endpoint to set
	 */
	public SetServerTaskReady(String endpoint) {
		super(endpoint);
		factory.setServiceClass(SetServerTaskReadySei.class); 
		factory.setAddress(endpoint+Constants.SET_SERVER_TASK_READY_ENDPOINT);
		client= (SetServerTaskReadySei) factory.create(); 
	}

	@Override
	public SetServerTaskReadyResponse setServerTaskReady(int id) {
		if(client!=null) {
			return client.setServerTaskReady(id);
		}
		return null;
	}

}
