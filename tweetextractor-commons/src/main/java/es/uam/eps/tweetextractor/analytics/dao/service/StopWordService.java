/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.analytics.dao.StopWordDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.StopWordServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.analytics.nlp.StopWord;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Service
public class StopWordService extends GenericService<StopWord, Integer> implements StopWordServiceInterface {
	@Autowired
	private StopWordDAO stopWordDAO;
	
	public StopWordService() {
		super();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<StopWord> findByList(CustomStopWordsListID fk) {
		return stopWordDAO.findByList(fk);
	}
}
