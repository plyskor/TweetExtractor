/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author jose
 *
 */
public interface AnalyticsReportImageDAOInterface<T> {
	public List<T> findByUser(User user);
}
