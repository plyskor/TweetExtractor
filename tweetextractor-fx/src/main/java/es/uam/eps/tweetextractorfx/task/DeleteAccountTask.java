/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class DeleteAccountTask extends TwitterExtractorFXTask<Integer>{
	private User user;
	private Logger logger = LoggerFactory.getLogger(DeleteAccountTask.class);
	/**
	 * @param user the user to set
	 * @param springContext the spring context to set

	 */
	public DeleteAccountTask( User user,AnnotationConfigApplicationContext springContext) {
		super(springContext);
		this.user = user;
	}


	@Override
	protected Integer call() throws Exception {
		if (user==null)return Constants.ERROR;
		UserServiceInterface userService = springContext.getBean(UserServiceInterface.class);
		userService.deleteById(user.getIdDB());
		logger.info("Account "+user.getNickname()+" and all its data succesfully deleted.");
		return Constants.SUCCESS;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
