/**
 * 
 */
package es.uam.eps.tweetextractor.model.task.status;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class RegisterStatus {
	private Integer status=Constants.UNKNOWN_REGISTER_ERROR;
	private User user=null;
	/**
	 * 
	 */
	public RegisterStatus() {
		super();
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
	public void setStatus(Integer status) {
		this.status = status;
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

}
