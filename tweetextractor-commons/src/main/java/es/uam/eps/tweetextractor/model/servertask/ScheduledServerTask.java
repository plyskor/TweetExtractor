/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask;

import java.util.Date;

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
	/**
	 * @param id
	 * @param user
	 */
	public ScheduledServerTask(int id, User user) {
		super(id, user);
	}

	/**
	 * 
	 */
	public ScheduledServerTask() {
		super();
	}


	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.servertask.ServerTask#initialize(org.springframework.context.annotation.AnnotationConfigApplicationContext)
	 */
	@Override
	public void initialize(AnnotationConfigApplicationContext context) {

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
	public void onOutdate() {
		this.sServ=springContext.getBean(ServerTaskServiceInterface.class);
		if(this.getStatus()==Constants.ST_SCHEDULED) {
			this.setStatus(Constants.ST_OUTDATED);
			sServ.update(this);
		}
	}


}
