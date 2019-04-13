/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;

/**
 * @author jose
 *
 */
@Controller
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AnalyticsServerTask extends ScheduledServerTask {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 874178661084669884L;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(nullable=true)
	protected AnalyticsReport report;

	/**
	 * @param id the id to set
	 * @param user the user to set
	 */
	public AnalyticsServerTask(int id, User user) {
		super(id, user);
	}

	/**
	 * 
	 */
	public AnalyticsServerTask() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Logger logger = LoggerFactory.getLogger(AnalyticsServerTask.class);
		this.setLastExecutedDate(new Date());
		onStart();
		if(Thread.currentThread().isInterrupted()) {
			logger.info("The task with id: "+this.getId()+" has been interrupted.");
			onStop();
		}
		logger.info("Starting execution of task with id: " + this.getId());
		try {
			/*Try execution catching any exception*/
			implementation();
		}catch (Exception e) {
			/*In case of exception, try to recover*/
			logger.warn("An exception has been thrown in task with id "+this.getId()+"\n"+e.getMessage()+".\nInterrupting task...");
			onInterrupt();			
		}
		/*If task finished without interruptions*/
		if(this.getStatus()==Constants.ST_RUNNING) {
			finish();
		}
	}

	/**
	 * @return the report
	 */
	public AnalyticsReport getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsReport report) {
		this.report = report;
	}



}
