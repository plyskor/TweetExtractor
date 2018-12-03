/**
 * 
 */
package es.uam.eps.tweetextractor.context;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import es.uam.eps.tweetextractor.server.Server;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Configuration
public class TweetExtractorServerContext {

	/**
	 * 
	 */
	
	public TweetExtractorServerContext() {
		// TODO Auto-generated constructor stub
	}
	 
	@Bean("Server")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	   public Server getServer(){
		return new Server();
	   }
}
