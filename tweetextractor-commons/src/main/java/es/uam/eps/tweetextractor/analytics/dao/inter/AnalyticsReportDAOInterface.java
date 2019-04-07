package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;

public interface AnalyticsReportDAOInterface<T> {
	public List<T> findByUser(User user);
	public List<T> findByUserAndReportType(User user,List<AnalyticsReportTypes> types);

}
