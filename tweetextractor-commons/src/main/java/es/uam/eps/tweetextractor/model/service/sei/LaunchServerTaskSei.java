/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.LaunchServerTaskResponse;

/**
 * @author jgarciadelsaz
 *
 */
@WebService
public interface LaunchServerTaskSei {
	public LaunchServerTaskResponse launchServerTask(int id) ;
}
