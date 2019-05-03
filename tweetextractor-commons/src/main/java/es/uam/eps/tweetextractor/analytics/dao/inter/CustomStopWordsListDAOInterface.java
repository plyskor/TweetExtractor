/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.inter.GenericDAOInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface CustomStopWordsListDAOInterface<T> extends GenericDAOInterface<CustomStopWordsList, CustomStopWordsListID> {
	public List<T> findByUserAndLanguage(User user, AvailableTwitterLanguage language);
}
