/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.Date;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.task.status.UpdateStatus;
import es.uam.eps.tweetextractorfx.twitterapi.TwitterExtractor;
import javafx.concurrent.Task;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class UpdateExtractionTask extends Task<UpdateStatus>{
	private TwitterExtractor twitter;
	private Extraction extraction;
	
	public UpdateExtractionTask(TwitterExtractor twitter, Extraction extraction) {
		super();
		this.twitter = twitter;
		this.extraction = extraction;
		
	}

	@Override
	protected UpdateStatus call() throws Exception {
		UpdateStatus ret =null;
		ret=twitter.updateExtraction(extraction);
		if(ret==null)return null;
		if(ret.getnTweets()>0) {
			extraction.setLastModificationDate(new Date());
			return ret ;
		}
		return ret;
	}
}
