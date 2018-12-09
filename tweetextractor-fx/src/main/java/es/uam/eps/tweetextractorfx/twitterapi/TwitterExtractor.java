package es.uam.eps.tweetextractorfx.twitterapi;


import java.util.ArrayList;
import java.util.List;

import es.uam.eps.tweetextractor.dao.service.TweetService;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.task.status.UpdateStatus;
import es.uam.eps.tweetextractor.util.FilterManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterExtractor {
	private ConfigurationBuilder cb;
	private TwitterFactory tf;
	private Twitter twitter;
	private Credentials credentials;
	private Query query;
	private Integer updateStatus=null;
	private String errorMessage=null;
	public TwitterExtractor(String consulta,Credentials credentials) {
		super();
		if(credentials==null)return;
		/*Configuramos la API con nuestros datos provisionales*/
		setCredentials(credentials);
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(false).setOAuthConsumerKey(this.credentials.getConsumerKey())
		.setOAuthConsumerSecret(this.credentials.getConsumerSecret())
		.setOAuthAccessToken(this.credentials.getAccessToken()).setTweetModeExtended(true)
		.setOAuthAccessTokenSecret(this.credentials.getAccessTokenSecret());
		/*Instanciamos la conexión*/
		 tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	public Twitter getTwitter() {
		return twitter;
	}
	public Query getQuery() {
		return query;
	}
	public void setQuery(String query) {
		if(query!=null) {
			this.query = new Query(query);
		}
	}
	public UpdateStatus execute(){
		List<Tweet> tweetList = new ArrayList<Tweet>();
		UpdateStatus ret;
		ret=getStatusListExecution();
		if(ret.getStatusList()==null)return ret;
		for(Status status : ret.getStatusList()) {
			tweetList.add(new Tweet(status));
		}
		ret.setTweetList(tweetList);
		return ret;
	}
	public UpdateStatus getStatusListExecution() {
		UpdateStatus ret= new UpdateStatus(0, null);
		List<Status>resultList=new ArrayList<Status>();
		try {
            QueryResult result;
            do {
            	this.updateStatus=Constants.SUCCESS_UPDATE;
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                	resultList.add(tweet);
                }
            } while ((query = result.nextQuery()) != null);
            ret.setStatusList(resultList);
            return ret;
        } catch (TwitterException te) {
            //*CONNECTION ISSUE
            if(te.getStatusCode()==-1&&te.getErrorCode()==-1) {
            	this.updateStatus=Constants.CONNECTION_UPDATE_ERROR;
            }else 
            //*RATELIMIT
            if(te.getStatusCode()==429&&te.getErrorCode()==88) {
            	this.updateStatus=Constants.RATE_LIMIT_UPDATE_ERROR;
            	if(resultList!=null&&!resultList.isEmpty()) {
                	this.updateStatus=Constants.SUCCESS_UPDATE;
            		ret.setStatusList(resultList);
            		return ret;
            	}
            }else {
            	this.updateStatus=Constants.UNKNOWN_UPDATE_ERROR;
            	this.errorMessage=te.getErrorMessage();
            	return ret;
            }
        }
		return ret;
	}
	public Alert onError() {
		if(this.updateStatus==null)return null;
		switch(this.updateStatus) {
		case(Constants.CONNECTION_UPDATE_ERROR):
			return handleConnectionIssue();
		case(Constants.RATE_LIMIT_UPDATE_ERROR):
			return handleRateLimit();
		case(Constants.UNKNOWN_UPDATE_ERROR):
			return ErrorDialog.showErrorTwitterExecution(this.errorMessage);
		default:
			break;
		}
		return null;
	}
	public TwitterFactory getTf() {
		return tf;
	}
	public void setTf(TwitterFactory tf) {
		this.tf = tf;
	}
	public ConfigurationBuilder getCb() {
		return cb;
	}
	public void setCb(ConfigurationBuilder cb) {
		this.cb = cb;
	}
	public void setCredentials(Credentials credentials) {
			this.credentials=credentials;
	}
	public Alert handleRateLimit() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Account rate limit");
		alert.setContentText(
				"Twitter API rate limit has been reached.\nThe limit will be reseted in "+this.limit("/search/tweets").getSecondsUntilReset()+" seconds.\nPlease, add new credentials or try again later.");
		return alert;
	}
	public Alert handleConnectionIssue() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Connection error");
        alert.setContentText(
                      "An unknown error has occured while connecting with Twitter. Please check your network configuration and try again.");
        return alert;
	}
	public UpdateStatus updateExtraction(Extraction extraction){
		if (extraction==null)return null;
		this.setQuery(FilterManager.getQueryFromFilters(extraction.getFilterList())+"-filter:retweets");
		UpdateStatus ret=null;
		List<Tweet> toPersist= new ArrayList<Tweet>();
		ret= execute();
		if(ret.getTweetList()==null)return ret;
		for(Tweet tweet:ret.getTweetList()) {
			if(!extraction.contains(tweet)) {
				extraction.addTweet(tweet);
				ret.incrementNTweets();
				toPersist.add(tweet);
			}
		}
		if(ret.getnTweets()>0) {
			TweetService tweetService=new TweetService();
			tweetService.persistList(toPersist);
		}
		return ret;
	}
	public RateLimitStatus limit(String endpoint) {
		  try {
			  RateLimitStatus status = twitter.getRateLimitStatus().get(endpoint);
			return status;
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		  return null;
		}
	/**
	 * @return the updateStatus
	 */
	public Integer getUpdateStatus() {
		return updateStatus;
	}
	/**
	 * @param updateStatus the updateStatus to set
	 */
	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}
	
}
