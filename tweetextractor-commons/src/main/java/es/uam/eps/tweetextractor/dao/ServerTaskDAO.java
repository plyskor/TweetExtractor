package es.uam.eps.tweetextractor.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import es.uam.eps.tweetextractor.dao.inter.ServerTaskDAOInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;

public class ServerTaskDAO extends GenericDAO<ServerTask, Integer> implements ServerTaskDAOInterface<ServerTask, Integer> {

	public ServerTaskDAO() {
	}

	public ServerTask findById(Integer id) {
		if(currentSession!=null) {
		ServerTask serverTask = (ServerTask) currentSession.get(ServerTask.class, id);
		return serverTask; }
		return null;
	}
	public List<ServerTask> findByUser(User user) {
		if(currentSession==null)return null;
	    CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
	    CriteriaQuery<ServerTask> criteriaQuery = criteriaBuilder.createQuery(ServerTask.class);
	    Root<ServerTask> root = criteriaQuery.from(ServerTask.class);
	    criteriaQuery.select(root);
	    ParameterExpression<Integer> params = criteriaBuilder.parameter(Integer.class);
	    criteriaQuery.where(criteriaBuilder.equal(root.get("user_identifier"), params));
	    TypedQuery<ServerTask> query = currentSession.createQuery(criteriaQuery);
	    query.setParameter(params, user.getIdDB() );
	    List<ServerTask> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	System.out.println("No serverTask found for userID: "+user.getIdDB());	   
	    	}
	    return ret;
	}

	@SuppressWarnings("unchecked")
	public List<ServerTask> findAll() {
		this.openCurrentSession();
		List<ServerTask> serverTasks = (List<ServerTask>) currentSession.createQuery("from ServerTask").list();
		this.closeCurrentSession();
		return serverTasks;
	}

	public void deleteAll() {
		if(currentSession!=null)
			return;
		List<ServerTask> entityList = findAll();
		for (ServerTask entity : entityList) {
			delete(entity);
		}
	}


	
}