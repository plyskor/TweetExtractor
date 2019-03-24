/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;
@NamedQuery(name="findServerTasksByUser", query="SELECT s from ServerTask s WHERE s.user=:user")

/**
 * @author jose
 *
 */
@Controller
@Entity
@Table(name="perm_server_task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Polymorphism(type = PolymorphismType.IMPLICIT)
@DiscriminatorColumn(name = "task_type",length=6, discriminatorType = DiscriminatorType.STRING)
public abstract class ServerTask implements Runnable,Serializable {
	@Autowired(required = true)
	@Transient
	protected transient ServerTaskServiceInterface sServ;
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 4811595948604961284L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int id;
	@Column(name = "status")
	private int status;
	@XmlTransient
	@Column(name = "task_type", length=5,nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	public TaskTypes taskType;
	@XmlTransient
	@ManyToOne
	private User user;
	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Column(name = "last_executed_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastExecutedDate;
	@XmlTransient
	@Transient
	private transient Thread thread = new Thread(this);
	@Column(name = "running_scheduled")
	private boolean runningScheduled;
	@Transient
	@XmlTransient
	protected transient AnnotationConfigApplicationContext springContext;
	protected boolean trigger=false;
	public ServerTask(int id, User user) {
		super();
		this.id = id;
		this.status = Constants.ST_NEW;
		this.runningScheduled=false;
		this.user = user;
		this.creationDate=new Date();
	}
	public ServerTask() {
		this.status = Constants.ST_NEW;
		this.creationDate=new Date();
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	public void goReady() {
		sServ=springContext.getBean(ServerTaskServiceInterface.class);
		if(this.status!=Constants.ST_RUNNING) {
			if(this.isRunningScheduled()) {
				this.setRunningScheduled(false);
			}
			this.status=Constants.ST_READY;
			sServ.update(this);
		}
	}
	public void finish() {
		sServ=springContext.getBean(ServerTaskServiceInterface.class);
		if(this.status==Constants.ST_RUNNING) {
			this.status=Constants.ST_FINISHED;
			sServ.update(this);
		}
	}
	public void onInterrupt() {
		sServ=springContext.getBean(ServerTaskServiceInterface.class);
		this.setStatus(Constants.ST_INTERRUPTED);
		 sServ.update(this);
	}
	
	public void onStop() {
		sServ=springContext.getBean(ServerTaskServiceInterface.class);
		this.setStatus(Constants.ST_STOPPED);
		sServ.update(this);
	}

	public void onStart() {
		sServ=springContext.getBean(ServerTaskServiceInterface.class);
		if(this.status==Constants.ST_READY||this.status==Constants.ST_SCHEDULED) {
			this.status=Constants.ST_RUNNING;
			if(this.isRunningScheduled()) {
				this.setThread(Thread.currentThread());
			}
			sServ.update(this);
		}
	}
	/**
	 * @return the taskType
	 */
	public TaskTypes getTaskType() {
		return taskType;
	}
	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(TaskTypes taskType) {
		this.taskType = taskType;
	}
	public ServerTaskResponse call() {
		ServerTaskResponse ret =new ServerTaskResponse();
		if (this.getStatus() == Constants.ST_RUNNING) {
			ret.setError(true);
			ret.setMessage("Task is currently running.");
			return ret;
		}
		this.setThread(new Thread(this));
		this.getThread().setName("tweetextractor-server:ServerTask-" + this.getId());
		this.getThread().setDaemon(true);
		ret.setError(false);
		ret.setMessage("Task is ready to run.");
		this.goReady();
		if (this.getStatus() != Constants.ST_READY) {
			ret.setError(true);
			ret.setMessage("Task is not ready to be called.");
			return ret;
		}
		return ret;
	}
	/**
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}
	/**
	 * @param thread the thread to set
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	/**
	 * @return the trigger
	 */
	public boolean isTrigger() {
		return trigger;
	}
	/**
	 * @param trigger the trigger to set
	 */
	public void setTrigger(boolean trigger) {
		this.trigger = trigger;
	}
	
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the lastExecutedDate
	 */
	public Date getLastExecutedDate() {
		return lastExecutedDate;
	}
	/**
	 * @param lastExecutedDate the lastExecutedDate to set
	 */
	public void setLastExecutedDate(Date lastExecutedDate) {
		this.lastExecutedDate = lastExecutedDate;
	}
	public abstract void initialize(AnnotationConfigApplicationContext context);
	public abstract void implementation() ;
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
