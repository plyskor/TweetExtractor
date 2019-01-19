/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.Date;
import org.hibernate.HibernateException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.task.status.LoginStatus;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class LogInTask extends TwitterExtractorFXTask<LoginStatus>{
	String username;
	String password;
	/**
	 * 
	 */
	public LogInTask(String username,String password,AnnotationConfigApplicationContext context) {
		super(context);
		this.username=username;
		this.password=password;
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
			return ret;
		}
		String pass = password.trim();
		if(pass.isEmpty()) {
			ret.setStatus(Constants.EMPTY_PASSWORD_LOGIN_ERROR);
			ret.setUser(null);
			return ret;
		}
		try {
			userLogged =userService.findByNickname(username);
			if(userLogged==null) {
				ret.setStatus(Constants.EXIST_USER_LOGIN_ERROR);
				ret.setUser(null);
				return ret;
			}
		}catch(HibernateException e) {
			e.printStackTrace();
			return null;
		}
		boolean passOK = BCrypt.checkpw(pass, userLogged.getPassword());
		if(passOK) {
			ret.setStatus(Constants.SUCCESS_LOGIN);
			userLogged.setLastConnectionDate(new Date());
			userService.update(userLogged);
			ret.setUser(userLogged);
			return ret;
		}else {
			ret.setStatus(Constants.INCORRECT_PASSWORD_LOGIN_ERROR);
			ret.setUser(null);
			return ret;
		}
	}

}
