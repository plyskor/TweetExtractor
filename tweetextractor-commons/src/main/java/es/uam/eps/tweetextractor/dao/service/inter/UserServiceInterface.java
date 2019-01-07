/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import es.uam.eps.tweetextractor.model.User;

/**
 * @author jose
 *
 */
public interface UserServiceInterface extends GenericServiceInterface<User, Integer>{
	public boolean existsUser(String nickname) ;
	public User findByNickname(String nickname);
}
