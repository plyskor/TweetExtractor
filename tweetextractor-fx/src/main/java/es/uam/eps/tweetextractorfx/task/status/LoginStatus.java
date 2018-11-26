/**
 * 
 */
package es.uam.eps.tweetextractorfx.task.status;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class LoginStatus {
	private User user;
	private Integer status= Constants.UNKNOWN_LOGIN_ERROR;
	/**
	 * 
	 */
	public LoginStatus() {
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer state) {
		this.status = state;
	}

}
