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
import es.uam.eps.tweetextractor.analytics.dao.inter.TweetExtractorChartGraphicPreferencesDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;

/**
 * @author jgarciadelsaz
 *
 */
@Repository
public class TweetExtractorChartGraphicPreferencesDAO extends AbstractGenericDAO<TweetExtractorChartGraphicPreferences, Integer>
		implements TweetExtractorChartGraphicPreferencesDAOInterface<TweetExtractorChartGraphicPreferences> {

	public TweetExtractorChartGraphicPreferencesDAO() {
		super();
	}

	@Override
	public List<TweetExtractorChartGraphicPreferences> findByUserAndChartType(User user,AnalyticsReportImageTypes chartType) {
		Query<TweetExtractorChartGraphicPreferences> query = currentSession().createNamedQuery("findTweetExtractorChartGraphicPreferencesByUserAndChartType",TweetExtractorChartGraphicPreferences.class);
	    query.setParameter("user", user);
	    query.setParameter("chartType", chartType);
	     List<TweetExtractorChartGraphicPreferences> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No chart configurations found for userID: "+user.getIdDB()+" and chart type: "+chartType);
	    	return new ArrayList<>();
	    	}
	    return ret;	}

}
