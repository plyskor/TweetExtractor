/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorTopic;

/**
 * @author jose
 *
 */
public interface TweetExtractorTopicServiceInterface extends GenericServiceInterface<TweetExtractorTopic, Integer> {
	public List<TweetExtractorTopic> findByNamedEntity(int namedEntityID);
}
