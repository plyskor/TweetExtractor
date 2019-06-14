/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.io.Serializable;
import java.util.List;

/**
 * @author jose
 *
 */
public interface GenericServiceInterface<V extends Serializable, K extends Serializable> {
	    public void saveOrUpdate(V entity);
	    public List<V> findAll();
	    public V findById(K id);
	    public void persist(V entity);
	    public void persistList(List<V> entityList);
	    public boolean existsAny(K id);
	    public void update(V entity);
	    public void delete(V entity);
	    public void detach(V entity);
	    public void detachList(List<V> entityList);
	    public void merge(V entity);
	    public void deleteById(K id);
	    public void refresh(V entity);
	    public void deleteAll();
		public void initialize(Object lazyObject);
		public void deleteList(List<V> entityList);
		public void flush();
}
