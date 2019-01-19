/**
 * 
 */
package es.uam.eps.tweetextractor.util;


/**
 * @author Jose Antonio García del Saz
 *
 */
public class TweetExtractorUtils {

	/**
	 * 
	 */
	private TweetExtractorUtils() {
		super();
	}
	
	public static String buildServerAdress(String serverHost,String serverAppName, int port) {
		return "https://"+serverHost+":"+port+"/"+serverAppName+"/";
	}
}
