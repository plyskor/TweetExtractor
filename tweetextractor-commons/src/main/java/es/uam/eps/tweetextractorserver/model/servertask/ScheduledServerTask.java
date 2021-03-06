/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;

/**
 * @author jose
 *
 */
@Controller
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value=TaskTypes.Values.TYPE_SCHEDULED_TASK)
public abstract class ScheduledServerTask extends ServerTask {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -145203305639763354L;
	@Column(name = "schedule_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleDate;
	@Transient
	@XmlTransient
	private ScheduledFuture<?> future=null;
	@Column(name = "running_scheduled")
	private boolean runningScheduled;
	/**
	 * @param id the id to set
	 * @param user the user to set
	 */
	public ScheduledServerTask(int id, User user) {
		super(id, user);
		this.runningScheduled=false;
	}

	/**
	 * 
	 */
	public ScheduledServerTask() {
		super();
		this.runningScheduled=false;
	}


	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.servertask.ServerTask#initialize(org.springframework.context.annotation.AnnotationConfigApplicationContext)
	 */
	@Override
	public void initialize(AnnotationConfigApplicationContext context) {

	}
	@Override
	public void goReady() {
		sServ=springContext.getBean(ServerTaskServiceInterface.class);
		if(this.getStatus()!=Constants.ST_RUNNING) {
			if(this.isRunningScheduled()) {
				this.setRunningScheduled(false);
				if(this.getFuture()!=null) {
					this.getFuture().cancel(true);
					this.getLogger().info("The task with id "+this.getId()+" is no longer scheduled to run.");
				}
			}
			this.setStatus(Constants.ST_READY);
			sServ.update(this);
		}
	}
	/**
	 * @return the scheduleDate
	 */
	public Date getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param scheduleDate the scheduleDate to set
	 */
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public void onSchedule() {
		this.sServ=springContext.getBean(ServerTaskServiceInterface.class);
		if(this.getStatus()==Constants.ST_READY) {
			this.setStatus(Constants.ST_SCHEDULED);
			sServ.update(this);
		}
	}
	public void onOutdated() {
		sServ=springContext.getBean(ServerTaskServiceInterface.class);
		if(this.getStatus()==Constants.ST_SCHEDULED) {
			this.setStatus(Constants.ST_OUTDATED);
			this.setRunningScheduled(false);
			sServ.update(this);
		}
	}
	@Override
	public void onStart() {
		sServ=springContext.getBean(ServerTaskServiceInterface.class);
		if(this.getStatus()==Constants.ST_READY||this.getStatus()==Constants.ST_SCHEDULED) {
			this.setStatus(Constants.ST_RUNNING);
			if(this.isRunningScheduled()) {
				this.setThread(Thread.currentThread());
				this.setScheduleDate(null);
			}
			sServ.update(this);
		}
	}

	/**
	 * @return the future
	 */
	public ScheduledFuture<?> getFuture() {
		return future;
	}

	/**
	 * @param future the future to set
	 */
	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}

	/**
	 * @return the runningScheduled
	 */
	public boolean isRunningScheduled() {
		return runningScheduled;
	}

	/**
	 * @param runningScheduled the runningScheduled to set
	 */
	public void setRunningScheduled(boolean runningScheduled) {
		this.runningScheduled = runningScheduled;
	}

}
