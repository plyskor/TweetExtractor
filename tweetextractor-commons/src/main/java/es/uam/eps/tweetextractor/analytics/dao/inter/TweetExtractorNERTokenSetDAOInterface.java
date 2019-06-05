/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jose
 *
 */
public interface TweetExtractorNERTokenSetDAOInterface<T>{
	public List<T> findByUserAndLanguage(User user, AvailableTwitterLanguage language);
}
