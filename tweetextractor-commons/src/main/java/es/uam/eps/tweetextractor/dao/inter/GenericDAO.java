/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface GenericDAO <V>{
	public Session openCurrentSession();
	public Session openCurrentSessionwithTransaction();
	public void closeCurrentSession();
	public void closeCurrentSessionwithTransaction();
	public void destroySessionFactory();
	public void persist(V entity);
}
