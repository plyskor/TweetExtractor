/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.io.Serializable;
import java.util.List;
import es.uam.eps.tweetextractor.model.Extraction;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface TweetDAOInterface <T,Id extends Serializable>{
		
	public T findById(Id id);
			
	public List<T> findAll();
	
	public void deleteAll();
	
	public List<T> findByExtraction(Extraction extraction);
	
	public void persistList(List<T> entityList);
}