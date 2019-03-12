/**
 * 
 */
package es.uam.eps.tweetextractor.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.servertask.ExtractionServerTask;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.servertask.ServerTaskInfo;
import es.uam.eps.tweetextractor.model.servertask.impl.ServerTaskTimelineVolumeReport;
import es.uam.eps.tweetextractor.model.servertask.response.ServerTaskResponse;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Controller
public class TweetExtractorServer {
	ExtractionServiceInterface eServ;
	ServerTaskServiceInterface stServ;
	private List<ServerTask> serverTaskList = new ArrayList<>();
	/*Initialize logger*/
	private Logger logger = LoggerFactory.getLogger(TweetExtractorServer.class);
	private AnnotationConfigApplicationContext springContext;
	/**
	 * @param tEServerSpringContext 
	 * 
	 */
	
	public TweetExtractorServer(AnnotationConfigApplicationContext tEServerSpringContext) {
		springContext=tEServerSpringContext;
		eServ=springContext.getBean(ExtractionServiceInterface.class);
		stServ=springContext.getBean(ServerTaskServiceInterface.class);
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
				haltTask(task);
			}
		}
	}
	public void initialize() {
		/*Load all tasks from database*/
		serverTaskList.addAll(stServ.findAll());
		//testing();
		/*Launch all ready tasks*/
		for (ServerTask task : serverTaskList) {
			task.initialize(springContext);
			launchServerTask(task);
		}
		
	}
	public void testing () {
		ServerTaskTimelineVolumeReport taskAux = new ServerTaskTimelineVolumeReport();
		taskAux.setUser(serverTaskList.get(10).getUser());
		stServ.persist(taskAux);
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
			if(task!=null&&task.getStatus()==Constants.ST_RUNNING) {
				logger.info("Trying to interrupt task with id "+task.getId()+" ...");
				task.getThread().interrupt();
				try {
					task.getThread().join();
				} catch (InterruptedException e) {
					logger.error(Arrays.toString(e.getStackTrace()));
					Thread.currentThread().interrupt();
				}
			}
	}
	
	public void interruptTask(ServerTask task) {
			if(task!=null&&task.getStatus()==Constants.ST_RUNNING) {
				logger.info("Trying to interrupt task with id "+task.getId()+" ...");
				task.getThread().interrupt();
				try {
					task.getThread().join();
				} catch (InterruptedException e) {
					logger.error(Arrays.toString(e.getStackTrace()));
					Thread.currentThread().interrupt();
				}
			}
	}
	public void haltTask(ServerTask task) {
			if(task!=null&&task.getStatus()==Constants.ST_RUNNING) {
				task.getThread().interrupt();
				try {
					task.getThread().join();
					task.setStatus(Constants.ST_HALT);
					stServ.update(task);
					
				} catch (InterruptedException e) {
					logger.error(Arrays.toString(e.getStackTrace()));
					Thread.currentThread().interrupt();
				}
			}
	}
	public void addTaskToServer(ServerTask task) {
		if(task!=null&&serverTaskList!=null) {
			task.initialize(springContext);
			logger.info("Task with id "+task.getId()+" has been added to Server instance.");
			serverTaskList.add(task);
		}
	}
	
	public void deleteTaskFromServer(ServerTask task) {
		if(task!=null&&serverTaskList!=null) {
			logger.info("Task with id "+task.getId()+" has been deleted from Server instance.");
			serverTaskList.remove(task);
		}
		
	}
	public ServerTaskResponse launchServerTask(ServerTask task) {
		/*Response object for calls*/
		ServerTaskResponse response = null;
		if (task.getStatus() == Constants.ST_READY||task.getStatus() ==Constants.ST_HALT) {
			try {
			if(ExtractionServerTask.class.isAssignableFrom(task.getClass())) {
				eServ.refresh(((ExtractionServerTask)task).getExtraction());
				if (((ExtractionServerTask)task).getExtraction().isExtracting()) {
					response=new ServerTaskResponse();
					response.setError(true);
					response.setMessage("Another task is updating this extraction");
					return response;
				}
			}
				response = task.call();
				if (response!=null&&!response.isError()) {
					task.setStatus(Constants.ST_RUNNING);
					stServ.update(task);
					task.getThread().start();
				}
			} catch (Exception e1) {
				logger.error("Exception calling task with id:"+task.getId()+" :\n"+Arrays.toString(e1.getStackTrace()));
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

	
	public List<ServerTaskInfo> getUserServerTasksInfo(int userId){
		List<ServerTaskInfo> ret = new ArrayList<>();
		if(serverTaskList==null||serverTaskList.isEmpty())return ret;
		for(ServerTask task:serverTaskList) {
			if(task.getUser().getIdDB()==userId) {
				String extractionSummary="";
				if(ExtractionServerTask.class.isAssignableFrom(task.getClass())) {
					extractionSummary=((ExtractionServerTask)task).getExtraction().getFiltersColumn();
					ret.add(new ServerTaskInfo(task.getId(), task.getStatus(), task.getTaskType(),extractionSummary,((ExtractionServerTask)task).getExtraction().getIdDB()));
				}
				ret.add(new ServerTaskInfo(task.getId(), task.getStatus(), task.getTaskType(),extractionSummary,0));
			}
		}
		return ret;
	}

	public AnnotationConfigApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(AnnotationConfigApplicationContext springContext) {
		this.springContext = springContext;
	}
	
}
