/**
 * 
 */
package es.uam.eps.tweetextractor.model.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;

/**
 * @author jose
 *
 */
@XmlType(name="model")
public class ScheduleServerTaskResponse {
	@XmlElement(name="error")
	private boolean error;
	@XmlElement(name="message")
	private String message;
	@XmlElement(name="response")
	private ServerTaskResponse response;
	/**
	 * 
	 */
	public ScheduleServerTaskResponse() {
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
	/**
	 * @return the response
	 */
	@XmlTransient
	public ServerTaskResponse getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(ServerTaskResponse response) {
		this.response = response;
	}

}
