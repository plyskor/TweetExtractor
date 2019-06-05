/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.analytics.dao.TweetExtractorNERTokenSetDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERTokenSetServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jose
 *
 */
@Service
public class TweetExtractorNERTokenSetService extends GenericService<TweetExtractorNERTokenSet, TweetExtractorNERTokenSetID>
		implements TweetExtractorNERTokenSetServiceInterface {
	@Autowired
	private TweetExtractorNERTokenSetDAO tweetExtractorNERTokenSetDAO;
	
	/**
	 * 
	 */
	public TweetExtractorNERTokenSetService() {
		super();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TweetExtractorNERTokenSet> findByUserAndLanguage(User user, AvailableTwitterLanguage language) {
		return tweetExtractorNERTokenSetDAO.findByUserAndLanguage(user, language);
	}

}
