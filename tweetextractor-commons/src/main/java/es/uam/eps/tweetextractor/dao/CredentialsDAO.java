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

import es.uam.eps.tweetextractor.dao.inter.CredentialsDAOInterface;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class CredentialsDAO  extends GenericDAO<Credentials,Integer> implements CredentialsDAOInterface<Credentials, Integer> {


	public CredentialsDAO() {
	}



	public Credentials findById(Integer id) {
		if(currentSession!=null) {
			Credentials credentials = currentSession.get(Credentials.class, id);
			return credentials; 
		}
		return null;
	}
	public List<Credentials> findByUser(User user) {
		if(currentSession==null)return null;
	    CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
	    CriteriaQuery<Credentials> criteriaQuery = criteriaBuilder.createQuery(Credentials.class);
	    Root<Credentials> root = criteriaQuery.from(Credentials.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Integer> params = criteriaBuilder.parameter(Integer.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("user_identifier"), params));
	    TypedQuery<Credentials> query = currentSession.createQuery(criteriaQuery);
	    query.setParameter(params, user.getIdDB() );
	    List<Credentials> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	System.out.println("No credentials found for userID: "+user.getIdDB());	   
	    	}
	    return ret;
	}


	@SuppressWarnings("unchecked")
	public List<Credentials> findAll() {
		if(currentSession!=null) {
			List<Credentials> credentials = (List<Credentials>) currentSession.createQuery("from Credentials").list();
			return credentials;
		}
		return null;
	}

	public void deleteAll() {
		List<Credentials> entityList = findAll();
		for (Credentials entity : entityList) {
			delete(entity);
		}
	}

	
}