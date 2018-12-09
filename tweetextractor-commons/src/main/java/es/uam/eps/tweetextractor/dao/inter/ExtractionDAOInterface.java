/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.io.Serializable;
import java.util.List;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface ExtractionDAOInterface <T,Id extends Serializable>{
public void persist(T entity);
	
	public void update(T entity);
	
	public T findById(Id id);
		
	public void delete(T entity);
	
	public List<T> findAll();
	
	public void deleteAll();
	
	public void refresh(Extraction entity);
	
	public Extraction merge (Extraction extraction);
	
	public List<Extraction> findByUser(User user);
	
	
}