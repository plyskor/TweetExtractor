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

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.dao.inter.TweetDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */

public class TweetDAO implements TweetDAOInterface<Tweet, Integer> {

	private Session currentSession;
	
	private Transaction currentTransaction;

	public TweetDAO() {
	}

	public Session openCurrentSession() {
		SessionFactory sf=getSessionFactory();
		if(sf!=null)
			currentSession=sf.openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		SessionFactory sf=getSessionFactory();
		if(sf!=null)
			currentSession =sf.openSession();
		if(currentSession!=null)
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		if (currentSession!=null)
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		if(currentSession!=null&&currentTransaction!=null) {
		currentTransaction.commit();
		currentSession.close();
		}
	}
	
	private static SessionFactory getSessionFactory() {
		SessionFactory sessionFactory=null;
		Configuration configuration = new Configuration().configure("tweetextractordb.xml");
		sessionFactory = configuration.buildSessionFactory();
		return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	public void persist(Tweet entity) {
		if(currentSession!=null)
		currentSession.persist(entity);
	}
	public void persistList(List<Tweet> entityList) {
		if(entityList==null)return;
		if(currentSession==null)return;
		for(Tweet entity : entityList) {
			currentSession.persist(entity);
		}
	}
	public void update(Tweet entity) {
		if(currentSession!=null)
		currentSession.update(entity);
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
	public void delete(Tweet entity) {
		if(currentSession!=null)
		currentSession.delete(entity);
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