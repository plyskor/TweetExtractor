package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;

public interface AnalyticsReportServiceInterface extends GenericServiceInterface<AnalyticsCategoryReport, Integer>{
	public List<AnalyticsCategoryReport> findByUser(User user);
	public List<AnalyticsCategoryReport> findByUserAndReportType(User user,List<AnalyticsReportTypes> types) ;
	public List<String> findStringFilterListByReport(TrendsReport report);
}
