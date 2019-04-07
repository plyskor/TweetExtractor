/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao;

import java.util.List;

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
		return null;
	}

}
