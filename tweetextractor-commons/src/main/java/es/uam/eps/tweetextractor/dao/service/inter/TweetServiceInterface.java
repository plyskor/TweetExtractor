/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;

/**
 * @author jose
 *
 */
public interface TweetServiceInterface extends GenericServiceInterface<Tweet, Integer> {
	public List<TimelineReportVolumeRegister> extractHashtagTimelineVolumeReport(User user,String hashtag);
	public void persistList(List<Tweet> entityList);
	public List<Tweet> findByExtraction(Extraction extraction);
	public List<TimelineReportVolumeRegister> extractGlobalTimelineVolumeReport(User user);
	public List<String> findTopNHashtags(int n);
	public List<String> findTopNHashtagsFiltered(int n,List<String> filter);
}
