package es.uam.eps.tweetextractor.analytics.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.analytics.dao.inter.AnalyticsReportDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

@Repository
public class AnalyticsReportDAO extends AbstractGenericDAO<AnalyticsCategoryReport,Integer> implements AnalyticsReportDAOInterface<AnalyticsCategoryReport> {

	public AnalyticsReportDAO() {
		super();
	}
	public List<AnalyticsCategoryReport> findByUser(User user) {
	    Query<AnalyticsCategoryReport> query = currentSession().createNamedQuery("findAnalyticsReportByUser",AnalyticsCategoryReport.class);
	    query.setParameter("user", user);
	     List<AnalyticsCategoryReport> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No analytics report found for userID: "+user.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}
	public List<AnalyticsCategoryReport> findByUserAndReportType(User user,List<AnalyticsReportTypes> types) {
	    Query<AnalyticsCategoryReport> query = currentSession().createNamedQuery("findAnalyticsReportByUserAndReportType",AnalyticsCategoryReport.class);
	    query.setParameter("user", user);
	    query.setParameterList("reportTypeList", types);
	    List<AnalyticsCategoryReport> ret= null;
	    Logger logger = LoggerFactory.getLogger(this.getClass());
	    try {ret=query.getResultList();}catch(Exception e) {
	    	logger.info("No analytics report found for userID: "+user.getIdDB()+" and types: "+types.toString());
	    	return new ArrayList<>();
	    }
	    return ret;
	}
}
