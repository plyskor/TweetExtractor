/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

/**
 * @author Jose Antonio García del Saz
 *
 */
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
import es.uam.eps.tweetextractor.dao.inter.UserDAOInterface;
import es.uam.eps.tweetextractor.model.User;

public class UserDAO implements UserDAOInterface<User, Integer> {

	private Session currentSession;
	
	private Transaction currentTransaction;

	public UserDAO() {
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
		if(currentSession!=null)
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
			throw(e);
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

	public void persist(User entity) {
		getCurrentSession().persist(entity);
	}

	public void update(User entity) {
		getCurrentSession().update(entity);
	}

	public User findById(Integer id) {
		User User = (User) getCurrentSession().get(User.class, id);
		return User; 
	}
	public User findByNickname(String nickname) {
		if(getCurrentSession()==null)return null;
	    CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
	    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
	    Root<User> root = criteriaQuery.from(User.class);
	    criteriaQuery.select(root);
	    ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("nickname"), params));
	    TypedQuery<User> query = getCurrentSession().createQuery(criteriaQuery);
	    query.setParameter(params, nickname);
	    User ret= null;
	    try {ret=query.getSingleResult();}catch(NoResultException e) {
	    	System.out.println("No user found for nickname: "+nickname);	   
	    	}
	    return ret;
	}
	public void delete(User entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		List<User> Users = (List<User>) getCurrentSession().createQuery("from User").list();
		return Users;
	}

	public void deleteAll() {
		List<User> entityList = findAll();
		for (User entity : entityList) {
			delete(entity);
		}
	}

	
}