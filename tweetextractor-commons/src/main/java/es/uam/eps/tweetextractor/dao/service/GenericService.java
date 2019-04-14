/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;

/**
 * @author jose
 *
 */
@Service
public abstract class GenericService<V extends Serializable, K extends Serializable> 
        implements GenericServiceInterface<V, K> {
	@Autowired
    private AbstractGenericDAO<V, K> genericDao;
 
    public GenericService(AbstractGenericDAO<V,K> genericDao) {
        this.genericDao=genericDao;
    }
 
    public GenericService() {
    }
 
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(V entity) {
        genericDao.saveOrUpdate(entity);
    }
 
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<V> findAll() {
        return genericDao.getAll();
    }
 
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public V findById(K id) {
        return genericDao.find(id);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void refresh(V entity) {
         genericDao.refresh(entity);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void initialize(Object lazyObject) {
        genericDao.initialize(lazyObject);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(V entity) {
        genericDao.persist(entity);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void merge(V entity) {
        genericDao.merge(entity);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(V entity) {
        genericDao.update(entity);
    }
 
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(V entity) {
        genericDao.delete(entity);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(K id) {
    	V entity = findById(id);
        if(entity!=null)genericDao.delete(entity);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAll() {
        genericDao.deleteAll();
    }
}