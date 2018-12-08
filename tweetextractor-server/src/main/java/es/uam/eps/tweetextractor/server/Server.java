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
import es.uam.eps.tweetextractor.model.servertask.ExtractionServerTask;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.servertask.ServerTaskInfo;
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
		/*Launch all ready tasks*/
		for (ServerTask task : serverTaskList) {
			task.initialize();
			launchServerTask(task);
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
				logger.info("Trying to interrupt task with id "+task.getId()+" ...");
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
				logger.info("Trying to interrupt task with id "+task.getId()+" ...");
				task.getThread().interrupt();
				try {
					task.getThread().join();
				} catch (InterruptedException e) {
					logger.error(e.getStackTrace().toString());
				}
			}
		}
	}
	
	public void addTaskToServer(ServerTask task) {
		if(task!=null||serverTaskList!=null) {
			logger.info("Task with id "+task.getId()+" has been added to Server instance.");
			serverTaskList.add(task);
		}
		return;
	}
	
	public void deleteTaskFromServer(ServerTask task) {
		if(task!=null&&serverTaskList!=null) {
			logger.info("Task with id "+task.getId()+" has been deleted from Server instance.");
			serverTaskList.remove(task);
		}
		
	}
	public ServerTaskResponse launchServerTask(ServerTask task) {
		ServerTaskService stService =new ServerTaskService();
		/*Response object for calls*/
		ServerTaskResponse response = null;
		if (task.getStatus() == Constants.ST_READY) {
			try {
				response = task.call();
				if (response!=null&&response.isError()==false) {
					task.setStatus(Constants.ST_RUNNING);
					stService.update(task);
					task.getThread().start();
				}
			} catch (Exception e1) {
				logger.error("Exception calling task with id:"+task.getId()+":\n"+e1.getStackTrace().toString());
				return response;
			}
			if (response != null) {
				logger.info("Calling server task with id:"+task.getId()+" returned message: "+response.getMessage());
			}
		return response;
		}else {
			response=new ServerTaskResponse();
			response.setError(true);
			response.setMessage("Task "+task.getId()+" is not ready to run.");
		}
		return response;
	}
	public void reinitializeTask(ServerTask task) {
		if(task!=null) {
			if(task.getStatus()==Constants.ST_INTERRUPTED||task.getStatus()==Constants.ST_FINISHED) {
				
			}
		}
		return;
	}
	public List<ServerTaskInfo> getUserServerTasksInfo(int userId){
		List<ServerTaskInfo> ret = new ArrayList<ServerTaskInfo>();
		if(serverTaskList==null||serverTaskList.isEmpty())return ret;
		for(ServerTask task:serverTaskList) {
			if(task.getUser().getIdDB()==userId) {
				String extractionSummary="";
				if(ExtractionServerTask.class.isAssignableFrom(task.getClass())) {
					extractionSummary=((ExtractionServerTask)task).getExtraction().getFiltersColumn();
				}
				ret.add(new ServerTaskInfo(task.getId(), task.getStatus(), task.getTaskType(),extractionSummary));
			}
		}
		return ret;
	}
}
