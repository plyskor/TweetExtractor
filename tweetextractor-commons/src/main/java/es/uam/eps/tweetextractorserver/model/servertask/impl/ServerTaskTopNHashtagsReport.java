/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.impl;

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
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineTopNHashtagsReport;
import es.uam.eps.tweetextractorserver.model.servertask.AnalyticsServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractorserver.model.servertask.response.TopNHashtagsResponse;

/**
 * @author jose
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = TaskTypes.Values.TYPE_TASK_TIMELINE_TOP_N_HASHTAGS_REPORT)
@XmlRootElement(name = "serverTaskTimelineTopNHashtagsReport")
public class ServerTaskTopNHashtagsReport extends AnalyticsServerTask {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -7958677688349963763L;
	@Transient
	private AnalyticsReportServiceInterface arServ;
	@Transient
	private AnalyticsReportRegisterServiceInterface regServ;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(nullable=true)
	private TimelineTopNHashtagsReport report;
	
	public ServerTaskTopNHashtagsReport() {
		super();
	}
	@Override
	public ServerTaskResponse call() {
		return new TopNHashtagsResponse(super.call());
	}
	@Override
	public void initialize(AnnotationConfigApplicationContext context) {
		this.springContext=context;
		arServ=springContext.getBean(AnalyticsReportServiceInterface.class);
		regServ=springContext.getBean(AnalyticsReportRegisterServiceInterface.class);
	}
	@Override
	public void implementation() {
		Logger logger = LoggerFactory.getLogger(ServerTaskTopNHashtagsReport.class);
		logger.info("Generating timeline Top "+report.getnHashtags()+" hashtags volume report...");
		
	}
	/**
	 * @return the report
	 */
	public TimelineTopNHashtagsReport getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(TimelineTopNHashtagsReport report) {
		this.report = report;
	}
	
}
