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
import es.uam.eps.tweetextractor.analytics.dao.inter.TweetExtractorNERTokenSetDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jose
 *
 */
@Repository
public class TweetExtractorNERTokenSetDAO extends AbstractGenericDAO<TweetExtractorNERTokenSet, TweetExtractorNERTokenSetID>
		implements TweetExtractorNERTokenSetDAOInterface<TweetExtractorNERTokenSet> {

	/**
	 * 
	 */
	public TweetExtractorNERTokenSetDAO() {
		super();
	}

	@Override
	public List<TweetExtractorNERTokenSet> findByUserAndLanguage(User user, AvailableTwitterLanguage language) {
		Query<TweetExtractorNERTokenSet> query = currentSession().createNamedQuery("findNERTokenSetByUserAndLanguage",TweetExtractorNERTokenSet.class);
		query.setParameter("user", user);
	    query.setParameter("language", language);
	    List<TweetExtractorNERTokenSet> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No NER token sets found for userID: "+user.getIdDB()+" and language: "+language.getLongName());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
