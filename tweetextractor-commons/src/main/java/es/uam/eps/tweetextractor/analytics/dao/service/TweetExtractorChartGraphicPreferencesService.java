/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.analytics.dao.TweetExtractorChartGraphicPreferencesDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorChartGraphicPreferencesServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;

/**
 * @author jgarciadelsaz
 *
 */
@Service
public class TweetExtractorChartGraphicPreferencesService extends GenericService<TweetExtractorChartGraphicPreferences, Integer> implements TweetExtractorChartGraphicPreferencesServiceInterface{
	@Autowired
	private TweetExtractorChartGraphicPreferencesDAO tweetExtractorChartGraphicPreferencesDAO;
	/**
	 * 
	 */
	public TweetExtractorChartGraphicPreferencesService() {
		super();
	}
	/**
	 * @return the tweetExtractorChartGraphicPreferencesDAO
	 */
	public TweetExtractorChartGraphicPreferencesDAO getTweetExtractorChartGraphicPreferencesDAO() {
		return tweetExtractorChartGraphicPreferencesDAO;
	}
	/**
	 * @param tweetExtractorChartGraphicPreferencesDAO the tweetExtractorChartGraphicPreferencesDAO to set
	 */
	public void setTweetExtractorChartGraphicPreferencesDAO(
			TweetExtractorChartGraphicPreferencesDAO tweetExtractorChartGraphicPreferencesDAO) {
		this.tweetExtractorChartGraphicPreferencesDAO = tweetExtractorChartGraphicPreferencesDAO;
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<TweetExtractorChartGraphicPreferences> findByUserAndChartType(User user, AnalyticsReportImageTypes chartType) {
		return this.tweetExtractorChartGraphicPreferencesDAO.findByUserAndChartType(user, chartType);
	}

}
