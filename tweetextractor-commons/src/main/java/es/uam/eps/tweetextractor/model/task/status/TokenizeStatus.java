/**
 * 
 */
package es.uam.eps.tweetextractor.model.task.status;
import es.uam.eps.tweetextractor.model.Constants;

/**
 * @author jose
 *
 */
public class TokenizeStatus {
	private int status= Constants.ERROR;
	private Exception exception = null;
	private int nTokens=0;
	/**
	 * 
	 */
	public TokenizeStatus() {
		super();
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
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}
	/**
	 * @return the nTokens
	 */
	public int getnTokens() {
		return nTokens;
	}
	/**
	 * @param nTokens the nTokens to set
	 */
	public void setnTokens(int nTokens) {
		this.nTokens = nTokens;
	}

}
