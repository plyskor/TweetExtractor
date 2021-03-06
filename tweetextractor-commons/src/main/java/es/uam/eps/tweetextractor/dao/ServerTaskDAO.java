package es.uam.eps.tweetextractor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.ServerTaskDAOInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTask;
@Repository
public class ServerTaskDAO extends AbstractGenericDAO<ServerTask, Integer> implements ServerTaskDAOInterface<ServerTask> {

	public ServerTaskDAO() {
		super();
	}

	public List<ServerTask> findByUser(User user) {
		Query<ServerTask> query = currentSession().createNamedQuery("findServerTasksByUser",ServerTask.class);
	    query.setParameter("user", user);
	     List<ServerTask> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No server task found for userID: "+user.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

	@Override
	public List<ServerTask> findByReport(AnalyticsCategoryReport report) {
		Query<ServerTask> query = currentSession().createNamedQuery("findServerTasksByReport",ServerTask.class);
	    query.setParameter("report", report);
	     List<ServerTask> ret= new ArrayList<>();
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No server task found for report with ID: "+report.getId());
	    	return ret;
	    	}
	    return ret;
	}
	
}