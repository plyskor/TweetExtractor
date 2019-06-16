/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.task.status.UpdateStatus;
import es.uam.eps.tweetextractorfx.twitterapi.TwitterExtractor;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class UpdateExtractionTask extends TweetExtractorFXTask<UpdateStatus>{
	private TwitterExtractor twitter;
	private Extraction extraction;
	private Logger logger = LoggerFactory.getLogger(UpdateExtractionTask.class);

	/**
	 * @param springContext the springContext to set
	 * @param twitter the extractor to set
	 * @param extraction the extraction to set
	 */
	public UpdateExtractionTask(AnnotationConfigApplicationContext springContext, TwitterExtractor twitter,
			Extraction extraction) {
		super(springContext);
		this.twitter = twitter;
		this.extraction = extraction;
	}

	@Override
	protected UpdateStatus call() throws Exception {
		UpdateStatus ret =null;
		logger.info("Updating extraction with id "+extraction.getIdDB()+" ...");
		ret=twitter.updateExtraction(extraction);
		if(ret==null)return null;
		if(ret.getnTweets()>0) {
			extraction.setLastModificationDate(new Date());
			logger.info("Successfully added "+ret.getnTweets()+" to extraction with id "+extraction.getIdDB());
			return ret ;
		}
		return ret;
	}
}
