/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReportVolumeRegister;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface TweetDAOInterface <T>{
	public List<TimelineReportVolumeRegister> extractGlobalTimelineVolumeReport(User user);
	public List<T> findByExtraction(Extraction extraction);

}