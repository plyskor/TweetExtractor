/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;

/**
 * @author joseantoniogarciadelsaz
 *
 */

public class ServerTaskInfo {
	@XmlElement(name="id")
	private int id;
	@XmlElement(name="status")
	private int status;
	@XmlElement(name="type")
	public TaskTypes taskType;
	@XmlElement(name="extraction")
	private String extractionSummary;
	@XmlElement(name="extractionId")
	private int extractionId;
	/**
	 * 
	 */
	public ServerTaskInfo() {
		super();
	}
	
	/**
	 * @param id the id to set
	 * @param status the status to set
	 * @param taskType the tasktype to set
	 * @param extractionSummary the summary of the extraction to set
	 * @param extractionId id ID of the extraction to set
	 */
	public ServerTaskInfo(int id, int status, TaskTypes taskType, String extractionSummary, int extractionId) {
		super();
		this.id = id;
		this.status = status;
		this.taskType = taskType;
		this.extractionSummary = extractionSummary;
		this.extractionId = extractionId;
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
	/**
	 * @return the extractionSummary
	 */
	@XmlTransient
	public String getExtractionSummary() {
		return extractionSummary;
	}
	/**
	 * @param extractionSummary the extractionSummary to set
	 */
	public void setExtractionSummary(String extractionSummary) {
		this.extractionSummary = extractionSummary;
	}
	/**
	 * @return the extractionId
	 */
	@XmlTransient
	public int getExtractionId() {
		return extractionId;
	}
	/**
	 * @param extractionId the extractionId to set
	 */
	public void setExtractionId(int extractionId) {
		this.extractionId = extractionId;
	}
	
}
