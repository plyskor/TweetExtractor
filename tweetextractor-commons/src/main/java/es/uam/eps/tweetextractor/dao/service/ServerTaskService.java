/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import es.uam.eps.tweetextractor.dao.ServerTaskDAO;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class ServerTaskService {

	private static ServerTaskDAO serverTaskDAO;

	public ServerTaskService() {
		serverTaskDAO = new ServerTaskDAO();
	}

	public void persist(ServerTask entity) {
		serverTaskDAO.openCurrentSessionwithTransaction();
		serverTaskDAO.persist(entity);
		serverTaskDAO.closeCurrentSessionwithTransaction();
	}

	public void update(ServerTask entity) {
		serverTaskDAO.openCurrentSessionwithTransaction();
		serverTaskDAO.update(entity);
		serverTaskDAO.closeCurrentSessionwithTransaction();
	}
	public void merge(ServerTask entity) {
		serverTaskDAO.openCurrentSessionwithTransaction();
		serverTaskDAO.merge(entity);
		serverTaskDAO.closeCurrentSessionwithTransaction();
	}
	public ServerTask findById(int id) {
		serverTaskDAO.openCurrentSession();
		ServerTask serverTask = serverTaskDAO.findById(id);
		serverTaskDAO.closeCurrentSession();
		return serverTask;
	}

	public void delete(int id) {
		serverTaskDAO.openCurrentSessionwithTransaction();
		ServerTask serverTask = serverTaskDAO.findById(id);
		serverTaskDAO.delete(serverTask);
		serverTaskDAO.closeCurrentSessionwithTransaction();
	}
	public boolean hasAnyServerTask(User user) {
		if(findByUser(user)==null)return false;
		return true;	}
	public List<ServerTask> findByUser(User user) {
		serverTaskDAO.openCurrentSession();
		List<ServerTask> ret=serverTaskDAO.findByUser(user);
		serverTaskDAO.closeCurrentSession();
		return ret;
	}

	public List<ServerTask> findAll() {
		List<ServerTask> serverTasks = serverTaskDAO.findAll();
		return serverTasks;
	}

	public void deleteAll() {
		serverTaskDAO.openCurrentSessionwithTransaction();
		serverTaskDAO.deleteAll();
		serverTaskDAO.closeCurrentSessionwithTransaction();
	}

	public ServerTaskDAO serverTaskDAO() {
		return serverTaskDAO;
	}
}