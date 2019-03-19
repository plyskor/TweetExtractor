/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import java.util.Date;
import javax.jws.WebService;
import es.uam.eps.tweetextractor.model.service.ScheduleServerTaskResponse;

/**
 * @author jose
 *
 */
@WebService
public interface ScheduleServerTaskSei {
	public ScheduleServerTaskResponse scheduleServerTask(int taskId, Date date);
	
}
