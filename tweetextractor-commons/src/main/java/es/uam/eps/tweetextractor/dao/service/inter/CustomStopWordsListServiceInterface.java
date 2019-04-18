/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface CustomStopWordsListServiceInterface extends GenericServiceInterface<CustomStopWordsList, CustomStopWordsListID> {
	public List<CustomStopWordsList> findByUserAndLanguage(User user, AvailableTwitterLanguage language) ;
}
