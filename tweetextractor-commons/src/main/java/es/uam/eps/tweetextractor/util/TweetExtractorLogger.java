/**
 * 
 */
package es.uam.eps.tweetextractor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jose
 *
 */
public class TweetExtractorLogger {

	private Logger logger;
	
	public TweetExtractorLogger(Class<?> clazz) {
		logger=LoggerFactory.getLogger(clazz);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	
}
