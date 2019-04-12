/**
 * 
 */
package es.uam.eps.tweetextractorserver.twitterapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.task.status.UpdateStatus;
import es.uam.eps.tweetextractor.util.FilterManager;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Controller
public class ServerTwitterExtractor {
	TweetServiceInterface tweetService;
	private ConfigurationBuilder cb;
	private TwitterFactory tf;
	private Twitter twitter;
	private List<Credentials> credentialsList;
	private Query query;
	private Credentials currentCredentials;
	private Credentials lastReadyCredentials;
	private AnnotationConfigApplicationContext springContext;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * @param springContext the spring context to set 
	 */
	public ServerTwitterExtractor(AnnotationConfigApplicationContext springContext) {
		this.springContext=springContext;
		tweetService = this.springContext.getBean(TweetServiceInterface.class);
	}

	/**
	 * @return the cb
	 */
	public ConfigurationBuilder getCb() {
		return cb;
	}

	/**
	 * @param cb the cb to set
	 */
	public void setCb(ConfigurationBuilder cb) {
		this.cb = cb;
	}

	/**
	 * @return the tf
	 */
	public TwitterFactory getTf() {
		return tf;
	}

	/**
	 * @param tf the tf to set
	 */
	public void setTf(TwitterFactory tf) {
		this.tf = tf;
	}

	/**
	 * @return the twitter
	 */
	public Twitter getTwitter() {
		return twitter;
	}

	/**
	 * @param twitter the twitter to set
	 */
	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}



	/**
	 * @return the credentialsList
	 */
	public List<Credentials> getCredentialsList() {
		return credentialsList;
	}

	/**
	 * @param credentialsList the credentialsList to set
	 */
	public void setCredentialsList(List<Credentials> credentialsList) {
		this.credentialsList = credentialsList;
	}

	/**
	 * @return the query
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * @return the currentCredentials
	 */
	public Credentials getCurrentCredentials() {
		return currentCredentials;
	}

	/**
	 * @param currentCredentials the currentCredentials to set
	 */
	public void setCurrentCredentials(Credentials currentCredentials) {
		this.currentCredentials = currentCredentials;
		this.configure(currentCredentials);
	}

	/**
	 * @return the lastReadyCredentials
	 */
	public Credentials getLastReadyCredentials() {
		return lastReadyCredentials;
	}

	/**
	 * @param lastReadyCredentials the lastReadyCredentials to set
	 */
	public void setLastReadyCredentials(Credentials lastReadyCredentials) {
		this.lastReadyCredentials = lastReadyCredentials;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(Query query) {
		this.query = query;
	}
	
	public AnnotationConfigApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(AnnotationConfigApplicationContext springContext) {
		this.springContext = springContext;
	}

	public void initialize(Extraction extraction) {
		this.credentialsList=extraction.getUser().getCredentialList();
		if(this.credentialsList!=null&&!this.credentialsList.isEmpty()) {
			this.currentCredentials=this.getCredentialsList().get(0);
			this.lastReadyCredentials=currentCredentials;
			this.configure(currentCredentials);
			this.setQuery(FilterManager.getQueryFromFilters(extraction.getFilterList())+"-filter:retweets");
		}
	}
	public void configure(Credentials credentials) {
		if(credentials==null)return;
		/*Configuramos la API con nuestros datos provisionales*/
		ConfigurationBuilder confBuilder = new ConfigurationBuilder();
		confBuilder.setDebugEnabled(false).setOAuthConsumerKey(credentials.getConsumerKey())
		.setOAuthConsumerSecret(credentials.getConsumerSecret())
		.setOAuthAccessToken(credentials.getAccessToken()).setTweetModeExtended(true)
		.setOAuthAccessTokenSecret(credentials.getAccessTokenSecret());
		/*Instanciamos la conexión*/
		 tf = new TwitterFactory(confBuilder.build());
		twitter = tf.getInstance();
	}
	public UpdateStatus getStatusListExecution() {
		UpdateStatus ret= new UpdateStatus(0);
		List<Status>resultList=new ArrayList<>();
		try {
            QueryResult result;
            do {
            	ret.setStatus(Constants.SUCCESS_UPDATE);
            	ret.setError(false);
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
            	ret.setStatus(Constants.CONNECTION_UPDATE_ERROR);
            	ret.setErrorMessage(te.getErrorMessage());
            	ret.setError(true);
            }else 
            //*RATELIMIT
            if(te.getStatusCode()==429&&te.getErrorCode()==88) {
            	ret.setStatus(Constants.RATE_LIMIT_UPDATE_ERROR);
            	ret.setError(true);
            	if(!resultList.isEmpty()) {
                	ret.setStatus(Constants.SUCCESS_UPDATE);
            		ret.setStatusList(resultList);
                	ret.setErrorMessage(te.getErrorMessage());
            		return ret;
            	}
            }else {
            	ret.setStatus(Constants.UNKNOWN_UPDATE_ERROR);
            	ret.setError(true);
            	ret.setErrorMessage(te.getErrorMessage());
            	return ret;
            }
        }
		return ret;
	}
	public UpdateStatus execute(){
		List<Tweet> tweetList = new ArrayList<>();
		UpdateStatus ret;
		ret=getStatusListExecution();
		if(ret.isError())return ret;
		for(Status status : ret.getStatusList()) {
			tweetList.add(new Tweet(status));
		}
		ret.setTweetList(tweetList);
		return ret;
	}
	public UpdateStatus updateExtraction(Extraction extraction){
		if (extraction==null)return null;
		UpdateStatus ret=null;
		List<Tweet> toPersist=new ArrayList<>();
		ret= execute();
		if(ret.isError())return ret;
		for(Tweet tweet:ret.getTweetList()) {
			if(!extraction.contains(tweet)) {
				extraction.addTweet(tweet);
				ret.incrementNTweets();
				toPersist.add(tweet);
			}
		}
		if(ret.getnTweets()>0) {
			tweetService.persistList(toPersist);			
		}
		return ret;
	}
	public void setQuery(String query) {
		if(query==null)return;
		this.query=new Query(query);
	}
	public RateLimitStatus limit(String endpoint,Twitter twitter) {
		  try {
			return twitter.getRateLimitStatus().get(endpoint);
		} catch (TwitterException e) {
			logger.error(e.getMessage());
		}
		  return null;
		}
	public void nextCredentials() {
		if(credentialsList==null||currentCredentials==null)return;
		int actualIndex =credentialsList.indexOf(currentCredentials);
		if(actualIndex+1==credentialsList.size()) {
			currentCredentials=credentialsList.get(0);
		}else {
			currentCredentials=credentialsList.get(actualIndex+1);
		}
	}
	public int getMinSecondsBlocked() {
		if(credentialsList==null||credentialsList.isEmpty()) {
			return 0;
		}
		List<Integer> secondsList= new ArrayList<>();
		for(Credentials credentials:credentialsList){
			/*Configuramos la API con nuestros datos provisionales*/
			ConfigurationBuilder confBuild = new ConfigurationBuilder();
			confBuild.setDebugEnabled(false).setOAuthConsumerKey(credentials.getConsumerKey())
			.setOAuthConsumerSecret(credentials.getConsumerSecret())
			.setOAuthAccessToken(credentials.getAccessToken()).setTweetModeExtended(true)
			.setOAuthAccessTokenSecret(credentials.getAccessTokenSecret());
			/*Instanciamos la conexión*/
			TwitterFactory twFact = new TwitterFactory(confBuild.build());
			Twitter twttr = twFact.getInstance();
			secondsList.add(this.limit("/search/tweets",twttr).getSecondsUntilReset());
		}
		int newIndex=secondsList.indexOf(Collections.min(secondsList,null));
		this.setCurrentCredentials(credentialsList.get(newIndex));
		this.lastReadyCredentials=null;
		return Collections.min(secondsList,null);
	}
	public Tweet getTweetByID(Long tweetID) {
		 try {
		        Status status = twitter.showStatus(tweetID);
		        if (status == null) { // 
		            // don't know if needed - T4J docs are very bad
		        } else {
		        	return new Tweet(status);
		        }
		    } catch (TwitterException e) {
		    	Logger logger = LoggerFactory.getLogger(this.getClass());
		        logger.warn("Failed to search tweets: " + e.getMessage());
		        
		    }
		 return null;
	}
}
