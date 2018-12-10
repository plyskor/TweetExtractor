/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;


import java.util.List;

import es.uam.eps.tweetextractor.dao.CredentialsDAO;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class CredentialsService {

	private static CredentialsDAO credentialsDAO;

	public CredentialsService() {
		credentialsDAO = new CredentialsDAO();
	}

	public void persist(Credentials entity) {
		credentialsDAO.openCurrentSessionwithTransaction();
		credentialsDAO.persist(entity);
		credentialsDAO.closeCurrentSessionwithTransaction();
	}

	public void update(Credentials entity) {
		credentialsDAO.openCurrentSessionwithTransaction();
		credentialsDAO.update(entity);
		credentialsDAO.closeCurrentSessionwithTransaction();
	}

	public Credentials findById(int id) {
		credentialsDAO.openCurrentSession();
		Credentials credentials = credentialsDAO.findById(id);
		credentialsDAO.closeCurrentSession();
		return credentials;
	}

	public void delete(int id) {
		credentialsDAO.openCurrentSessionwithTransaction();
		Credentials credentials = credentialsDAO.findById(id);
		credentialsDAO.delete(credentials);
		credentialsDAO.closeCurrentSessionwithTransaction();
	}
	public boolean hasAnyCredentials(User user) {
		if(findByUser(user)==null)return false;
		return true;	
		}
	public List<Credentials> findByUser(User user) {
		credentialsDAO.openCurrentSession();
		List<Credentials> ret=credentialsDAO.findByUser(user);
		credentialsDAO.closeCurrentSession();
		return ret;
	}

	public List<Credentials> findAll() {
		credentialsDAO.openCurrentSession();
		List<Credentials> credentialss = credentialsDAO.findAll();
		credentialsDAO.closeCurrentSession();
		return credentialss;
	}

	public void deleteAll() {
		credentialsDAO.openCurrentSessionwithTransaction();
		credentialsDAO.deleteAll();
		credentialsDAO.closeCurrentSessionwithTransaction();
	}

	public CredentialsDAO credentialsDAO() {
		return credentialsDAO;
	}

}