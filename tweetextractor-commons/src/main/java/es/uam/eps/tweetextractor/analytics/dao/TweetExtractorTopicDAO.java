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
import es.uam.eps.tweetextractor.analytics.dao.inter.TweetExtractorTopicDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorTopic;

/**
 * @author jose
 *
 */
@Repository
public class TweetExtractorTopicDAO extends AbstractGenericDAO<TweetExtractorTopic, Integer> implements TweetExtractorTopicDAOInterface<TweetExtractorTopic> {

	@Override
	public List<TweetExtractorTopic> findByNamedEntity(int namedEntityID) {
		Query<TweetExtractorTopic> query = currentSession().createNamedQuery("findByNamedEntity",TweetExtractorTopic.class);
	    query.setParameter("id", namedEntityID);
	     List<TweetExtractorTopic> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No topics found for named entity with id: "+namedEntityID);
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
