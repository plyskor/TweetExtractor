/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service.inter;

import java.util.List;

import es.uam.eps.tweetextractor.dao.service.inter.GenericServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.analytics.nlp.StopWord;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface StopWordServiceInterface extends GenericServiceInterface<StopWord, Integer> {
	public List<StopWord> findByList(CustomStopWordsListID fk);
}
