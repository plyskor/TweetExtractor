/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.DeleteServerTaskResponse;

/**
 * @author jgarciadelsaz
 *
 */
@WebService
public interface DeleteServerTaskSei {
	public DeleteServerTaskResponse deleteServerTask(int id);
}
