/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface UserDAOInterface <T,Id extends Serializable>{
	
	public T findById(Id id);
			
	public List<T> findAll();
	
	public void deleteAll();
	
}
