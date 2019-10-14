/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.Extraction;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface FilterDAOInterface <T>{
	public List<T> findByExtraction(Extraction e);
}
