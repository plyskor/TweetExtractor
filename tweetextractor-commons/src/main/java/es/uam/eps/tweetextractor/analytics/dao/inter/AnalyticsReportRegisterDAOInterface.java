/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

/**
 * @author jose
 *
 */
public interface AnalyticsReportRegisterDAOInterface<T> {
	public List<T> findByReport(AnalyticsReport report);
}
