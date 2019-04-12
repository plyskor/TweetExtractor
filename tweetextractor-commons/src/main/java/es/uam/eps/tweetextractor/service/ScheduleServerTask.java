/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import java.util.Date;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.ScheduleServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.ScheduleServerTaskSei;

/**
 * @author jose
 *
 */
public class ScheduleServerTask extends TweetExtractorCXFService implements ScheduleServerTaskSei{
	private ScheduleServerTaskSei client;

	/**
	 * @param endpoint the server endpoint to set
	 */
	public ScheduleServerTask(String endpoint) {
		super(endpoint);
		factory.setServiceClass(ScheduleServerTaskSei.class); 
		factory.setAddress(endpoint+Constants.SCHEDULE_SERVER_TASK_ENDPOINT);
		client= (ScheduleServerTaskSei) factory.create(); 	}

	@Override
	public ScheduleServerTaskResponse scheduleServerTask(int taskId, Date date) {
		if(client!=null) {
			return client.scheduleServerTask(taskId, date);
		}
		return null;
	}

}
