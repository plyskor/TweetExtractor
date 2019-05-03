/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.analytics.dao.TweetExtractorNERConfigurationDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERConfigurationServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jose
 *
 */
@Service
public class TweetExtractorNERConfigurationService extends GenericService<TweetExtractorNERConfiguration, TweetExtractorNERConfigurationID>
		implements TweetExtractorNERConfigurationServiceInterface {
	@Autowired
	private TweetExtractorNERConfigurationDAO tweetExtractorNERConfigurationDAO;
	
	/**
	 * 
	 */
	public TweetExtractorNERConfigurationService() {
		super();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TweetExtractorNERConfiguration> findByUserAndLanguage(User user, AvailableTwitterLanguage language) {
		return tweetExtractorNERConfigurationDAO.findByUserAndLanguage(user, language);
	}

	
}
