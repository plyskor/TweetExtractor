/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;

/**
 * @author jose
 *
 */
public interface TweetExtractorNamedEntityDAOInterface<T>{
	public List<T> findByConfiguration(TweetExtractorNERConfigurationID fk);
}
