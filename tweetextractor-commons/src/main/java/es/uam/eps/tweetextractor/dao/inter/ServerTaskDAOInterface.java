package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTask;

/**
 * @author Jose Antonio García del Saz
 *
 */
public interface ServerTaskDAOInterface <T>{
	public List<T> findByUser(User user);
	public List<ServerTask> findByReport(AnalyticsCategoryReport report);
}