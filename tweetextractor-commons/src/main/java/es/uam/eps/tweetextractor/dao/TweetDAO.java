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

import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.dao.inter.TweetDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Repository
public class TweetDAO extends AbstractGenericDAO<Tweet,Integer> implements TweetDAOInterface<Tweet, Integer>{

	public TweetDAO() {
		super();
	}

	public void persistList(List<Tweet> entityList) {
		if(entityList==null)return;
		for(Tweet entity : entityList) {
			persist(entity);
		}
	}
	public Tweet findById(Integer id) {
		return find(id);
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
	    List<Tweet> ret= new ArrayList<Tweet>();
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	System.out.println("No tweet found for extractionID: "+extraction.getIdDB());	   
	    	}
	    return ret;
	}
	public List<Tweet> findAll() {
		List<Tweet> tweets = (List<Tweet>) currentSession().createQuery("from Tweet").list();
		return tweets;
	}

	public void deleteAll() {
		List<Tweet> entityList = findAll();
		for (Tweet entity : entityList) {
			delete(entity);
		}
	}

	
}