/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import es.uam.eps.tweetextractor.dao.TweetDAO;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class TweetService {

	private static TweetDAO tweetDAO;

	public TweetService() {
		tweetDAO = new TweetDAO();
	}

	public void persist(Tweet entity) {
		tweetDAO.openCurrentSessionwithTransaction();
		tweetDAO.persist(entity);
		tweetDAO.closeCurrentSessionwithTransaction();
	}
	public void persistList(List<Tweet> entityList) {
		tweetDAO.openCurrentSessionwithTransaction();
		tweetDAO.persistList(entityList);
		tweetDAO.closeCurrentSessionwithTransaction();
	}
	public void update(Tweet entity) {
		tweetDAO.openCurrentSessionwithTransaction();
		tweetDAO.update(entity);
		tweetDAO.closeCurrentSessionwithTransaction();
	}

	public Tweet findById(int id) {
		tweetDAO.openCurrentSession();
		Tweet tweet = tweetDAO.findById(id);
		tweetDAO.closeCurrentSession();
		return tweet;
	}

	public void delete(int id) {
		tweetDAO.openCurrentSessionwithTransaction();
		Tweet tweet = tweetDAO.findById(id);
		tweetDAO.delete(tweet);
		tweetDAO.closeCurrentSessionwithTransaction();
	}
	public List<Tweet> findByExtraction(Extraction extraction) {
		tweetDAO.openCurrentSession();
		List<Tweet> ret=tweetDAO.findByExtraction(extraction);
		tweetDAO.closeCurrentSession();
		return ret;
	}

	public List<Tweet> findAll() {
		tweetDAO.openCurrentSession();
		List<Tweet> tweets = tweetDAO.findAll();
		tweetDAO.closeCurrentSession();
		return tweets;
	}

	public void deleteAll() {
		tweetDAO.openCurrentSessionwithTransaction();
		tweetDAO.deleteAll();
		tweetDAO.closeCurrentSessionwithTransaction();
	}

	public TweetDAO tweetDAO() {
		return tweetDAO;
	}
}
