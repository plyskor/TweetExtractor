/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;

/**
 * @author jose
 *
 */
public interface TweetExtractorNamedEntityServiceInterface extends GenericServiceInterface<TweetExtractorNamedEntity, Integer> {
	public List<TweetExtractorNamedEntity> findByConfiguration(TweetExtractorNERConfigurationID fk);

}
