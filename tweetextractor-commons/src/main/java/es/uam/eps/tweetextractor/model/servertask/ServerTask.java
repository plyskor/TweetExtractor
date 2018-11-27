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
import javax.xml.bind.annotation.XmlTransient;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;

/**
 * @author jose
 *
 */
@Entity
@Table(name="perm_server_task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "filter_type",length=6, discriminatorType = DiscriminatorType.STRING)
public abstract class ServerTask {
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
	
	public ServerTask(int id, int status, User user) {
		super();
		this.id = id;
		this.status = Constants.ST_NEW;
		this.user = user;
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

	
}
