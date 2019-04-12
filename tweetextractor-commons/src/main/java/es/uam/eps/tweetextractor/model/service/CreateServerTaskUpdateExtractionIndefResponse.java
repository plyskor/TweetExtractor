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
public class CreateServerTaskUpdateExtractionIndefResponse {
	@XmlElement(name="error")
	private boolean error;
	@XmlElement(name="message")
	private String message;
	@XmlElement(name="id")
	private int id;
	/*
	* @param error the error to set
	 * @param message the message to set
	 * */
	public CreateServerTaskUpdateExtractionIndefResponse(boolean error, String message) {
		super();
		this.error = error;
		this.message = message;
	}
	public CreateServerTaskUpdateExtractionIndefResponse() {
		super();
	}
	@XmlTransient
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	@XmlTransient
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	
}
