/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;

/**
 * @author jose
 *
 */
public interface AnalyticsReportRegisterDAOInterface<T> {
	public List<T> findByReport(AnalyticsReport report);
	public List<AnalyticsReportCategoryRegister> findAnalyticsReportCategoryRegisterByCategory(AnalyticsReportCategory category) ;
}
