/**
 * 
 */
package es.uam.eps.tweetextractor.model.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author jgarciadelsaz
 *
 */
@XmlType(name="model")
public class DeleteServerTaskResponse {
	@XmlElement(name="error")
	private boolean error;
	@XmlElement(name="message")
	private String message;
	/*
	* @param error the error to set
	 * @param message the message to set
	 * */
	public DeleteServerTaskResponse(boolean error, String message) {
		super();
		this.error = error;
		this.message = message;
	}
	public DeleteServerTaskResponse() {
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
	
	
}
