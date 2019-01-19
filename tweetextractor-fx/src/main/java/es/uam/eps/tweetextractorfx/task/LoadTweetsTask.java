/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class LoadTweetsTask extends TwitterExtractorFXTask<Integer>{
	private Extraction extraction;
	/**
	 * 
	 */
	public LoadTweetsTask(Extraction extraction,AnnotationConfigApplicationContext context) {
		super(context);
		this.setExtraction(extraction);
	}

	@Override
	protected Integer call() throws Exception {
		if(extraction==null)return 0;
		TweetServiceInterface tweetService= springContext.getBean(TweetServiceInterface.class);
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
