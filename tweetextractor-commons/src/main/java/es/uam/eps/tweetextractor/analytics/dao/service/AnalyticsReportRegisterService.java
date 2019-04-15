/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.uam.eps.tweetextractor.analytics.dao.AnalyticsReportRegisterDAO;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportRegisterServiceInterface;
import es.uam.eps.tweetextractor.dao.service.GenericService;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;

/**
 * @author jose
 *
 */
@Service
public class AnalyticsReportRegisterService extends GenericService<AnalyticsReportRegister, Integer> implements AnalyticsReportRegisterServiceInterface {
	@Autowired
	private AnalyticsReportRegisterDAO analyticsReportRegisterDAO;
	/**
	 * 
	 */
	public AnalyticsReportRegisterService() {
		super();
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<AnalyticsReportRegister> findByReport(AnalyticsReport report) {
		return analyticsReportRegisterDAO.findByReport(report);
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<AnalyticsReportCategoryRegister> findAnalyticsReportCategoryRegisterByCategory(
			AnalyticsReportCategory category) {
		return analyticsReportRegisterDAO.findAnalyticsReportCategoryRegisterByCategory(category);
	}

	
}
