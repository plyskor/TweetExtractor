/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.filter.Filter;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface FilterServiceInterface extends GenericServiceInterface<Filter, Integer> {
	public List<Filter> findByExtraction(Extraction e);
}
