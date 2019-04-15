package es.uam.eps.tweetextractor.analytics.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.analytics.dao.inter.AnalyticsReportDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;

@Repository
public class AnalyticsReportDAO extends AbstractGenericDAO<AnalyticsCategoryReport,Integer> implements AnalyticsReportDAOInterface<AnalyticsCategoryReport> {

	public AnalyticsReportDAO() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.dao.AbstractGenericDAO#find(java.io.Serializable)
	 */
	@Override
	public AnalyticsCategoryReport find(Integer key) {
		AnalyticsCategoryReport ret =  super.find(key);
		Hibernate.initialize(ret.getCategories());
		for(AnalyticsReportCategory category : ret.getCategories()) {
			Hibernate.initialize(category.getResult());
		}
		return ret;
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
	@Override
	public List<String> findStringFilterListByReport(TrendsReport report) {
		Query q = currentSession().createSQLQuery("SELECT filter_element\n" + 
				"FROM perm_trend_report_filter_list\n" + 
				"WHERE report=:id").setParameter("id", report.getId());
		List<String>ret=null;
		try {
			ret=(List<String>)q.getResultList();
		}catch(Exception e ) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.warn(e.getMessage());
	    	logger.warn(Arrays.toString(e.getStackTrace()));
	    	return ret;
		}
		return ret;
	}
	@Override
	public List<AnalyticsReportCategory> getCategoriesByReport(AnalyticsCategoryReport report) {
	    Query<AnalyticsReportCategory> query = currentSession().createNamedQuery("findCategoriesByReport",AnalyticsReportCategory.class);
	    query.setParameter("id", report.getId());
	    List<AnalyticsReportCategory> ret= null;
	    Logger logger = LoggerFactory.getLogger(this.getClass());
	    try {ret=query.getResultList();}catch(Exception e) {
	    	logger.info("No categories found for report with ID: "+report.getId());
	    	return new ArrayList<>();
	    }
	    return ret;
	}
}
