/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author jose
 *
 */
public interface CredentialsServiceInterface extends GenericServiceInterface<Credentials, Integer> {
	public boolean hasAnyCredentials(User user);
	public List<Credentials> findByUser(User user);
}
