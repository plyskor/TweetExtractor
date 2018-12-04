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
	
}
