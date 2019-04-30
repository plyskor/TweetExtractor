/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTask;

/**
 * @author jose
 *
 */
public interface ServerTaskServiceInterface extends GenericServiceInterface<ServerTask, Integer>{
	public boolean hasAnyServerTask(User user);
	public List<ServerTask> findByUser(User user);
	public List<ServerTask> findByReport(AnalyticsCategoryReport report);
}
