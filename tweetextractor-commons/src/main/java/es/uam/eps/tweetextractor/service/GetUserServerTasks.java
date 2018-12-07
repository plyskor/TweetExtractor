/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.GetUserServerTasksResponse;
import es.uam.eps.tweetextractor.model.service.sei.GetUserServerTasksSei;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class GetUserServerTasks extends TweetExtractorCXFService implements GetUserServerTasksSei{
	private GetUserServerTasksSei client ;

	/**
	 * 
	 */
	public GetUserServerTasks(String endpoint) {
		super(endpoint);
		factory.setServiceClass(GetUserServerTasksSei.class); 
		factory.setAddress(endpoint+Constants.GET_USER_SERVER_TASKS_ENDPOINT);
		client= (GetUserServerTasksSei) factory.create(); 
	}

	@Override
	public GetUserServerTasksResponse getUserServerTasks(int id) {
		if(client!=null) {
			return client.getUserServerTasks(id);
		}
		return null;
	}

}
