package es.uam.eps.tweetextractor.dao;

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
import es.uam.eps.tweetextractor.dao.inter.ExtractionDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;

public class ExtractionDAO implements ExtractionDAOInterface<Extraction, Integer> {

	private Session currentSession;
	
	private Transaction currentTransaction;

	public ExtractionDAO() {
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
		try{
			 sessionFactory = configuration.buildSessionFactory();
		}catch(HibernateException e) {
			ErrorDialog.showErrorDB(e.getMessage());
		}
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

	public void persist(Extraction entity) {
		getCurrentSession().persist(entity);
	}

	public void update(Extraction entity) {
		getCurrentSession().update(entity);
	}

	public Extraction findById(Integer id) {
		Extraction extraction = (Extraction) getCurrentSession().get(Extraction.class, id);
		return extraction; 
	}
	public List<Extraction> findByUser(User user) {
		if(getCurrentSession()==null)return null;
	    CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
	    CriteriaQuery<Extraction> criteriaQuery = criteriaBuilder.createQuery(Extraction.class);
	    Root<Extraction> root = criteriaQuery.from(Extraction.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Integer> params = criteriaBuilder.parameter(Integer.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("user_identifier"), params));
	    TypedQuery<Extraction> query = getCurrentSession().createQuery(criteriaQuery);
	    query.setParameter(params, user.getIdDB() );
	    List<Extraction> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	System.out.println("No extraction found for userID: "+user.getIdDB());	   
	    	}
	    return ret;
	}
	public void delete(Extraction entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Extraction> findAll() {
		List<Extraction> extractions = (List<Extraction>) getCurrentSession().createQuery("from Extraction").list();
		return extractions;
	}

	public void deleteAll() {
		List<Extraction> entityList = findAll();
		for (Extraction entity : entityList) {
			delete(entity);
		}
	}

	public Extraction merge(Extraction entity) {
		return (Extraction) getCurrentSession().merge(entity);
	}

	
}