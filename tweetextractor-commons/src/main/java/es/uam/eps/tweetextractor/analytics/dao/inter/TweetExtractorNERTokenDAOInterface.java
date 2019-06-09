/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;

/**
 * @author jose
 *
 */
public interface TweetExtractorNERTokenDAOInterface<T> {
	public List<T> findBySet(TweetExtractorNERTokenSetID setID);
	public List<T> findNotClassifiedBySet(TweetExtractorNERTokenSetID setID);
}
