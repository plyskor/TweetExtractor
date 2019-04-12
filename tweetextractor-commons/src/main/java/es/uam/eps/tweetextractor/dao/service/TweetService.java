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
import es.uam.eps.tweetextractor.dao.TweetDAO;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Service
public class TweetService extends GenericService<Tweet, Integer> implements TweetServiceInterface{
	private TweetDAO tweetDAO;
    public TweetService(){
    	super();
    }
    @Autowired
    public TweetService(
           AbstractGenericDAO<Tweet, Integer> genericDao) {
        super(genericDao);
        this.tweetDAO = (TweetDAO) genericDao;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void persistList(List<Tweet> entityList) {
		tweetDAO.persistList(entityList);
	}
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<Tweet> findByExtraction(Extraction extraction) {
		return tweetDAO.findByExtraction(extraction);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TimelineReportVolumeRegister> extractGlobalTimelineVolumeReport(User user) {
		return tweetDAO.extractGlobalTimelineVolumeReport(user);
	}

}
