/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface CustomStopWordsListDAOInterface<T> extends GenericDAOInterface<CustomStopWordsList, CustomStopWordsListID> {
	public List<T> findByUserAndLanguage(User user, AvailableTwitterLanguage language);
}
