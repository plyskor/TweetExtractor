/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.Date;
import org.hibernate.HibernateException;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.task.status.LoginStatus;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class LogInTask extends TweetExtractorFXTask<LoginStatus>{
	String username;
	String password;
	private Logger logger = LoggerFactory.getLogger(LogInTask.class);

	/**
	 * @param springContext the spring context to set
	 * @param username the username to set
	 * @param password the password to set
	 */
	public LogInTask(AnnotationConfigApplicationContext springContext, String username, String password) {
		super(springContext);
		this.username = username;
		this.password = password;
	}

	@Override
	protected LoginStatus call() throws Exception {
		if(username==null||password==null)return null;
		UserServiceInterface userService=springContext.getBean(UserServiceInterface.class);
		LoginStatus ret= new LoginStatus();
		User userLogged = null;
		if(username.isEmpty()) {
			ret.setStatus(Constants.EMPTY_USER_LOGIN_ERROR);
			ret.setUser(null);
			logger.info("Empty user invalid for login.");
			return ret;
		}
		String pass = password.trim();
		if(pass.isEmpty()) {
			ret.setStatus(Constants.EMPTY_PASSWORD_LOGIN_ERROR);
			ret.setUser(null);
			logger.info("Empty password invalid for login.");
			return ret;
		}
		try {
			userLogged =userService.findByNickname(username);
			if(userLogged==null) {
				ret.setStatus(Constants.EXIST_USER_LOGIN_ERROR);
				ret.setUser(null);
				logger.info("Provided user for login "+username+" doesn't exist");
				return ret;
			}
		}catch(HibernateException e) {
			logger.error(e.getMessage());
			return null;
		}
		boolean passOK = BCrypt.checkpw(pass, userLogged.getPassword());
		if(passOK) {
			ret.setStatus(Constants.SUCCESS_LOGIN);
			userLogged.setLastConnectionDate(new Date());
			userService.update(userLogged);
			ret.setUser(userLogged);
			logger.info("User "+ userLogged.getNickname()+" has logged in");
			return ret;
		}else {
			ret.setStatus(Constants.INCORRECT_PASSWORD_LOGIN_ERROR);
			ret.setUser(null);
			logger.info("Password provided for login is not correct.");
			return ret;
		}
	}

}
