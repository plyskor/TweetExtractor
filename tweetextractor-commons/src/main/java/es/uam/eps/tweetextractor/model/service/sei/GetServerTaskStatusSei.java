/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.GetServerTaskStatusResponse;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService
public interface GetServerTaskStatusSei {
	public GetServerTaskStatusResponse getServerTaskStatus(int id);
}
