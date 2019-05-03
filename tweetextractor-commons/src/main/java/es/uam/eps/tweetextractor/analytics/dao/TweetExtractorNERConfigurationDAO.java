/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.analytics.dao.inter.TweetExtractorNERConfigurationDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jose
 *
 */
@Repository
public class TweetExtractorNERConfigurationDAO extends AbstractGenericDAO<TweetExtractorNERConfiguration, TweetExtractorNERConfigurationID>
		implements TweetExtractorNERConfigurationDAOInterface<TweetExtractorNERConfiguration> {

	@Override
	public List<TweetExtractorNERConfiguration> findByUserAndLanguage(User user, AvailableTwitterLanguage language) {
		return null;
	}

}
