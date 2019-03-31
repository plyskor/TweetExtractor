/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.analytics.dao.AnalyticsReportImageDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportImageServiceInterface;

import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReportImage;

/**
 * @author jose
 *
 */
@Service
public class AnalyticsReportImageService extends GenericService<AnalyticsReportImage, Integer> implements AnalyticsReportImageServiceInterface {
	@Autowired
	private AnalyticsReportImageDAO analyticsReportImageDAO;
	public AnalyticsReportImageService() {
		super();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<AnalyticsReportImage> findByUser(User user) {
		return analyticsReportImageDAO.findByUser(user);
	}

	
}
