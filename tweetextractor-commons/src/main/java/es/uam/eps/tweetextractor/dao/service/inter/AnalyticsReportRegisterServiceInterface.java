/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReportRegister;

/**
 * @author jose
 *
 */
public interface AnalyticsReportRegisterServiceInterface extends GenericServiceInterface<AnalyticsReportRegister, Integer> {
	public List<AnalyticsReportRegister> findByReport(AnalyticsReport report);
}
