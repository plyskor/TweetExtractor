package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

public interface AnalyticsReportServiceInterface extends GenericServiceInterface<AnalyticsReport, Integer>{
	public List<AnalyticsReport> findByUser(User user);
	public List<AnalyticsReport> findByUserAndReportType(User user,List<AnalyticsReportTypes> types) ;
}
