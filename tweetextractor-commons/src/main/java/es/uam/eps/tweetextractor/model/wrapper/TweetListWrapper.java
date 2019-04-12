/**
 * 
 */
package es.uam.eps.tweetextractor.model.wrapper;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */
@XmlRootElement(name = "tweets")
public class TweetListWrapper {
	private List<Tweet> tweets;

	/**
	 * @return the tweets
	 */
	@XmlElement(name="tweet")
	public List<Tweet> getTweets() {
		return tweets;
	}

	/**
	 * @param tweets the tweets to set
	 */
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
}
