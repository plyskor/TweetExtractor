/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.SetServerTaskReadyResponse;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService
public interface SetServerTaskReadySei {
	public SetServerTaskReadyResponse setServerTaskReady(int id);
}
