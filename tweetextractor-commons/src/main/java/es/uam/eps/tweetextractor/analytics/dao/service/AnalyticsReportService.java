/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.analytics.dao.AnalyticsReportDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;

/**
 * @author jose
 *
 */
@Service
public class AnalyticsReportService extends GenericService<AnalyticsCategoryReport, Integer> implements AnalyticsReportServiceInterface{
	@Autowired
	private AnalyticsReportDAO analyticsReportDAO;
	/**
	 * 
	 */
	public AnalyticsReportService() {
		super();
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<AnalyticsCategoryReport> findByUser(User user) {
		return analyticsReportDAO.findByUser(user);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<AnalyticsCategoryReport> findByUserAndReportType(User user,List<AnalyticsReportTypes> types) {
		return analyticsReportDAO.findByUserAndReportType(user, types);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<String> findStringFilterListByReport(TrendsReport report) {
		return analyticsReportDAO.findStringFilterListByReport(report);
	}
}
