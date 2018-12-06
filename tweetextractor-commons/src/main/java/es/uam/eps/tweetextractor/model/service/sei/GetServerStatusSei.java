/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService
public interface GetServerStatusSei {
	public boolean getServerStatus();
}
