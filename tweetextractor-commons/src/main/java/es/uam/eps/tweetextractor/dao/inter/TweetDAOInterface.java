/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.io.Serializable;
import java.util.List;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface TweetDAOInterface <T,Id extends Serializable>{
public void persist(T entity);
	
	public void update(T entity);
	
	public T findById(Id id);
		
	public void delete(T entity);
	
	public List<T> findAll();
	
	public void deleteAll();
	
	public List<Tweet> findByExtraction(Extraction extraction);
	
	public void persistList(List<T> entityList);
}