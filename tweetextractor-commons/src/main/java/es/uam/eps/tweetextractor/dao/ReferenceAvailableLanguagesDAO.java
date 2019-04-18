/**
 * 
 */
package es.uam.eps.tweetextractor.dao;


import javax.persistence.NoResultException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.ReferenceAvailableLanguagesDAOInterface;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Repository //findByShortCode
public class ReferenceAvailableLanguagesDAO extends AbstractGenericDAO<AvailableTwitterLanguage, Integer>
		implements ReferenceAvailableLanguagesDAOInterface<AvailableTwitterLanguage> {

	@Override
	public AvailableTwitterLanguage findByShortCode(String shortcode) {
		Query<AvailableTwitterLanguage> query = currentSession().createNamedQuery("findByShortCode",AvailableTwitterLanguage.class);
	    query.setParameter("shortCode", shortcode);
	     AvailableTwitterLanguage ret= null;
	    try {ret=query.getSingleResult();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No available language found for code: "+shortcode);
	    	return ret;
	    	}
	    return ret;
	}

}
