/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.analytics.dao.TweetExtractorNERTokenDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERTokenServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERToken;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;

/**
 * @author jose
 *
 */
@Service
public class TweetExtractorNERTokenService extends GenericService<TweetExtractorNERToken, Integer>
		implements TweetExtractorNERTokenServiceInterface {
	@Autowired
	private TweetExtractorNERTokenDAO tweetExtractorNERTokenDAO;
	
	/**
	 * 
	 */
	public TweetExtractorNERTokenService() {
		super();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TweetExtractorNERToken> findBySet(TweetExtractorNERTokenSetID setID) {
		return tweetExtractorNERTokenDAO.findBySet(setID);
	}

	
}
