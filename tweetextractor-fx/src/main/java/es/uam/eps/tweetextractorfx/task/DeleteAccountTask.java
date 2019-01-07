/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.uam.eps.tweetextractor.dao.service.UserService;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import javafx.concurrent.Task;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class DeleteAccountTask extends TwitterExtractorFXTask<Integer>{
	private User user;
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
