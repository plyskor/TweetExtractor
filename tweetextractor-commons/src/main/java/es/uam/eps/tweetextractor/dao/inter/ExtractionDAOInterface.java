/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface ExtractionDAOInterface <T>{
	public List<T> findListById(List<Integer> extractions);
	public List<T> findListByReport(AnalyticsReport report);
	public List<T> findByUser(User user);
	public List<T> findListByNERTokenSet(TweetExtractorNERTokenSet tokenSet);	
	
}