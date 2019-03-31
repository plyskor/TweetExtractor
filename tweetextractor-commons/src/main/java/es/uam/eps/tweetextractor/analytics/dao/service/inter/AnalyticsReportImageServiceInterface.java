/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;

/**
 * @author jose
 *
 */
public interface AnalyticsReportImageServiceInterface extends GenericServiceInterface<AnalyticsReportImage, Integer>{
	public List<AnalyticsReportImage> findByUser(User user);
}
