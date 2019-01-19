/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.dao.ServerTaskDAO;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Service
public class ServerTaskService extends GenericService<ServerTask, Integer> implements ServerTaskServiceInterface{
	private ServerTaskDAO serverTaskDAO;
    public ServerTaskService(){
    	super();
    }
    @Autowired
    public ServerTaskService(
             AbstractGenericDAO<ServerTask, Integer> genericDao) {
        super(genericDao);
        this.serverTaskDAO = (ServerTaskDAO) genericDao;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public boolean hasAnyServerTask(User user) {
		return !findByUser(user).isEmpty();
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<ServerTask> findByUser(User user) {
		return serverTaskDAO.findByUser(user);
	}
}