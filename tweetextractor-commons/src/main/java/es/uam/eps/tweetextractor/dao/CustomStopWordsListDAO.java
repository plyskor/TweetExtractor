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

import es.uam.eps.tweetextractor.dao.inter.CustomStopWordsListDAOInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Repository
public class CustomStopWordsListDAO extends AbstractGenericDAO<CustomStopWordsList, CustomStopWordsListID>
		implements CustomStopWordsListDAOInterface<CustomStopWordsList> {

	@Override
	public List<CustomStopWordsList> findByUserAndLanguage(User user, AvailableTwitterLanguage language) {
		Query<CustomStopWordsList> query = currentSession().createNamedQuery("findByUserAndLanguage",CustomStopWordsList.class);
	    query.setParameter("user", user);
	    query.setParameter("language", language);
	     List<CustomStopWordsList> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No stop words lists found for userID: "+user.getIdDB()+" and language: "+language.getLongName());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}

}
