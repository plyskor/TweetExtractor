/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		Query<TweetExtractorNERConfiguration> query = currentSession().createNamedQuery("findNERPreferencesByUserAndLanguage",TweetExtractorNERConfiguration.class);
	    query.setParameter("user", user);
	    query.setParameter("language", language);
	     List<TweetExtractorNERConfiguration> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No NER preferences found for userID: "+user.getIdDB()+" and language: "+language.getLongName());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
