/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import es.uam.eps.tweetextractor.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReportVolumeRegister;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineVolumeReport;
import es.uam.eps.tweetextractor.model.servertask.AnalyticsServerTask;
import es.uam.eps.tweetextractor.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractor.model.servertask.response.TimelineVolumeReportResponse;
import net.bytebuddy.implementation.attribute.AnnotationAppender.Target.OnField;

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
	TweetServiceInterface tServ;
	@Transient
	AnalyticsReportServiceInterface arServ;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(nullable=true)
	private TimelineVolumeReport report;

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
		for(TimelineReportVolumeRegister register: list) {
			register.setReport(report);
		}
		report.getResult().clear();
		report.getResult().addAll(list);
		report.setLastUpdatedDate(new Date());
		arServ.update(report);
		logger.info("Timeline tweet volume report succesfully saved to database with id: "+report.getId());
		finish();
	}

	/**
	 * @return the report
	 */
	public TimelineVolumeReport getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(TimelineVolumeReport report) {
		this.report = report;
	}

}
