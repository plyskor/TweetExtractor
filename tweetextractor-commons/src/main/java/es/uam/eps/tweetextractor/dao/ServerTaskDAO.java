package es.uam.eps.tweetextractor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.dao.inter.ServerTaskDAOInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
@Repository
public class ServerTaskDAO extends AbstractGenericDAO<ServerTask, Integer> implements ServerTaskDAOInterface<ServerTask> {

	public ServerTaskDAO() {
		super();
	}

	public List<ServerTask> findByUser(User user) {
	    CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
	    CriteriaQuery<ServerTask> criteriaQuery = criteriaBuilder.createQuery(ServerTask.class);
	    Root<ServerTask> root = criteriaQuery.from(ServerTask.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Integer> params = criteriaBuilder.parameter(Integer.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("user_identifier"), params));
	    TypedQuery<ServerTask> query = currentSession().createQuery(criteriaQuery);
	    query.setParameter(params, user.getIdDB() );
	    List<ServerTask> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No serverTask found for userID: "+user.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}
	
}