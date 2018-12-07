/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask;



import javax.xml.bind.annotation.XmlTransient;

import es.uam.eps.tweetextractor.model.Constants.TaskTypes;

/**
 * @author joseantoniogarciadelsaz
 *
 */

public class ServerTaskInfo {
	private int id;
	private int status;
	public TaskTypes taskType;
	/**
	 * 
	 */
	public ServerTaskInfo() {
		super();
	}
	/**
	 * @param id
	 * @param status
	 * @param taskType
	 */
	public ServerTaskInfo(int id, int status, TaskTypes taskType) {
		super();
		this.id = id;
		this.status = status;
		this.taskType = taskType;
	}
	/**
	 * @return the id
	 */
	@XmlTransient
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
	@XmlTransient
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
	 * @return the taskType
	 */
	@XmlTransient
	public TaskTypes getTaskType() {
		return taskType;
	}
	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(TaskTypes taskType) {
		this.taskType = taskType;
	}
	
}
