/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;
import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERToken;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;

/**
 * @author jose
 *
 */
public interface TweetExtractorNERTokenServiceInterface extends GenericServiceInterface<TweetExtractorNERToken, Integer> {
	public List<TweetExtractorNERToken> findBySet(TweetExtractorNERTokenSetID setID);
}
