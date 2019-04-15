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
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingReportRegister;

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
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<String> findTopNHashtags(int n) {
		return tweetDAO.findTopNHashtags(n);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<String> findTopNHashtagsFiltered(int n, List<String> filter) {
		return tweetDAO.findTopNHashtagsFiltered(n, filter);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TimelineReportVolumeRegister> extractHashtagTimelineVolumeReport(User user,String hashtag){
		return tweetDAO.extractHashtagTimelineVolumeReport(user, hashtag);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TrendingReportRegister> findTopNHashtagsByExtraction(int n, List<Integer> extractionIDList) {
		return tweetDAO.findTopNHashtagsByExtraction(n, extractionIDList);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TrendingReportRegister> findTopNHashtagsByExtractionFiltered(int n, List<String> filter,
			List<Integer> extractionIDList) {
		return tweetDAO.findTopNHashtagsByExtractionFiltered(n, filter, extractionIDList);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TrendingReportRegister> findTopNUsersByExtraction(int n, List<Integer> extractionIDList) {
		return tweetDAO.findTopNUsersByExtraction(n, extractionIDList);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TrendingReportRegister> findTopNUsersByExtractionFiltered(int n, List<String> filter,
			List<Integer> extractionIDList) {
		return tweetDAO.findTopNUsersByExtractionFiltered(n, filter, extractionIDList);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TrendingReportRegister> findTopNUserMentionsByExtraction(int n, List<Integer> extractionIDList) {
		return tweetDAO.findTopNUserMentionsByExtraction(n, extractionIDList);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TrendingReportRegister> findTopNUserMentionsByExtractionFiltered(int n, List<String> filter,
			List<Integer> extractionIDList) {
		return tweetDAO.findTopNUserMentionsByExtractionFiltered(n, filter, extractionIDList);
	}

}
