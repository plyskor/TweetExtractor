/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class GenericDAO<V> implements es.uam.eps.tweetextractor.dao.inter.GenericDAO<V> {
	private Session currentSession;
	private static Configuration configuration = new Configuration().configure("tweetextractordb.xml");
	private static SessionFactory sessionFactory=sessionFactory = configuration.buildSessionFactory();
	private Transaction currentTransaction;
	/**
	 * 
	 */
	public GenericDAO() {
	}

	@Override
	public Session openCurrentSession() {
		return null;
	}

	@Override
	public Session openCurrentSessionwithTransaction() {
		return null;
	}

	@Override
	public void closeCurrentSession() {
		
	}

	@Override
	public void closeCurrentSessionwithTransaction() {
		
	}

	@Override
	public void destroySessionFactory() {
		
	}

	@Override
	public void persist(V entity) {
		
	}

}
