/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask.response;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class ServerTaskResponse {
	private boolean error=false;
	private String message = "";
	/**
	 * 
	 */
	public ServerTaskResponse() {
		super();
	}
	/**
	 * @return the error
	 */
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
