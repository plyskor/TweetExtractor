/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.impl;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportRegisterServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineVolumeReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;
import es.uam.eps.tweetextractorserver.model.servertask.AnalyticsServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractorserver.model.servertask.response.TimelineVolumeReportResponse;

/**
 * @author jose
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = TaskTypes.Values.TYPE_TASK_TIMELINE_VOLUME_REPORT)
@XmlRootElement(name = "serverTaskTimelineVolumeReport")
public class ServerTaskTimelineVolumeReport extends AnalyticsServerTask {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 2360564182683128202L;
	@Transient
	private TweetServiceInterface tServ;
	@Transient
	private AnalyticsReportServiceInterface arServ;
	@Transient
	private AnalyticsReportRegisterServiceInterface regServ;
	
	public ServerTaskTimelineVolumeReport() {
		super();
	}

	@Override
	public ServerTaskResponse call() {
		return new TimelineVolumeReportResponse(super.call());
	}

	@Override
	public void initialize(AnnotationConfigApplicationContext context) {
		this.springContext=context;
		tServ=springContext.getBean(TweetServiceInterface.class);
		arServ=springContext.getBean(AnalyticsReportServiceInterface.class);
		regServ=springContext.getBean(AnalyticsReportRegisterServiceInterface.class);
	}

	@Override
	public void implementation() {
		Logger logger = LoggerFactory.getLogger(ServerTaskTimelineVolumeReport.class);
		logger.info("Generating timeline tweet volume report...");
		if(this.report==null) {
			this.report=new TimelineVolumeReport();
			report.setUser(getUser());
		}
		List<TimelineReportVolumeRegister> list=tServ.extractGlobalTimelineVolumeReport(this.getUser());
		if(list==null) {
			logger.warn("There was an error querying the database while generating the report.");
			onInterrupt();
			return;
		}
		if (list.isEmpty()) {
			logger.info("Server task "+this.getId()+" has generated an empty timeline volume report. It hasn't been saved.");
			finish();
			return;
		}
		AnalyticsReportCategory nTweetsCategory= ((TimelineVolumeReport)report).getCategories().get(0);
		for(TimelineReportVolumeRegister register: list) {
			register.setCategory(nTweetsCategory);
		}
		permanentClearReport();
		nTweetsCategory.getResult().clear();
		nTweetsCategory.getResult().addAll(list);
		report.setLastUpdatedDate(new Date());
		arServ.update((AnalyticsCategoryReport)report);
		logger.info("Timeline tweet volume report succesfully saved to database with id: "+report.getId());
		finish();
	}

	

	private void permanentClearReport() {
		if(report!=null) {
			for(AnalyticsReportCategory category : ((TimelineVolumeReport)report).getCategories()) {
				for(AnalyticsReportRegister register : category.getResult()) {
					regServ.delete(register);
				}
			}
		}
	}


}
