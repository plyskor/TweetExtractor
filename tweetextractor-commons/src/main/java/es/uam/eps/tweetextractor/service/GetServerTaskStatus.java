/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.GetServerTaskStatusResponse;
import es.uam.eps.tweetextractor.model.service.sei.GetServerTaskStatusSei;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class GetServerTaskStatus extends TweetExtractorCXFService implements GetServerTaskStatusSei{
	private GetServerTaskStatusSei client ;
	/**
	 * 
	 */
	public GetServerTaskStatus(String endpoint) {
		super(endpoint);
		factory.setServiceClass(GetServerTaskStatusSei.class); 
		factory.setAddress(endpoint+Constants.GET_SERVER_TASK_STATUS_ENDPOINT);
		client= (GetServerTaskStatusSei) factory.create(); 
	}

	public GetServerTaskStatusResponse getServerTaskStatus(int id) {
		if(client !=null) {
			GetServerTaskStatusResponse reply = client.getServerTaskStatus(id); 
			return reply;
		}
		return null;	
	}

}
