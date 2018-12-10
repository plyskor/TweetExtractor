/**
 * 
 */
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
import es.uam.eps.tweetextractor.dao.inter.CredentialsDAOInterface;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class CredentialsDAO implements CredentialsDAOInterface<Credentials, Integer> {

	private Session currentSession;
	
	private Transaction currentTransaction;

	public CredentialsDAO() {
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

	public void persist(Credentials entity) {
		if(currentSession!=null)
		currentSession.persist(entity);
	}

	public void update(Credentials entity) {
		if(currentSession!=null)
		currentSession.update(entity);
	}

	public Credentials findById(Integer id) {
		if(currentSession!=null) {
			Credentials credentials = currentSession.get(Credentials.class, id);
			return credentials; 
		}
		return null;
	}
	public List<Credentials> findByUser(User user) {
		if(getCurrentSession()==null)return null;
	    CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
	    CriteriaQuery<Credentials> criteriaQuery = criteriaBuilder.createQuery(Credentials.class);
	    Root<Credentials> root = criteriaQuery.from(Credentials.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Integer> params = criteriaBuilder.parameter(Integer.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("user_identifier"), params));
	    TypedQuery<Credentials> query = getCurrentSession().createQuery(criteriaQuery);
	    query.setParameter(params, user.getIdDB() );
	    List<Credentials> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	System.out.println("No credentials found for userID: "+user.getIdDB());	   
	    	}
	    return ret;
	}
	public void delete(Credentials entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Credentials> findAll() {
		List<Credentials> credentials = (List<Credentials>) getCurrentSession().createQuery("from Credentials").list();
		return credentials;
	}

	public void deleteAll() {
		List<Credentials> entityList = findAll();
		for (Credentials entity : entityList) {
			delete(entity);
		}
	}

	
}