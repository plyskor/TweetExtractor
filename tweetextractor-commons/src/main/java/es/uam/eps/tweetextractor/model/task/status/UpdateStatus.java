/**
 * 
 */
package es.uam.eps.tweetextractor.model.task.status;

import java.util.List;

import es.uam.eps.tweetextractor.model.Tweet;
import javafx.scene.control.Alert;
import twitter4j.Status;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class UpdateStatus {
	private boolean error;
	private Integer nTweets;
	private Integer status;
	private List<Status> statusList=null;
	private List<Tweet>tweetList=null;
	private String errorMessage;
	/**
	 * 
	 */
	public UpdateStatus(Integer nTweets,Alert alert) {
		this.nTweets=nTweets;
	}
	/**
	 * @return the nTweets
	 */
	public Integer getnTweets() {
		return nTweets;
	}
	/**
	 * @param nTweets the nTweets to set
	 */
	public void setnTweets(Integer nTweets) {
		this.nTweets = nTweets;
	}

	/**
	 * @return the statusList
	 */
	public List<Status> getStatusList() {
		return statusList;
	}
	/**
	 * @param statusList the statusList to set
	 */
	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
	/**
	 * @return the tweetList
	 */
	public List<Tweet> getTweetList() {
		return tweetList;
	}
	/**
	 * @param tweetList the tweetList to set
	 */
	public void setTweetList(List<Tweet> tweetList) {
		this.tweetList = tweetList;
	}
	public void incrementNTweets(){
		this.nTweets=this.nTweets+1;
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
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
