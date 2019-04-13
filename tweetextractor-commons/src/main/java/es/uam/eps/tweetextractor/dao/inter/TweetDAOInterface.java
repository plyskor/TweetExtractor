/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface TweetDAOInterface <T>{
	public List<TimelineReportVolumeRegister> extractHashtagTimelineVolumeReport(User user,String hashtag);
	public List<TimelineReportVolumeRegister> extractGlobalTimelineVolumeReport(User user);
	public List<T> findByExtraction(Extraction extraction);
	public List<String> findTopNHashtags(int n);
	public List<String> findTopNHashtagsFiltered(int n,List<String> filter);
}