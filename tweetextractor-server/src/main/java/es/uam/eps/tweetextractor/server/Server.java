/**
 * 
 */
package es.uam.eps.tweetextractor.server;

import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	/*Initialize logger*/
	private Logger logger = LoggerFactory.getLogger(Server.class);
	/**
	 * 
	 */
	
	public Server() {
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

	public void destroy() {
		if(serverTaskList!=null) {
			for(ServerTask task : serverTaskList) {
				interruptTask(task);
			}
		}
	}
	public void initialize() {
		/*Load all tasks from database*/
		ServerTaskService stService =new ServerTaskService();
		serverTaskList.addAll(stService.findAll());
		/*Response object for calls*/
		ServerTaskResponse response = null;
		
		/*Launch all ready tasks*/
		for (ServerTask task : serverTaskList) {
			task.initialize();
			if (task.getStatus() == Constants.ST_READY) {
				try {
					response = task.call();
					if (response!=null&&response.isError()==false) {
						task.setStatus(Constants.ST_RUNNING);
						stService.update(task);
						task.getThread().start();
					}
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
	public ServerTask findById(int id) {
		if(id<=0)return null;
		for(ServerTask task:getServerTaskList()) {
			if(task.getId()==id)return task;
		}
		return null;
	}
	public void interruptTask(int id) {
		ServerTask task= findById(id);
		if(task!=null) {
			if(task.getStatus()==Constants.ST_RUNNING) {
				task.getThread().interrupt();
				try {
					task.getThread().join();
				} catch (InterruptedException e) {
					logger.error(e.getStackTrace().toString());
				}
			}
		}
	}
	public void interruptTask(ServerTask task) {
		if(task!=null) {
			if(task.getStatus()==Constants.ST_RUNNING) {
				task.getThread().interrupt();
				try {
					task.getThread().join();
				} catch (InterruptedException e) {
					logger.error(e.getStackTrace().toString());
				}
			}
		}
	}
	
}
