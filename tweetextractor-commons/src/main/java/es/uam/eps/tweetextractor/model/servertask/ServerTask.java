/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractor.dao.service.ServerTaskService;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;

/**
 * @author jose
 *
 */
@Entity
@Table(name="perm_server_task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Polymorphism(type = PolymorphismType.IMPLICIT)
@DiscriminatorColumn(name = "task_type",length=6, discriminatorType = DiscriminatorType.STRING)
public abstract class ServerTask implements Runnable {
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
	@XmlTransient
	@Transient
	private Thread thread = new Thread(this);
	private boolean trigger=false;
	public ServerTask(int id, int status, User user) {
		super();
		this.id = id;
		this.status = Constants.ST_NEW;
		this.user = user;
	}
	public ServerTask() {
		this.status = Constants.ST_NEW;
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
		if(this.status!=Constants.ST_RUNNING) {
			this.status=Constants.ST_READY;
			ServerTaskService stService = new ServerTaskService();
			stService.update(this);
		}
	}
	public void finish() {
		if(this.status==Constants.ST_RUNNING) {
			this.status=Constants.ST_FINISHED;
			ServerTaskService stService = new ServerTaskService();
			stService.update(this);
		}
	}
	public void onInterrupt() {
		this.setStatus(Constants.ST_INTERRUPTED);
		 ServerTaskService stServce = new ServerTaskService();
		stServce.update(this);
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
	public abstract ServerTaskResponse call();
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
	public abstract void initialize();
	public abstract void implementation() throws Exception;
}
