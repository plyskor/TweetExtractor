/**
 * 
 */
package es.uam.eps.tweetextractor.server;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.uam.eps.tweetextractor.spring.config.TweetExtractorSpringConfig;


/**
 * @author Jose Antonio García del Saz
 *
 */
public class TEServletContextListener implements ServletContextListener {
	private TweetExtractorServer server =null;
	private Logger logger = LoggerFactory.getLogger(TEServletContextListener.class);

	/**
	 * 
	 */
	public TEServletContextListener() {
		super();
	}

	  @Override
	  public void contextDestroyed(ServletContextEvent arg0) {
		  ServletContext context = arg0.getServletContext();
		  server=(TweetExtractorServer)context.getAttribute("Server");
		  if(this.server!=null) {
			  server.destroy();
		  }else {
			  logger.error("[contextDestroyed]:Server is null");
		  }
	  }

	  @Override
	  public void contextInitialized(ServletContextEvent arg0) {
		  AnnotationConfigApplicationContext tEServerSpringContext = 
	                new AnnotationConfigApplicationContext(TweetExtractorSpringConfig.class);
		  ServletContext context = arg0.getServletContext();
		  server = new TweetExtractorServer(tEServerSpringContext);
		  if(this.server!=null) {
			  this.server.initialize();
		  }else {
			  logger.error("[contextInitialized]:Server is null");
		  }
		  context.setAttribute("Server", server);
		  
	  }

	/**
	 * @return the server
	 */
	public TweetExtractorServer getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(TweetExtractorServer server) {
		this.server = server;
	}


}
