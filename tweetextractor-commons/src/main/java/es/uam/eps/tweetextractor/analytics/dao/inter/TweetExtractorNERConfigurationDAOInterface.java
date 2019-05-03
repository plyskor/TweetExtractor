/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.inter.GenericDAOInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jose
 *
 */
public interface TweetExtractorNERConfigurationDAOInterface<T> extends GenericDAOInterface< TweetExtractorNERConfiguration, TweetExtractorNERConfigurationID> {
	public List<T> findByUserAndLanguage(User user, AvailableTwitterLanguage language);
}
