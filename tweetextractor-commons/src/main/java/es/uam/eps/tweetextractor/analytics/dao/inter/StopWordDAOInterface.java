/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsListID;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface StopWordDAOInterface<T> {
	public List<T> findByList(CustomStopWordsListID fk);
}
