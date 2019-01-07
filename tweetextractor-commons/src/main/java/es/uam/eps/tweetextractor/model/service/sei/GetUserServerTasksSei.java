/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.GetUserServerTasksResponse;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService
public interface GetUserServerTasksSei {
	public GetUserServerTasksResponse getUserServerTasks(int id);
}
