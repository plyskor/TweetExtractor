/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.TweetService;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import javafx.concurrent.Task;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class LoadTweetsTask extends Task<Integer>{
	private Extraction extraction;
	/**
	 * 
	 */
	public LoadTweetsTask(Extraction extraction) {
		this.setExtraction(extraction);
	}

	@Override
	protected Integer call() throws Exception {
		if(extraction==null)return 0;
		TweetService tweetService= new TweetService();
		List<Tweet>ret =tweetService.findByExtraction(this.getExtraction());
		if (ret==null)return 0;
		this.getExtraction().setTweetList(ret);
		System.out.println("Loaded "+ret.size() +"tweets from database");
		return ret.size();
	}

	/**
	 * @return the extraction
	 */
	public Extraction getExtraction() {
		return extraction;
	}

	/**
	 * @param extraction the extraction to set
	 */
	public void setExtraction(Extraction extraction) {
		this.extraction = extraction;
	}

}
