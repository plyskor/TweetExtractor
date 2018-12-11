/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

/**
 * @author Jose Antonio García del Saz
 *
 */
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import es.uam.eps.tweetextractor.dao.inter.UserDAOInterface;
import es.uam.eps.tweetextractor.model.User;

public class UserDAO extends GenericDAO<User,Integer> implements UserDAOInterface<User, Integer>{

	public UserDAO() {
	}

	public User findById(Integer id) {
		if(currentSession==null) return null;
		User User = (User) currentSession.get(User.class, id);
		return User; 
	}
	public User findByNickname(String nickname) {
		if(currentSession==null)return null;
	    CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
	    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
	    Root<User> root = criteriaQuery.from(User.class);
	    criteriaQuery.select(root);
	    ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("nickname"), params));
	    TypedQuery<User> query = currentSession.createQuery(criteriaQuery);
	    query.setParameter(params, nickname);
	    User ret= null;
	    try {ret=query.getSingleResult();}catch(NoResultException e) {
	    	System.out.println("No user found for nickname: "+nickname);	   
	    	}
	    return ret;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		if(currentSession==null) return null;
		List<User> Users = (List<User>) currentSession.createQuery("from User").list();
		return Users;
	}

	public void deleteAll() {
		if(currentSession==null) return ;
		List<User> entityList = findAll();
		for (User entity : entityList) {
			delete(entity);
		}
	}

	
}