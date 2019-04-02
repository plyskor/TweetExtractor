/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.User;


/**
 * @author jgarciadelsaz
 *
 */
public interface TweetExtractorChartGraphicPreferencesDAOInterface<T> {
	public List<T> findByUserAndChartType(User user,AnalyticsReportImageTypes chartType);
}
