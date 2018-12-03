/**
 * 
 */
package es.uam.eps.tweetextractor.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.dao.service.ExtractionService;
import es.uam.eps.tweetextractor.dao.service.ServerTaskService;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.servertask.response.ServerTaskResponse;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class Server {
	private List<ServerTask> serverTaskList = new ArrayList<ServerTask>();
	private ServerTaskService stService ;
	private ExtractionService eService;
	
	/**
	 * 
	 */
	public Server() {
		/*Load all tasks from database*/
		stService=new ServerTaskService();
		eService=new ExtractionService();
		serverTaskList.addAll(stService.findAll());
		/*Response object for calls*/
		ServerTaskResponse response = null;
		/*Initialize logger*/
		Logger logger = LoggerFactory.getLogger(Server.class);
		/*Launch all ready tasks*/
		for (ServerTask task : serverTaskList) {
			task.initialize();
			if (task.getStatus() == Constants.ST_READY) {
				try {
					response = task.call();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					logger.error("Exception calling task with id:"+task.getId()+":\n"+e1.getStackTrace().toString());
				}
				if (response != null) {
					logger.info("Calling server task with id:"+task.getId()+" returned message: "+response.getMessage());
				}
			}
		}
	}

	/**
	 * @return the serverTaskList
	 */
	public List<ServerTask> getServerTaskList() {
		return serverTaskList;
	}

	/**
	 * @param serverTaskList the serverTaskList to set
	 */
	public void setServerTaskList(List<ServerTask> serverTaskList) {
		this.serverTaskList = serverTaskList;
	}

}
