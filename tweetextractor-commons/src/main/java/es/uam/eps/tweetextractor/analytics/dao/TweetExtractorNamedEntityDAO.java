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
import es.uam.eps.tweetextractor.analytics.dao.inter.TweetExtractorNamedEntityDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;

/**
 * @author jose
 *
 */
@Repository
public class TweetExtractorNamedEntityDAO extends AbstractGenericDAO<TweetExtractorNamedEntity, Integer>
		implements TweetExtractorNamedEntityDAOInterface<TweetExtractorNamedEntity> {

	@Override
	public List<TweetExtractorNamedEntity> findByConfiguration(TweetExtractorNERConfigurationID fk) {
		Query<TweetExtractorNamedEntity> query = currentSession().createNamedQuery("findByConfiguration",TweetExtractorNamedEntity.class);
	    query.setParameter("fk", fk);
	     List<TweetExtractorNamedEntity> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No named entities found for userID: "+fk.getUser().getIdDB()+", language: "+fk.getLanguage().getShortName()+" and list name: "+fk.getName());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
