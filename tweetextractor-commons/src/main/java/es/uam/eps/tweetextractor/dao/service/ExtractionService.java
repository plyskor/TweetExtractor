/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.ExtractionDAO;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Service
public class ExtractionService extends GenericService<Extraction, Integer> implements ExtractionServiceInterface{
	@Autowired
	private ExtractionDAO extractionDAO;
 
    
    public ExtractionService() {
        super();
    }
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
    @Override
	public boolean hasAnyExtraction(User user) {
		return !findByUser(user).isEmpty();
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<Extraction> findByUser(User user) {
		return extractionDAO.findByUser(user);
	}
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<Extraction> findListById(List<Integer> extractions) {
		return extractionDAO.findListById(extractions);
	}
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<Extraction> findListByReport(AnalyticsReport report) {
		return extractionDAO.findListByReport(report);
	}
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<Extraction> findListByNERTokenSet(TweetExtractorNERTokenSet tokenSet) {
		return extractionDAO.findListByNERTokenSet(tokenSet);
	}

}