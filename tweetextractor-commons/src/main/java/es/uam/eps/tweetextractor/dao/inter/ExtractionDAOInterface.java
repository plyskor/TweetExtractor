/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface ExtractionDAOInterface <T>{
	public List<Extraction> findListById(List<Integer> extractions);
	public List<Extraction> findListByReport(AnalyticsReport report);
	public List<T> findByUser(User user);	
	
}