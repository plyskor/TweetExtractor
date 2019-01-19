/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

/**
 * @author Jose Antonio García del Saz
 *
 */
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.UserDAOInterface;
import es.uam.eps.tweetextractor.model.User;

@Repository
public class UserDAO extends AbstractGenericDAO<User,Integer> implements UserDAOInterface{

	public UserDAO() {
		super();
	}

	public User findByNickname(String nickname) {
	    CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
	    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
	    Root<User> root = criteriaQuery.from(User.class);
	    criteriaQuery.select(root);
	    ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("nickname"), params));
	    TypedQuery<User> query = currentSession().createQuery(criteriaQuery);
	    query.setParameter(params, nickname);
	    User ret= null;
	    try {ret=query.getSingleResult();}catch(NoResultException e) {
	    	System.out.println("No user found for nickname: "+nickname);	   
	    	}
	    return ret;
	}
	
}