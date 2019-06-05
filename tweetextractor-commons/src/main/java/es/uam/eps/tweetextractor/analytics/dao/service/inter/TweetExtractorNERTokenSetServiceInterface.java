/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;
import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jose
 *
 */
public interface TweetExtractorNERTokenSetServiceInterface extends GenericServiceInterface<TweetExtractorNERTokenSet, TweetExtractorNERTokenSetID> {
	public List<TweetExtractorNERTokenSet> findByUserAndLanguage(User user, AvailableTwitterLanguage language);
}
