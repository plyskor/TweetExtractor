/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.AbstractGenericDAO;
import es.uam.eps.tweetextractor.dao.FilterDAO;
import es.uam.eps.tweetextractor.dao.service.inter.FilterServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.filter.Filter;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Service
public class FilterService extends GenericService<Filter, Integer> implements FilterServiceInterface {
	@Autowired
	private FilterDAO filterDAO;
	/**
	 * 
	 */
	public FilterService() {
		super();
	}

	/**
	 * @param genericDao
	 */
	public FilterService(AbstractGenericDAO<Filter, Integer> genericDao) {
		super(genericDao);
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.dao.service.inter.FilterServiceInterface#findByExtraction(es.uam.eps.tweetextractor.model.Extraction)
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<Filter> findByExtraction(Extraction e) {
		return filterDAO.findByExtraction(e);
	}

}
