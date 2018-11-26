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
public void persist(T entity);
	
	public void update(T entity);
	
	public T findById(Id id);
		
	public void delete(T entity);
	
	public List<T> findAll();
	
	public void deleteAll();
	
}
