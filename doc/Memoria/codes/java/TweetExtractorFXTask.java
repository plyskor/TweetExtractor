/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.concurrent.Task;

/**
 * @author jose
 *
 */
public abstract class TweetExtractorFXTask<V> extends Task<V> {

	protected AnnotationConfigApplicationContext springContext;

	public TweetExtractorFXTask(AnnotationConfigApplicationContext springContext) {
		super();
		this.springContext = springContext;
	}

	public AnnotationConfigApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(AnnotationConfigApplicationContext springContext) {
		this.springContext = springContext;
	}
	
}
