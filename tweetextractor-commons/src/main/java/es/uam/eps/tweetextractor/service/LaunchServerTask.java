/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.LaunchServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.LaunchServerTaskSei;

/**
 * @author jgarciadelsaz
 *
 */
public class LaunchServerTask extends TweetExtractorCXFService implements LaunchServerTaskSei{
	private LaunchServerTaskSei client ;
	
	/**
	 * @param endpoint the server endpoint to set
	 */
	public LaunchServerTask(String endpoint) {
		super(endpoint);
		factory.setServiceClass(LaunchServerTaskSei.class); 
		factory.setAddress(endpoint+Constants.LAUNCH_SERVER_TASK_ENDPOINT);
		client= (LaunchServerTaskSei) factory.create(); 
	}

	@Override
	public LaunchServerTaskResponse launchServerTask(int id) {
		if (client!=null) {
			return client.launchServerTask(id);
		}
		return null;
	}
	

}
