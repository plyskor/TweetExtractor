/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.io.Serializable;
import java.util.List;

import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface CredentialsDAOInterface <T,Id extends Serializable>{
public void persist(T entity);
	
	public void update(T entity);
	
	public T findById(Id id);
		
	public void delete(T entity);
	
	public List<T> findAll();
	
	public void deleteAll();
	
	public List<Credentials> findByUser(User user);
	
	
}
