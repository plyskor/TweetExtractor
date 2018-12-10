package es.uam.eps.tweetextractor.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import es.uam.eps.tweetextractor.dao.inter.ServerTaskDAOInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;

public class ServerTaskDAO implements ServerTaskDAOInterface<ServerTask, Integer> {

	private Session currentSession;
	
	private Transaction currentTransaction;

	public ServerTaskDAO() {
	}

	public Session openCurrentSession() {
		SessionFactory sf=getSessionFactory();
		if(sf!=null)
		this.setCurrentSession(sf.openSession());
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		SessionFactory sf=getSessionFactory();
		if(sf!=null)
			currentSession =sf.openSession();
		if(currentSession!=null)
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		if (currentSession!=null)
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		if(currentSession!=null&&currentTransaction!=null) {
		currentTransaction.commit();
		currentSession.close();
		}
	}
	
	private static SessionFactory getSessionFactory() {
		SessionFactory sessionFactory=null;
		Configuration configuration = new Configuration().configure("tweetextractordb.xml");
			 sessionFactory = configuration.buildSessionFactory();		
		return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	public void persist(ServerTask entity) {
		if(currentSession!=null)
		currentSession.persist(entity);
	}

	public void update(ServerTask entity) {
		if(currentSession!=null)
		currentSession.update(entity);
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
	public void delete(ServerTask entity) {
		if(currentSession!=null)
		currentSession.delete(entity);
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

	public ServerTask merge(ServerTask entity) {
		if(currentSession!=null)
		return (ServerTask) currentSession.merge(entity);
		return null;
	}

	
}