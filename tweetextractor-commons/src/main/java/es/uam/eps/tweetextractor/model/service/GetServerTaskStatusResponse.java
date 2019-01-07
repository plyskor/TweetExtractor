/**
 * 
 */
package es.uam.eps.tweetextractor.model.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Jose Antonio García del Saz
 *
 */
@XmlType(name="model")
public class GetServerTaskStatusResponse {
	@XmlElement(name="status")
	private int status;
	@XmlElement(name="error")
	private boolean error;
	@XmlElement(name="message")
	private String message;
	public GetServerTaskStatusResponse(int status, boolean error, String message) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
	}
	
	/**
	 * 
	 */
	public GetServerTaskStatusResponse() {
		super();
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

	

}
