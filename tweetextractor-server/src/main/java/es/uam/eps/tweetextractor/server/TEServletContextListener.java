/**
 * 
 */
package es.uam.eps.tweetextractor.server;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class TEServletContextListener implements ServletContextListener  {
	private Server server;
	/**
	 * 
	 */
	public TEServletContextListener() {
		// TODO Auto-generated constructor stub
	}

	  @Override
	  public void contextDestroyed(ServletContextEvent arg0) {
		  if(this.server!=null) {
			  for(ServerTask task:server.getServerTaskList()) {
				  if(task.getStatus()==Constants.ST_RUNNING) {
					  task.getThread().interrupt();
					  try {
						task.getThread().join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  }
			  }
		  }
	  }

	  @Override
	  public void contextInitialized(ServletContextEvent arg0) {
	    server = new Server();
	  }

}
