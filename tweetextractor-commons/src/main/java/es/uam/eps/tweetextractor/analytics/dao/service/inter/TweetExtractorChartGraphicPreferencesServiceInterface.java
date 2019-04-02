/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;

/**
 * @author jgarciadelsaz
 *
 */
public interface TweetExtractorChartGraphicPreferencesServiceInterface extends GenericServiceInterface<TweetExtractorChartGraphicPreferences, Integer> {
	public List<TweetExtractorChartGraphicPreferences> findByUserAndChartType(User user,AnalyticsReportImageTypes chartType);
}
