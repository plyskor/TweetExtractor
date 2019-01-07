/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author jose
 *
 */
public interface TweetServiceInterface extends GenericServiceInterface<Tweet, Integer> {
	public void persistList(List<Tweet> entityList);
	public List<Tweet> findByExtraction(Extraction extraction);
}
