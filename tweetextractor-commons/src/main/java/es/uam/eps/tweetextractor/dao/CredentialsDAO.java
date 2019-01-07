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

import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.dao.inter.CredentialsDAOInterface;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Repository
public class CredentialsDAO  extends AbstractGenericDAO<Credentials,Integer> implements CredentialsDAOInterface<Credentials, Integer> {
	public CredentialsDAO() {
		super();
	}
	public Credentials findById(Integer id) {
		return find(id); 
	}
	public List<Credentials> findByUser(User user) {
	    CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
	    CriteriaQuery<Credentials> criteriaQuery = criteriaBuilder.createQuery(Credentials.class);
	    Root<Credentials> root = criteriaQuery.from(Credentials.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Integer> params = criteriaBuilder.parameter(Integer.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("user_identifier"), params));
	    TypedQuery<Credentials> query = currentSession().createQuery(criteriaQuery);
	    query.setParameter(params, user.getIdDB() );
	    List<Credentials> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	System.out.println("No credentials found for userID: "+user.getIdDB());
	    	return null;
	    	}
	    return ret;
	}
	public List<Credentials> findAll() {
		List<Credentials> credentials = (List<Credentials>) currentSession().createQuery("from Credentials").list();
		return credentials;
	}
	public void deleteAll() {
		List<Credentials> entityList = findAll();
		for (Credentials entity : entityList) {
			delete(entity);
		}
	}
	

	
}