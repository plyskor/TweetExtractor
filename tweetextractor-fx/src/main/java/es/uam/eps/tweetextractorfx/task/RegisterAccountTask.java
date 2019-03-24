/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.task.status.RegisterStatus;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class RegisterAccountTask extends TwitterExtractorFXTask<RegisterStatus>{
	private String username;
	private String password1;
	private String password2;
	private Logger logger = LoggerFactory.getLogger(RegisterAccountTask.class);
	/**
	 * 
	 */
	public RegisterAccountTask(String username,String password1,String password2,AnnotationConfigApplicationContext context) {
		super(context);
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
			logger.warn("Nickname or password to register are null");
			return ret;
		}
		UserServiceInterface userService = springContext.getBean(UserServiceInterface.class);
		if(username.isEmpty()||username.length()<3) {			
			ret.setStatus(Constants.EMPTY_USER_REGISTER_ERROR);
			ret.setUser(null);
			return ret;
		}
		if(userService.existsUser(username)) {
			ret.setStatus(Constants.EXIST_USER_REGISTER_ERROR);
			ret.setUser(null);
			logger.info("Account with nickname "+username+" already exists.");
			return ret;
		}
		String password11=this.password1.replace("\r", "").replace("\n", "");
		if(password11.trim().isEmpty()||!checkPassword(password11)) {
			ret.setStatus(Constants.UNSAFE_PASSWORD_REGISTER_ERROR);
			ret.setUser(null);
			return ret;
		}
		String password22=this.password2;
		if(!password11.equals(password22)) {
			ret.setStatus(Constants.PASSWORD_MISMATCH_REGISTER_ERROR);
			ret.setUser(null);
			return ret;
		}
		User newUser = new User(username,BCrypt.hashpw(password11, BCrypt.gensalt(12)));
		userService.persist(newUser);
		ret.setStatus(Constants.SUCCESS_REGISTER);
		ret.setUser(newUser);
		logger.info("User account with nickname "+newUser.getNickname()+" has been successfully created");
		return ret;		
	}
	public static boolean checkPassword(String password) {
		 String pattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{6,16})";
		 Pattern p = Pattern.compile(pattern);
	     Matcher m = p.matcher(password);
		return m.matches();
	}
}
