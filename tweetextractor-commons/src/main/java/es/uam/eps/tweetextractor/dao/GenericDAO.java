/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import es.uam.eps.tweetextractor.dao.inter.GenericDAOInterface;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class GenericDAO<V,Id extends Serializable> implements GenericDAOInterface<V,Id> {
	protected Session currentSession;
	protected static Configuration configuration = new Configuration().configure("tweetextractordb.xml");
	protected static SessionFactory sessionFactory=sessionFactory = configuration.buildSessionFactory();
	protected Transaction currentTransaction;
	/**
	 * 
	 */
	public GenericDAO() {
	}
	public static SessionFactory buildSessionFactory() {
		SessionFactory sessionFactory=null;
		try{
			 sessionFactory = configuration.buildSessionFactory();
		}catch(HibernateException e) {
			e.printStackTrace();
		}
		return sessionFactory;
	}
	@Override
	public Session openCurrentSession() {
		sessionFactory=buildSessionFactory();
		if(sessionFactory!=null)
			currentSession=sessionFactory.openSession();
		return currentSession;
	}
	
	@Override
	public Session openCurrentSessionwithTransaction() {
		sessionFactory=buildSessionFactory();
		if(sessionFactory!=null)
			currentSession =sessionFactory.openSession();
		if(currentSession!=null)
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}

	@Override
	public void closeCurrentSession() {
		if (currentSession!=null)
			currentSession.close();
		if(sessionFactory!=null)
		sessionFactory.close();
	}

	@Override
	public void closeCurrentSessionwithTransaction() {
		if(currentSession!=null&&currentTransaction!=null) {
			currentTransaction.commit();
			currentSession.close();
			}
		if(sessionFactory!=null) sessionFactory.close();
	}

	@Override
	public void persist(V entity) {
		if(currentSession!=null)
			currentSession.persist(entity);
	}

	@Override
	public void update(V entity) {
		if(currentSession!=null)
			currentSession.update(entity);
	}

	@Override
	public void delete(V entity) {
		if(currentSession!=null)
			currentSession.delete(entity);		
	}

	

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration configuration) {
		GenericDAO.configuration = configuration;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		GenericDAO.sessionFactory = sessionFactory;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}
	@Override
	public void refresh(V entity) {
		if(currentSession!=null)
			currentSession.refresh(entity);	
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public V merge(V entity) {
		if(currentSession!=null)
			return (V) currentSession.merge(entity);
			return null;
	}
	
}
