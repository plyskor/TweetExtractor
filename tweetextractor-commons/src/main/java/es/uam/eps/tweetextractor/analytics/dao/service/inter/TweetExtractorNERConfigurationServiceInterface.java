/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jose
 *
 */
public interface TweetExtractorNERConfigurationServiceInterface extends GenericServiceInterface<TweetExtractorNERConfiguration, TweetExtractorNERConfigurationID> {
	public List<TweetExtractorNERConfiguration> findByUserAndLanguage(User user, AvailableTwitterLanguage language);
}
