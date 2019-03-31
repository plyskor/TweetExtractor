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

import es.uam.eps.tweetextractor.analytics.dao.inter.AnalyticsReportImageDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;

/**
 * @author jose
 *
 */
@Repository
public class AnalyticsReportImageDAO extends AbstractGenericDAO<AnalyticsReportImage, Integer> implements AnalyticsReportImageDAOInterface<AnalyticsReportImage> {
	public AnalyticsReportImageDAO() {
		super();
	}

	@Override
	public List<AnalyticsReportImage> findByUser(User user) {
		Query<AnalyticsReportImage> query = currentSession().createNamedQuery("findAnalyticsReportImageByUser",AnalyticsReportImage.class);
	    query.setParameter("user", user);
	     List<AnalyticsReportImage> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No analytics report graphics found for userID: "+user.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
