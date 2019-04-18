/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.StopWordDAO;
import es.uam.eps.tweetextractor.dao.service.inter.StopWordServiceInterface;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.reference.nlp.StopWord;

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
