/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.dao.ExtractionDAO;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Service
public class ExtractionService extends GenericService<Extraction, Integer> implements ExtractionServiceInterface{
	@Autowired
	private ExtractionDAO extractionDAO;
 
    
    public ExtractionService() {
        super();
    }
    @Override
	public boolean hasAnyExtraction(User user) {
		if(findByUser(user)==null)return false;
		return true;	}
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<Extraction> findByUser(User user) {
		return extractionDAO.findByUser(user);
	}

}