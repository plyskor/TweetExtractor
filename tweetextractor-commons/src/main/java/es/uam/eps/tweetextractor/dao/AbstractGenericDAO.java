/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.GenericDAOInterface;

/**
 * @author jose
 *
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class AbstractGenericDAO<V extends Serializable, K extends Serializable>
		implements GenericDAOInterface<V, K> {
	@Autowired
    private SessionFactory sessionFactory;
     
    protected Class<V> daoType;
     
    /**
    * By defining this class as abstract, we prevent Spring from creating 
    * instance of this class If not defined as abstract, 
    * getClass().getGenericSuperClass() would return Object. There would be 
    * exception because Object class does not hava constructor with parameters.
    */
	public AbstractGenericDAO() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class<V>) pt.getActualTypeArguments()[0];
    }
     
    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
     
    @Override
    public void persist(V entity) {
        currentSession().save(entity);
    }
     
    @Override
    public void saveOrUpdate(V entity) {
        currentSession().saveOrUpdate(entity);
    }
     
    @Override
    public void update(V entity) {
        currentSession().saveOrUpdate(entity);
    }
     
    @Override
    public void delete(V entity) {
        currentSession().delete(entity);
    }
     
    @Override
    public V find(K key) {
        return (V) currentSession().get(daoType, key);
    }
    @Override
    public List<V> getAll() {
        return currentSession().createQuery("from "+daoType.getName()).list(); 
    }
    @Override
	public void refresh(V entity) {
		currentSession().refresh(entity);
		return;
	}
    @Override
    public void deleteById(K idDB) {
		V entity = find(idDB);
		if(entity!=null)delete(entity);
	}
}
