/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface GenericDAOInterface <V extends Serializable,K extends Serializable>{
	public void persist(V entity) ;
    public void saveOrUpdate(V entity) ;
    public void update(V entity) ;
    public void delete(V entity);
    public void deleteById(K id);
    public V find(K key);
    public List<V> getAll() ;
	void refresh(V entity);
	void deleteAll();
	void persistList(List<V> entityList);
}
