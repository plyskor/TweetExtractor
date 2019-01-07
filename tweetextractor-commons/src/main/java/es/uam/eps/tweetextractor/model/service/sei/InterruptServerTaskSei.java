/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.InterruptServerTaskResponse;


/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService
public interface InterruptServerTaskSei {
	public InterruptServerTaskResponse interruptServerTask(int id);
}
