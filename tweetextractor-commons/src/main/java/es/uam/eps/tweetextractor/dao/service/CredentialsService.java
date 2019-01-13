/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.CredentialsDAO;
import es.uam.eps.tweetextractor.dao.service.inter.CredentialsServiceInterface;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Service
public class CredentialsService extends GenericService<Credentials, Integer> implements CredentialsServiceInterface {
	@Autowired
	private CredentialsDAO credentialsDAO;
    public CredentialsService(){
    	super();
    }
    
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public boolean hasAnyCredentials(User user) {
		return !findByUser(user).isEmpty();	
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<Credentials> findByUser(User user) {
		return credentialsDAO.findByUser(user);
	}
  

}