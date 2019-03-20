/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;

/**
 * @author jose
 *
 */
public interface ServerTaskServiceInterface extends GenericServiceInterface<ServerTask, Integer>{
	public boolean hasAnyServerTask(User user);
	public List<ServerTask> findByUser(User user);
}
