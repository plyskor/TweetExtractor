package es.uam.eps.tweetextractor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;


import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.AnalyticsReportDAOInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

@Repository
public class AnalyticsReportDAO extends AbstractGenericDAO<AnalyticsReport,Integer> implements AnalyticsReportDAOInterface<AnalyticsReport> {

	public AnalyticsReportDAO() {
		super();
	}
	public List<AnalyticsReport> findByUser(User user) {
	    Query<AnalyticsReport> query = currentSession().createNamedQuery("findByUser",AnalyticsReport.class);
	    query.setParameter("user", user);
	     List<AnalyticsReport> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No analytics report found for userID: "+user.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}
}
