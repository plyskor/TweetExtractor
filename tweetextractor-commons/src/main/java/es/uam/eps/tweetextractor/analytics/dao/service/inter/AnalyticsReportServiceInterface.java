package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;

public interface AnalyticsReportServiceInterface extends GenericServiceInterface<AnalyticsCategoryReport, Integer>{
	public AnalyticsCategoryReport find(Integer key);
	public List<AnalyticsCategoryReport> findByUser(User user);
	public List<AnalyticsCategoryReport> findByUserAndReportType(User user,List<AnalyticsReportTypes> types) ;
	public List<String> findStringFilterListByReport(TrendsReport report);
	public List<AnalyticsReportCategory> getCategoriesByReport(AnalyticsCategoryReport report);
	

}
