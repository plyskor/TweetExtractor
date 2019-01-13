/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.dao.inter.TweetDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Repository
public class TweetDAO extends AbstractGenericDAO<Tweet,Integer> implements TweetDAOInterface<Tweet>{

	public TweetDAO() {
		super();
	}

	public List<Tweet> findByExtraction(Extraction extraction) {
	    CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
	    CriteriaQuery<Tweet> criteriaQuery = criteriaBuilder.createQuery(Tweet.class);
	    Root<Tweet> root = criteriaQuery.from(Tweet.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Extraction> params = criteriaBuilder.parameter(Extraction.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("extraction"), params));
	    TypedQuery<Tweet> query = currentSession().createQuery(criteriaQuery);
	    query.setParameter(params, extraction );
	    List<Tweet> ret= new ArrayList<>();
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No tweet found for extractionID: "+extraction.getIdDB());	   
	    	}
	    return ret;
	}
	
}