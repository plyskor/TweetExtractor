/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.StopWordDAOInterface;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.reference.nlp.StopWord;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Repository
public class StopWordDAO extends AbstractGenericDAO<StopWord, Integer> implements StopWordDAOInterface<StopWord> {


	public StopWordDAO() {
		super();
	}

	@Override
	public List<StopWord> findByList(CustomStopWordsListID fk) {
		Query<StopWord> query = currentSession().createNamedQuery("findByList",StopWord.class);
	    query.setParameter("fk", fk);
	     List<StopWord> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No stop words found for userID: "+fk.getUser().getIdDB()+", language: "+fk.getLanguage().getShortName()+" and list name: "+fk.getName());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
