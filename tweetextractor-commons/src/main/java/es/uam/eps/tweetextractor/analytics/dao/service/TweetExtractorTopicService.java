package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.analytics.dao.TweetExtractorTopicDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorTopicServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorTopic;

@Service
public class TweetExtractorTopicService extends GenericService<TweetExtractorTopic, Integer> implements TweetExtractorTopicServiceInterface {
	@Autowired
	private TweetExtractorTopicDAO tweetExtractorTopicDAO;
	
	/**
	 * 
	 */
	public TweetExtractorTopicService() {
		super();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TweetExtractorTopic> findByNamedEntity(int namedEntityID) {
		return tweetExtractorTopicDAO.findByNamedEntity(namedEntityID);
	}

}
