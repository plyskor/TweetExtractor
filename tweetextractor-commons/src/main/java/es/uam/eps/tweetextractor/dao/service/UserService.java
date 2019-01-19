/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.dao.UserDAO;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Service
public class UserService extends GenericService<User, Integer> implements UserServiceInterface{
	private UserDAO userDAO;
    public UserService(){
    	super();
    }
    @Autowired
    public UserService(
            AbstractGenericDAO<User, Integer> genericDao) {
        super(genericDao);
        this.userDAO = (UserDAO) genericDao;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public boolean existsUser(String nickname) {
		if(findByNickname(nickname)==null)return false;
		return true;	}
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public User findByNickname(String nickname) {
		User ret=userDAO.findByNickname(nickname);
		return ret;
	}

}
