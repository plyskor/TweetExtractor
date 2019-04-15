/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;

/**
 * @author jose
 *
 */
public interface AnalyticsReportRegisterServiceInterface extends GenericServiceInterface<AnalyticsReportRegister, Integer> {
	public List<AnalyticsReportRegister> findByReport(AnalyticsReport report);
	public List<AnalyticsReportCategoryRegister> findAnalyticsReportCategoryRegisterByCategory(AnalyticsReportCategory category) ;
}
