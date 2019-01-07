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
	public TweetExtractorUtils() {
	}
	
	public static String buildServerAdress(String serverHost,String serverAppName, int port) {
		return new String("http://"+serverHost+":"+port+"/"+serverAppName+"/");
	}
}
