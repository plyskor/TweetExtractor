/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask;

import java.util.Date;

import javax.persistence.CascadeType;
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

import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportRegisterServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;

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
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = true)
	protected AnalyticsCategoryReport report;
	@Transient
	@XmlTransient
	protected AnalyticsReportRegisterServiceInterface regServ;
	@Transient
	@XmlTransient
	protected AnalyticsReportServiceInterface arServ;
	@Transient
	@XmlTransient
	protected TweetServiceInterface tServ;
	@Transient
	@XmlTransient
	protected ExtractionServiceInterface eServ;

	/**
	 * @param id   the id to set
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Logger logger = LoggerFactory.getLogger(AnalyticsServerTask.class);
		this.setLastExecutedDate(new Date());
		onStart();
		if (Thread.currentThread().isInterrupted()) {
			logger.info("The task with id: " + this.getId() + " has been interrupted.");
			onStop();
		}
		logger.info("Starting execution of task with id: " + this.getId());
		try {
			/* Try execution catching any exception */
			initializeReport();
			implementation();
		} catch (Exception e) {
			/* In case of exception, try to recover */
			logger.warn("An exception has been thrown in task with id " + this.getId() + "\n" + e.getMessage()
					+ ".\nInterrupting task...");
			onInterrupt();
		}
		/* If task finished without interruptions */
		if (this.getStatus() == Constants.ST_RUNNING) {
			finish();
		}
	}

	/**
	 * @return the report
	 */
	public AnalyticsCategoryReport getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsCategoryReport report) {
		this.report = report;
	}
	public void initializeReport() {
		if(this.report!=null) {
			arServ.refresh(report);
		}
	}

	protected void permanentClearReport() {
		if (report != null) {
			for (AnalyticsReportCategory category : ((AnalyticsCategoryReport) report).getCategories()) {
				for (AnalyticsReportRegister register : category.getResult()) {
					regServ.delete(register);
				}
				category.getResult().clear();
			}
			arServ.saveOrUpdate(report);
		}
	}

}
