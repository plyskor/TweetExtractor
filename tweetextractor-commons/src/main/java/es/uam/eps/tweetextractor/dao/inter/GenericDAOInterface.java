/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.io.Serializable;
import org.hibernate.Session;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface GenericDAOInterface <V,Id extends Serializable>{
	public Session openCurrentSession();
	public Session openCurrentSessionwithTransaction();
	public void closeCurrentSession();
	public void closeCurrentSessionwithTransaction();
	public void persist(V entity);
	public void update(V entity);
	public void delete(V entity);
	public void refresh(V entity);
	public V merge(V entity);
}
