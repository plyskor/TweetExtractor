/**
 * 
 */
package es.uam.eps.tweetextractor.server;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Jose Antonio García del Saz
 *
 */
public class TEServletContextListener implements ServletContextListener {
	private Server server =null;
	private Logger logger = LoggerFactory.getLogger(TEServletContextListener.class);
	/*@Autowired
	private ApplicationContext context;*/
	/**
	 * 
	 */
	public TEServletContextListener() {
	}

	  @Override
	  public void contextDestroyed(ServletContextEvent arg0) {
		  ServletContext context = arg0.getServletContext();
		  server=(Server)context.getAttribute("Server");
		  if(this.server!=null) {
			  server.destroy();
		  }else {
			  logger.error("[contextDestroyed]:Server is null");
		  }
	  }

	  @Override
	  public void contextInitialized(ServletContextEvent arg0) {
		  ServletContext context = arg0.getServletContext();
		  server = new Server();
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
	public Server getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(Server server) {
		this.server = server;
	}


}
