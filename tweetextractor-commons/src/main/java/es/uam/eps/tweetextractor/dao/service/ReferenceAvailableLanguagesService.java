/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.dao.ReferenceAvailableLanguagesDAO;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Service
public class ReferenceAvailableLanguagesService extends GenericService<AvailableTwitterLanguage, Integer> implements ReferenceAvailableLanguagesServiceInterface{
	@Autowired
	private ReferenceAvailableLanguagesDAO referenceAvailableLanguagesDAO;
	
	
	public ReferenceAvailableLanguagesService() {
		super();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public AvailableTwitterLanguage findByShortCode(String shortcode) {
		return referenceAvailableLanguagesDAO.findByShortCode(shortcode);
	}

}
