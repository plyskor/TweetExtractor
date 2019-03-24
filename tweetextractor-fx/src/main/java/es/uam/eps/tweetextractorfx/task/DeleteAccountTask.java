/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class DeleteAccountTask extends TwitterExtractorFXTask<Integer>{
	private User user;
	private Logger logger = LoggerFactory.getLogger(DeleteAccountTask.class);
	/**
	 * 
	 */
	public DeleteAccountTask(User user, AnnotationConfigApplicationContext context)  {
		super(context);
		this.setUser(user);
	}

	@Override
	protected Integer call() throws Exception {
		if (user==null)return -1;
		UserServiceInterface userService = springContext.getBean(UserServiceInterface.class);
		userService.deleteById(user.getIdDB());
		logger.info("Account "+user.getNickname()+" and all its data succesfully deleted.");
		return 0;
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
