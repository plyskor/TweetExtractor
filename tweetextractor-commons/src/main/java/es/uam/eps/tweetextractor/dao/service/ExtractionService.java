/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import es.uam.eps.tweetextractor.dao.ExtractionDAO;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class ExtractionService {

	private static ExtractionDAO extractionDAO;

	public ExtractionService() {
		extractionDAO = new ExtractionDAO();
	}

	public void persist(Extraction entity) {
		extractionDAO.openCurrentSessionwithTransaction();
		extractionDAO.persist(entity);
		extractionDAO.closeCurrentSessionwithTransaction();
	}

	public void update(Extraction entity) {
		extractionDAO.openCurrentSessionwithTransaction();
		extractionDAO.update(entity);
		extractionDAO.closeCurrentSessionwithTransaction();
	}
	public void merge(Extraction entity) {
		extractionDAO.openCurrentSessionwithTransaction();
		extractionDAO.merge(entity);
		extractionDAO.closeCurrentSessionwithTransaction();
	}
	public Extraction findById(int id) {
		extractionDAO.openCurrentSession();
		Extraction extraction = extractionDAO.findById(id);
		extractionDAO.closeCurrentSession();
		return extraction;
	}

	public void delete(int id) {
		extractionDAO.openCurrentSessionwithTransaction();
		Extraction extraction = extractionDAO.findById(id);
		extractionDAO.delete(extraction);
		extractionDAO.closeCurrentSessionwithTransaction();
	}
	public boolean hasAnyExtraction(User user) {
		if(findByUser(user)==null)return false;
		return true;	}
	public List<Extraction> findByUser(User user) {
		extractionDAO.openCurrentSessionwithTransaction();
		List<Extraction> ret=extractionDAO.findByUser(user);
		extractionDAO.closeCurrentSessionwithTransaction();
		return ret;
	}

	public List<Extraction> findAll() {
		extractionDAO.openCurrentSession();
		List<Extraction> extractions = extractionDAO.findAll();
		extractionDAO.closeCurrentSession();
		return extractions;
	}

	public void deleteAll() {
		extractionDAO.openCurrentSessionwithTransaction();
		extractionDAO.deleteAll();
		extractionDAO.closeCurrentSessionwithTransaction();
	}

	public ExtractionDAO extractionDAO() {
		return extractionDAO;
	}
}