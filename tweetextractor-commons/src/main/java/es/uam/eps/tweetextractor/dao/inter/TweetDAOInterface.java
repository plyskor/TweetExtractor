/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.Extraction;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface TweetDAOInterface <T>{
	
	public List<T> findByExtraction(Extraction extraction);

}