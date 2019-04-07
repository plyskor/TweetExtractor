package es.uam.eps.tweetextractor.analytics.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.analytics.dao.inter.AnalyticsReportDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

@Repository
public class AnalyticsReportDAO extends AbstractGenericDAO<AnalyticsReport,Integer> implements AnalyticsReportDAOInterface<AnalyticsReport> {

	public AnalyticsReportDAO() {
		super();
	}
	public List<AnalyticsReport> findByUser(User user) {
	    Query<AnalyticsReport> query = currentSession().createNamedQuery("findAnalyticsReportByUser",AnalyticsReport.class);
	    query.setParameter("user", user);
	     List<AnalyticsReport> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No analytics report found for userID: "+user.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}
	public List<AnalyticsReport> findByUserAndReportType(User user,List<AnalyticsReportTypes> types) {
	    Query<AnalyticsReport> query = currentSession().createNamedQuery("findAnalyticsReportByUserAndReportType",AnalyticsReport.class);
	    query.setParameter("user", user);
	    query.setParameterList("reportTypeList", types);
	    List<AnalyticsReport> ret= null;
	    Logger logger = LoggerFactory.getLogger(this.getClass());
	    try {ret=query.getResultList();}catch(Exception e) {
	    	logger.info("No analytics report found for userID: "+user.getIdDB()+" and types: "+types.toString());
	    	return new ArrayList<>();
	    }
	    return ret;
	}
}
