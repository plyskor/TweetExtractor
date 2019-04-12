/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.analytics.dao.inter.AnalyticsReportRegisterDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;

/**
 * @author jose
 *
 */
@Repository
public class AnalyticsReportRegisterDAO extends AbstractGenericDAO<AnalyticsReportRegister,Integer> implements AnalyticsReportRegisterDAOInterface<AnalyticsReportRegister>{

	/**
	 * 
	 */
	public AnalyticsReportRegisterDAO() {
		super();
	}

	@Override
	public List<AnalyticsReportRegister> findByReport(AnalyticsReport report) {
		Query<AnalyticsReportRegister> query = currentSession().createNamedQuery("findAnalyticsReportRegisterByReport",AnalyticsReportRegister.class);
	    query.setParameter("report", report);
	     List<AnalyticsReportRegister> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No registers found for reportID: "+report.getId());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
