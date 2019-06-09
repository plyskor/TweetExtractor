/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.impl;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportRegisterServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineTopNHashtagsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;
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
	public ServerTaskTopNHashtagsReport() {
		super();
		setLogger(LoggerFactory.getLogger(ServerTaskTopNHashtagsReport.class));
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
		tServ=springContext.getBean(TweetServiceInterface.class);
	}
	@Override
	public void implementation() {
		getLogger().info("Generating timeline Top "+((TimelineTopNHashtagsReport)report).getnHashtags()+" hashtags volume report...");
		TimelineTopNHashtagsReport castedReport = (TimelineTopNHashtagsReport) getReport();
		boolean emptyReport=true;
		permanentClearReport();
		for(AnalyticsReportCategory category: castedReport.getCategories()) {
			List<TimelineReportVolumeRegister> list = tServ.extractHashtagTimelineVolumeReport(this.getUser(), category.getCategoryName());
			if(list==null) {
				getLogger().warn("There was an error querying the database while generating the report.");
				onInterrupt();
				return;
			}
			if(!list.isEmpty()){
				emptyReport=false;
			}
			category.getResult().clear();
			for(TimelineReportVolumeRegister register: list) {
				register.setCategory(category);
				category.getResult().add(register);
			}
		}
		if (emptyReport) {
			getLogger().info("Server task "+this.getId()+" has generated an empty timeline volume report. It hasn't been saved.");
			finish();
			return;
		}
		report.setLastUpdatedDate(new Date());
		arServ.update(castedReport);
		getLogger().info("Timeline Top "+castedReport.getnHashtags()+" hashtags report succesfully saved to database with id: "+report.getId());
		finish();
	}

}
