/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.analytics.dao.TweetExtractorNamedEntityDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNamedEntityServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;

/**
 * @author jose
 *
 */
@Service
public class TweetExtractorNamedEntityService extends GenericService<TweetExtractorNamedEntity,Integer>
		implements TweetExtractorNamedEntityServiceInterface {
	@Autowired
	private TweetExtractorNamedEntityDAO tweetExtractorNamedEntityDAO;
	
	/**
	 * 
	 */
	public TweetExtractorNamedEntityService() {
		super();
	}


	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TweetExtractorNamedEntity> findByConfiguration(TweetExtractorNERConfigurationID fk) {
		return tweetExtractorNamedEntityDAO.findByConfiguration(fk);
	}



}
