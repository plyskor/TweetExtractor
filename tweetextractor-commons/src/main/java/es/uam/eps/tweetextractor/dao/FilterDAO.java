/**
 * 
 */
package es.uam.eps.tweetextractor.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import es.uam.eps.tweetextractor.dao.inter.FilterDAOInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.filter.Filter;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Repository
public class FilterDAO extends AbstractGenericDAO<Filter,Integer> implements FilterDAOInterface<Filter> {

	/**
	 * 
	 */
	public FilterDAO() {
		super();
	}
	public List<Filter> findByExtraction(Extraction extraction) {
		org.hibernate.query.Query<Filter> query = currentSession().createNamedQuery("findFilterssByExtraction",Filter.class);
	    query.setParameter("extraction", extraction);
	     List<Filter> ret= null;
	    try {ret=query.getResultList();}catch(NoResultException e) {
	    	Logger logger = LoggerFactory.getLogger(this.getClass());
	    	logger.info("No filters found for extractionID: "+extraction.getIdDB());
	    	return new ArrayList<>();
	    	}
	    return ret;
	}
}
