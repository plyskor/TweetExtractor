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
import es.uam.eps.tweetextractor.analytics.dao.inter.TweetExtractorNERTokenDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERToken;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;

/**
 * @author jose
 *
 */
@Repository
public class TweetExtractorNERTokenDAO extends AbstractGenericDAO<TweetExtractorNERToken, Integer>
		implements TweetExtractorNERTokenDAOInterface<TweetExtractorNERToken> {

	/**
	 * 
	 */
	public TweetExtractorNERTokenDAO() {
		super();
	}

	@Override
	public List<TweetExtractorNERToken> findBySet(TweetExtractorNERTokenSetID setID) {
		Query<TweetExtractorNERToken> query = currentSession().createNamedQuery("findNERTokenBySet",TweetExtractorNERToken.class);
	    query.setParameter("setID", setID);
	     List<TweetExtractorNERToken> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No NER tokens found for userID: "+setID.getUser().getIdDB()+" and language: "+setID.getLanguage().getLongName());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

	@Override
	public List<TweetExtractorNERToken> findNotClassifiedBySet(TweetExtractorNERTokenSetID setID) {
		Query<TweetExtractorNERToken> query = currentSession().createNamedQuery("findNotClassifiedNERTokenBySet",TweetExtractorNERToken.class);
	    query.setParameter("setID", setID);
	     List<TweetExtractorNERToken> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No NER tokens found for userID: "+setID.getUser().getIdDB()+" and language: "+setID.getLanguage().getLongName());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
