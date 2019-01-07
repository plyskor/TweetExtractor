/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface CredentialsDAOInterface <T>{
		
	public List<T> findByUser(User user);
	
}
