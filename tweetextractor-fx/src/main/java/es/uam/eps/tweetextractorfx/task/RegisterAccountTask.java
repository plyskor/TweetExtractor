/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;
import es.uam.eps.tweetextractor.dao.service.UserService;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.task.status.RegisterStatus;
import javafx.concurrent.Task;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class RegisterAccountTask extends Task<RegisterStatus>{
	private String username;
	private String password1;
	private String password2;
	/**
	 * 
	 */
	public RegisterAccountTask(String username,String password1,String password2) {
		this.username=username;
		this.password1=password1;
		this.password2=password2;
	}

	@Override
	protected RegisterStatus call() throws Exception {
		RegisterStatus ret = new RegisterStatus();
		if(this.username==null||this.password1==null) {
			ret.setStatus(Constants.UNKNOWN_REGISTER_ERROR);
			ret.setUser(null);
			return ret;
		}
		UserService userService = new UserService();
		if(username.isEmpty()||username.length()<3) {			
			ret.setStatus(Constants.EMPTY_USER_REGISTER_ERROR);
			ret.setUser(null);
			return ret;
		}
		if(userService.existsUser(username)) {
			ret.setStatus(Constants.EXIST_USER_REGISTER_ERROR);
			ret.setUser(null);
			return ret;
		}
		String password1=this.password1.replace("\r", "").replace("\n", "");
		if(password1.trim().isEmpty()||!checkPassword(password1)) {
			ret.setStatus(Constants.UNSAFE_PASSWORD_REGISTER_ERROR);
			ret.setUser(null);
			return ret;
		}
		String password2=this.password2;
		if(!password1.equals(password2)) {
			ret.setStatus(Constants.PASSWORD_MISMATCH_REGISTER_ERROR);
			ret.setUser(null);
			return ret;
		}
		User newUser = new User(username,BCrypt.hashpw(password1, BCrypt.gensalt(12)));
		userService.persist(newUser);
		ret.setStatus(Constants.SUCCESS_REGISTER);
		ret.setUser(newUser);
		return ret;		
	}
	public static boolean checkPassword(String password) {
		 String pattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{6,16})";
		 Pattern p = Pattern.compile(pattern);
	     Matcher m = p.matcher(password);
		return m.matches();
	}
}
