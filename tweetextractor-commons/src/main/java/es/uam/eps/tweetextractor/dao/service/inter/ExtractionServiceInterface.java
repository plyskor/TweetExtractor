/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author jose
 *
 */
public interface ExtractionServiceInterface extends GenericServiceInterface<Extraction, Integer> {
	public boolean hasAnyExtraction(User user);
	public List<Extraction> findByUser(User user);
}
