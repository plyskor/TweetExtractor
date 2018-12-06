/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.InterruptServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.InterruptServerTaskSei;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class InterruptServerTask extends TweetExtractorCXFService implements InterruptServerTaskSei{
	private InterruptServerTaskSei client ;
	/**
	 * 
	 */
	public InterruptServerTask(String endpoint) {
		super(endpoint);
		factory.setServiceClass(InterruptServerTaskSei.class); 
		factory.setAddress(endpoint+Constants.INTERRUPT_SERVER_TASK_ENDPOINT);
		client= (InterruptServerTaskSei) factory.create(); 
	}
	@Override
	public InterruptServerTaskResponse interruptServerTask(int id) {
		if(client!=null) {
			InterruptServerTaskResponse reply = client.interruptServerTask(id);
			return reply;
		}
		return null;
	}
	

}
