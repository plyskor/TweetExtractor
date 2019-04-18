/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface StopWordDAOInterface<T> {
	public List<T> findByList(CustomStopWordsListID fk);
}
