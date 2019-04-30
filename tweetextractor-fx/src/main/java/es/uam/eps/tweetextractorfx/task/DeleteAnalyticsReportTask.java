/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.service.DeleteServerTask;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTask;

/**
 * @author jgarciadelsaz
 *
 */
public class DeleteAnalyticsReportTask extends TwitterExtractorFXTask<Integer> {
	
	private AnalyticsReportServiceInterface arServ;
	private ServerTaskServiceInterface stServ;
	private AnalyticsCategoryReport report;
	public DeleteAnalyticsReportTask(AnnotationConfigApplicationContext springContext) {
		super(springContext);
		arServ = this.springContext.getBean(AnalyticsReportServiceInterface.class);
		stServ = this.springContext.getBean(ServerTaskServiceInterface.class);

	}
	/**
	 * @param springContext
	 * @param report
	 */
	public DeleteAnalyticsReportTask(AnnotationConfigApplicationContext springContext, AnalyticsCategoryReport report) {
		super(springContext);
		this.report = report;
		arServ = this.springContext.getBean(AnalyticsReportServiceInterface.class);
		stServ = this.springContext.getBean(ServerTaskServiceInterface.class);
	}

	@Override
	protected Integer call() throws Exception {
		if(report==null) {
			return Constants.ERROR;
		}
		List<ServerTask> associatedServerTasks = stServ.findByReport(report);
		DeleteServerTask service = new DeleteServerTask(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		for (ServerTask associatedServerTask: associatedServerTasks) {
			service.deleteServerTask(associatedServerTask.getId());
		}
		arServ.delete(report);
		return Constants.SUCCESS;
	}
	
}
