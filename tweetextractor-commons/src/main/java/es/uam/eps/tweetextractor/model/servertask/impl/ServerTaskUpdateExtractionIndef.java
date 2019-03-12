/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.servertask.ExtractionServerTask;
import es.uam.eps.tweetextractor.model.servertask.response.UpdateExtractionIndefResponse;
import es.uam.eps.tweetextractor.model.task.status.UpdateStatus;
import es.uam.eps.tweetextractor.server.twitterapi.ServerTwitterExtractor;
import es.uam.eps.tweetextractor.util.FilterManager;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = TaskTypes.Values.TYPE_TASK_UPDATE_EXTRACTION_INDEF)
@XmlRootElement(name = "serverTaskUpdateExtractionIndef")
public class ServerTaskUpdateExtractionIndef extends ExtractionServerTask {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 5407409153873636491L;
	@Transient
	transient TweetServiceInterface tServ;
	
	/**
	 * 
	 */

	public ServerTaskUpdateExtractionIndef(Extraction e) {
		super(e);
	}

	/**
	 * 
	 */
	public ServerTaskUpdateExtractionIndef() {
		super();
	}

	@Override
	@Transient
	public UpdateExtractionIndefResponse call() {
		UpdateExtractionIndefResponse ret = new UpdateExtractionIndefResponse();
		if (this.extraction == null) {
			ret.setError(true);
			ret.setMessage("There is no extraction to update.");
			return ret;
		}
		return new UpdateExtractionIndefResponse(super.call());
	}

	@Override
	public void initialize(AnnotationConfigApplicationContext context) {
		this.springContext=context;
		eServ=springContext.getBean(ExtractionServiceInterface.class);
		tServ=springContext.getBean(TweetServiceInterface.class);
		this.extraction = eServ.findById(this.getExtractionId());
	}

	public void implementation() {
		Logger logger = LoggerFactory.getLogger(ServerTaskUpdateExtractionIndef.class);
		logger.info("Loading existing tweets...");
		List<Tweet> ret = tServ.findByExtraction(this.getExtraction());
		if (ret == null) {
			logger.error("An error has ocurred loading tweets. Task " + getId() + " has been interrupted");
			onInterrupt();
			return;
		}
		this.getExtraction().setTweetList(ret);
		logger.info("Successully loaded " + extraction.howManyTweets() + " tweets from database.");
		logger.info("Initializing TwitterExtractor...");
		ServerTwitterExtractor twitter = new ServerTwitterExtractor(springContext);
		twitter.initialize(extraction);
		logger.info("Starting infinite update of extraction with id: " + extraction.getIdDB());
		blockExtraction();
		while (!this.trigger) {
			/* Ckeck interruption */
			if (Thread.currentThread().isInterrupted()) {
				logger.info("The task with id: " + this.getId() + " has been interrupted.");
				onStop();
				return;
			}
			/* Iteration */
			extraction.refreshUserCredentials(springContext);
			twitter.setCredentialsList(extraction.getUser().getCredentialList());
			logger.info("Extracting with credentials @" + twitter.getCurrentCredentials().getAccountScreenName());
			twitter.setQuery(FilterManager.getQueryFromFilters(extraction.getFilterList()) + "-filter:retweets");
			UpdateStatus response = twitter.updateExtraction(extraction);
			if (response.isError()) {
				/* Error updating extraction */
				switch (response.getStatus()) {
				case Constants.SUCCESS_UPDATE:
					/* Rate Limit with tweets extracted */
					if (response.getnTweets() > 0) {
						logger.warn(
								"Rate Limit of the account " + twitter.getCurrentCredentials().getAccountScreenName()
										+ " has been reached, but " + response.getnTweets()
										+ " tweets has been added to the extraction with id: " + extraction.getIdDB());
					} else {
						logger.warn("Rate Limit of the account "
								+ twitter.getCurrentCredentials().getAccountScreenName() + " has been reached.");
					}
					twitter.setLastReadyCredentials(twitter.getCurrentCredentials());
					twitter.nextCredentials();
					break;
				case Constants.CONNECTION_UPDATE_ERROR:
					/* Connection error (network) */
					logger.error("An error has occurred connecting to Twitter: \n" + response.getErrorMessage());
					twitter.nextCredentials();
					break;
				case Constants.RATE_LIMIT_UPDATE_ERROR:
					/* Rate Limit with no tweets extracted */
					logger.warn("Rate Limit of the account " + twitter.getCurrentCredentials().getAccountScreenName()
							+ " has been reached.");
					twitter.nextCredentials();
					if (twitter.getCurrentCredentials().equals(twitter.getLastReadyCredentials())) {
						/* Sleep */
						try {
							int seconds = twitter.getMinSecondsBlocked();
							if (seconds == 0)
								break;
							logger.info(
									"All credentials are blocked. Waiting " + seconds + " seconds until next try...");
							TimeUnit.SECONDS.sleep(seconds);
						} catch (InterruptedException e) {
							logger.info("The task with id: " + this.getId() + " has been stppoed.");
							onStop();
							releaseExtraction();
							Thread.currentThread().interrupt();
							return;
						}
					}
					break;
				case Constants.UNKNOWN_UPDATE_ERROR:
					/* Unknown Twitter error */
					logger.error("An unkown error has occurred connecting to Twitter: \n" + response.getErrorMessage());
					twitter.nextCredentials();
					break;
				default:
					break;
				}
			} else {
				if (response.getnTweets() == 0) {
					/*
					 * No tweets available in the timeline, we wait so we dont block the credentials
					 * for nothing
					 */
					try {
						logger.info("No more tweets available at this moment for extraction with id: "
								+ extraction.getIdDB() + ". Waiting 15 minutes so the credentials don't get blocked.");
						TimeUnit.MINUTES.sleep(15);
					} catch (InterruptedException e) {
						logger.info("The task with id: " + this.getId() + " has been stopped.");
						onStop();
						releaseExtraction();
						Thread.currentThread().interrupt();
						return;
					}
				} else {
					/* Tweets extracted with no errors */
					logger.info("Succesfully added " + response.getnTweets() + " tweets to the extraction with id: "
							+ extraction.getIdDB());
					extraction.setLastModificationDate(new Date());
					eServ.update(extraction);
					twitter.setLastReadyCredentials(twitter.getCurrentCredentials());
				}
			}
		}
	}
}
