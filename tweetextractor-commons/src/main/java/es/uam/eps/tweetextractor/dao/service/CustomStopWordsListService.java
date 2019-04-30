/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.dao.CustomStopWordsListDAO;
import es.uam.eps.tweetextractor.dao.service.inter.CustomStopWordsListServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Service
public class CustomStopWordsListService extends GenericService<CustomStopWordsList, CustomStopWordsListID> implements CustomStopWordsListServiceInterface {
	@Autowired
	private CustomStopWordsListDAO customStopWordsListDAO;
	
	/**
	 * 
	 */
	public CustomStopWordsListService() {
		super();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CustomStopWordsList> findByUserAndLanguage(User user, AvailableTwitterLanguage language) {
		return customStopWordsListDAO.findByUserAndLanguage(user, language);
	}

	

}
