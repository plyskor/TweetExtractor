/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.reference.nlp.StopWord;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface StopWordServiceInterface extends GenericServiceInterface<StopWord, Integer> {
	public List<StopWord> findByList(CustomStopWordsListID fk);
}
