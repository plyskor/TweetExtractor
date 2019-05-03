/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.inter;

import java.util.List;

/**
 * @author jose
 *
 */
public interface TweetExtractorTopicDAOInterface <T>{
	public List<T> findByNamedEntity(int namedEntityID);
}
