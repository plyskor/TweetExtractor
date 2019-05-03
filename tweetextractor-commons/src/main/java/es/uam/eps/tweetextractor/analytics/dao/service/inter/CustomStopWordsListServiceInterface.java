/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface CustomStopWordsListServiceInterface extends GenericServiceInterface<CustomStopWordsList, CustomStopWordsListID> {
	public List<CustomStopWordsList> findByUserAndLanguage(User user, AvailableTwitterLanguage language) ;
}
