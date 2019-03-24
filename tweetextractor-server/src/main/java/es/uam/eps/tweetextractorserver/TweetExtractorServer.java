/**
 * 
 */
package es.uam.eps.tweetextractorserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractorserver.model.servertask.ExtractionServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.ScheduledServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTaskInfo;
import es.uam.eps.tweetextractorserver.model.servertask.response.ScheduleTaskOnDateResponse;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractorserver.util.TweetExtractorThreadFactory;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Controller
public class TweetExtractorServer {
	ExtractionServiceInterface eServ;
	ServerTaskServiceInterface stServ;
	private List<ServerTask> serverTaskList = new ArrayList<>();
	/* Initialize logger */
	private Logger logger = LoggerFactory.getLogger(TweetExtractorServer.class);
	private AnnotationConfigApplicationContext springContext;
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Constants.MAX_SCHEDULED_SERVER_TASKS,
			new TweetExtractorThreadFactory());

	/**
	 * @param tEServerSpringContext
	 * 
	 */

	public TweetExtractorServer(AnnotationConfigApplicationContext tEServerSpringContext) {
		springContext = tEServerSpringContext;
		eServ = springContext.getBean(ExtractionServiceInterface.class);
		stServ = springContext.getBean(ServerTaskServiceInterface.class);
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
		if (serverTaskList != null) {
			for (ServerTask task : serverTaskList) {
				haltTask(task);
			}
		}
		if (scheduler != null) {
			scheduler.shutdown();
		}
	}

	public void initialize() {
		/* Load all tasks from database */
		logger.info("Loading server tasks from database...");
		serverTaskList.addAll(stServ.findAll());
		logger.info("Successfully loaded "+serverTaskList.size()+" tasks from database.");
		/* Launch all ready tasks */
		for (ServerTask task : serverTaskList) {
			task.initialize(springContext);
			launchServerTask(task);
		}
	}

	public ServerTask findById(int id) {
		if (id <= 0)
			return null;
		for (ServerTask task : getServerTaskList()) {
			if (task.getId() == id)
				return task;
		}
		return null;
	}

	public void interruptTask(int id) {
		ServerTask task = findById(id);
		if (task != null && task.getStatus() == Constants.ST_RUNNING) {
			logger.info("Trying to interrupt task with id " + task.getId() + " ...");
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
		if (task != null && task.getStatus() == Constants.ST_RUNNING) {
			logger.info("Trying to interrupt task with id " + task.getId() + " ...");
			task.getThread().interrupt();
			if (!task.isRunningScheduled()) {
				try {
					task.getThread().join();
				} catch (InterruptedException e) {
					logger.error(Arrays.toString(e.getStackTrace()));
					Thread.currentThread().interrupt();
				}
			} else {
				task.setRunningScheduled(false);
				stServ.update(task);
			}
		}
	}

	public void haltTask(ServerTask task) {
		if (task != null && task.getStatus() == Constants.ST_RUNNING) {
			task.getThread().interrupt();
			if (!task.isRunningScheduled()) {
				try {
					task.getThread().join();
					task.setStatus(Constants.ST_HALT);
					stServ.update(task);
				} catch (InterruptedException e) {
					logger.error(Arrays.toString(e.getStackTrace()));
					Thread.currentThread().interrupt();
				}
			} else {
				task.setStatus(Constants.ST_HALT);
				task.setRunningScheduled(false);
				stServ.update(task);
			}
		}
	}

	public void addTaskToServer(ServerTask task) {
		if (task != null && serverTaskList != null) {
			task.initialize(springContext);
			logger.info("Task with id " + task.getId() + " has been added to Server instance.");
			serverTaskList.add(task);
		}
	}

	public void deleteTaskFromServer(ServerTask task) {
		if (task != null && serverTaskList != null) {
			logger.info("Task with id " + task.getId() + " has been deleted from Server instance.");
			serverTaskList.remove(task);
		}

	}

	public ServerTaskResponse launchServerTask(ServerTask task) {
		/* Response object for calls */
		ServerTaskResponse response = null;
		switch (task.getStatus()) {
		case Constants.ST_READY:
		case Constants.ST_HALT:
			try {
				if (ExtractionServerTask.class.isAssignableFrom(task.getClass())) {
					eServ.refresh(((ExtractionServerTask) task).getExtraction());
					if (((ExtractionServerTask) task).getExtraction().isExtracting()) {
						response = new ServerTaskResponse();
						response.setError(true);
						response.setMessage("Another task is updating this extraction");
						return response;
					}
				}
				response = task.call();
				if (response != null && !response.isError()) {
					task.setStatus(Constants.ST_RUNNING);
					stServ.update(task);
					task.getThread().start();
				}
			} catch (Exception e1) {
				logger.error("Exception calling task with id:" + task.getId() + " :\n"
						+ Arrays.toString(e1.getStackTrace()));
				return response;
			}
			if (response != null) {
				logger.info(
						"Calling server task with id:" + task.getId() + " returned message: " + response.getMessage());
			}
			return response;
		case Constants.ST_SCHEDULED:
			ScheduledServerTask sTask = (ScheduledServerTask) task;
			if(sTask.getScheduleDate()!=null) {
				if(!sTask.getScheduleDate().before(new Date())) {
					long delay = sTask.getScheduleDate().getTime() - System.currentTimeMillis();
					scheduler.schedule(sTask, delay, TimeUnit.MILLISECONDS);
				}else {
					sTask.onOutdated();
					response = new ServerTaskResponse();
					response.setError(true);
					response.setMessage("Task " + sTask.getId() + " is outdated.");
				}
			}
			break;
		default:
			response = new ServerTaskResponse();
			response.setError(true);
			response.setMessage("Task " + task.getId() + " is not ready to run.");
			break;
		}
		return response;
	}

	public List<ServerTaskInfo> getUserServerTasksInfo(int userId) {
		List<ServerTaskInfo> ret = new ArrayList<>();
		if (serverTaskList == null || serverTaskList.isEmpty())
			return ret;
		for (ServerTask task : serverTaskList) {
			if (task.getUser().getIdDB() == userId) {
				String extractionSummary = "";
				if (ExtractionServerTask.class.isAssignableFrom(task.getClass())) {
					extractionSummary = ((ExtractionServerTask) task).getExtraction().getFiltersColumn();
					ret.add(new ServerTaskInfo(task.getId(), task.getStatus(), task.getTaskType(), extractionSummary,
							((ExtractionServerTask) task).getExtraction().getIdDB()));
				} else {
					ret.add(new ServerTaskInfo(task.getId(), task.getStatus(), task.getTaskType(), extractionSummary,
							0));
				}
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

	/**
	 * @return the scheduler
	 */
	public ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	/**
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(ScheduledExecutorService scheduler) {
		this.scheduler = scheduler;
	}

	public ScheduleTaskOnDateResponse scheduleTaskOnDate(int taskId, Date date) {
		ScheduleTaskOnDateResponse reply = new ScheduleTaskOnDateResponse();
		if (scheduler == null || taskId <= 0 || date == null ) {
			reply.setError(true);
			reply.setMessage("Task id or date are not valid");
			reply.setError_code(Constants.ERROR);
			return reply;
		}
		ServerTask task = findById(taskId);
		if (task == null || !ScheduledServerTask.class.isAssignableFrom(task.getClass())) {
			logger.info("No scheduled task found in the server instance for id: %i", taskId);
			reply.setError(true);
			reply.setMessage("No scheduled task found in the server instance for id: " +taskId);
			reply.setError_code(Constants.SCHEDULE_NO_TASK_FOUND);
			return reply;
		}
		ScheduledServerTask sTask = (ScheduledServerTask) task;
		if (sTask.getStatus() != Constants.ST_READY) {
			logger.info("Task " + sTask.getId() + " is not ready to be scheduled.");
			reply.setError(true);
			reply.setMessage("Task " + sTask.getId() +" is not ready to be scheduled.");
			reply.setError_code(Constants.SCHEDULE_NOT_READY);
			return reply;
		}
		long delay = date.getTime() - System.currentTimeMillis();
		if (delay < 0) {
			logger.info("Scheduled date is in the past, please enter a date from the future.");
			reply.setError(true);
			reply.setMessage("Scheduled date is in the past, please enter a date from the future.");
			reply.setError_code(Constants.SCHEDULE_DATE_PAST);
			return reply;
		}
		try {
			scheduler.schedule(sTask, delay, TimeUnit.MILLISECONDS);
			sTask.setScheduleDate(date);
			sTask.setRunningScheduled(true);
			sTask.onSchedule();
			logger.info("Task with id "+task.getId()+" has been scheduled on "+date.toString());
			reply.setError(false);
			reply.setMessage("Task with id %i has been scheduled on %s\"");
			reply.setError_code(Constants.SUCCESS);
			return reply;
		} catch (Exception e) {
			logger.warn("An exception ocurred scheduling task with id %i : %s", sTask.getId(), e.getMessage());
			reply.setError(true);
			reply.setMessage("An exception ocurred scheduling task with id "+task.getId()+" : "+ e.getMessage());
			reply.setError_code(Constants.ERROR);
			return reply;
		}
	}
}
