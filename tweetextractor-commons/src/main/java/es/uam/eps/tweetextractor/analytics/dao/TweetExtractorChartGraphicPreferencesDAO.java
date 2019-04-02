/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao;

import java.util.List;

import es.uam.eps.tweetextractor.analytics.dao.inter.TweetExtractorChartGraphicPreferencesDAOInterface;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;

/**
 * @author jgarciadelsaz
 *
 */
public class TweetExtractorChartGraphicPreferencesDAO extends AbstractGenericDAO<TweetExtractorChartGraphicPreferences, Integer>
		implements TweetExtractorChartGraphicPreferencesDAOInterface<TweetExtractorChartGraphicPreferences> {

	@Override
	public List<TweetExtractorChartGraphicPreferences> findByUserAndChartType(User user,AnalyticsReportImageTypes chartType) {
		return null;
	}

}
