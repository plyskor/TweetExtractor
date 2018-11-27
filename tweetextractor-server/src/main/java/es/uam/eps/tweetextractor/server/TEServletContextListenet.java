/**
 * 
 */
package es.uam.eps.tweetextractor.server;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class TEServletContextListenet implements ServletContextListener  {

	/**
	 * 
	 */
	public TEServletContextListenet() {
		// TODO Auto-generated constructor stub
	}

	  @Override
	  public void contextDestroyed(ServletContextEvent arg0) {
		  
	  }

	  @Override
	  public void contextInitialized(ServletContextEvent arg0) {
	    Server server = new Server();
	  }

}
