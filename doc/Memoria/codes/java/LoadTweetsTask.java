/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class LoadTweetsTask extends TweetExtractorFXTask<Integer>{
	private Extraction extraction;
	private TweetServiceInterface tweetService;

	public LoadTweetsTask(AnnotationConfigApplicationContext springContext, Extraction extraction) {
		super(springContext);
		this.extraction = extraction;
		 tweetService= springContext.getBean(TweetServiceInterface.class);
	}

	@Override
	protected Integer call() throws Exception {
		if(extraction==null)return 0;
		List<Tweet>ret =tweetService.findByExtraction(this.getExtraction());
		if (ret==null)return 0;
		this.getExtraction().setTweetList(ret);
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Loaded "+ret.size() +"tweets from database");
		return ret.size();
	}

	public Extraction getExtraction() {
		return extraction;
	}

	public void setExtraction(Extraction extraction) {
		this.extraction = extraction;
	}

}
