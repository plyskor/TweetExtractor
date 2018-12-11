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

import es.uam.eps.tweetextractor.dao.inter.TweetDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */

public class TweetDAO extends GenericDAO<Tweet,Integer> implements TweetDAOInterface<Tweet, Integer>{

	public TweetDAO() {
	}

	public void persistList(List<Tweet> entityList) {
		if(entityList==null)return;
		if(currentSession==null)return;
		for(Tweet entity : entityList) {
			persist(entity);
		}
	}
	public Tweet findById(Integer id) {
		if(currentSession==null) return null;
		Tweet tweet = (Tweet) currentSession.get(Tweet.class, id);
		return tweet; 
	}
	public List<Tweet> findByExtraction(Extraction extraction) {
		if(currentSession==null)return null;
	    CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
	    CriteriaQuery<Tweet> criteriaQuery = criteriaBuilder.createQuery(Tweet.class);
	    Root<Tweet> root = criteriaQuery.from(Tweet.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Extraction> params = criteriaBuilder.parameter(Extraction.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("extraction"), params));
	    TypedQuery<Tweet> query = currentSession.createQuery(criteriaQuery);
	    query.setParameter(params, extraction );
	    List<Tweet> ret= new ArrayList<Tweet>();
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	System.out.println("No tweet found for extractionID: "+extraction.getIdDB());	   
	    	}
	    return ret;
	}

	@SuppressWarnings("unchecked")
	public List<Tweet> findAll() {
		if(currentSession==null) return null;
		List<Tweet> tweets = (List<Tweet>) currentSession.createQuery("from Tweet").list();
		return tweets;
	}

	public void deleteAll() {
		if(currentSession==null)return;
		List<Tweet> entityList = findAll();
		for (Tweet entity : entityList) {
			delete(entity);
		}
	}

	
}