/**
 * 
 */
package es.uam.eps.tweetextractor.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import es.uam.eps.tweetextractor.model.servertask.ServerTaskInfo;

/**
 * @author Jose Antonio García del Saz
 *
 */
@XmlRootElement(name="GetUserServerTasksResponse")
@XmlType(name="model")
public class GetUserServerTasksResponse {
	@XmlElement(name="error")
	private boolean error;
	@XmlElementWrapper(name = "serverTasksInfoList") 
	@XmlElement(name = "serverTaskInfo")
	private List<ServerTaskInfo> serverTasksList=new ArrayList<>();
	@XmlElement(name="message")
	private String message;
	
	/**
	 * 
	 */
	public GetUserServerTasksResponse() {
		super();
	}

	/**
	 * @return the error
	 */
	@XmlTransient
	public boolean isError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * @return the serverTasksList
	 */
	@XmlTransient
	public List<ServerTaskInfo> getServerTasksList() {
		return serverTasksList;
	}

	/**
	 * @param serverTasksList the serverTasksList to set
	 */
	
	public void setServerTasksList(List<ServerTaskInfo> serverTasksList) {
		this.serverTasksList = serverTasksList;
	}

	/**
	 * @return the message
	 */
	@XmlTransient
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
