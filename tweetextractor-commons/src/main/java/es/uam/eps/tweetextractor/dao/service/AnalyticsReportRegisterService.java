/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.uam.eps.tweetextractor.dao.AnalyticsReportRegisterDAO;
import es.uam.eps.tweetextractor.dao.service.inter.AnalyticsReportRegisterServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReportRegister;

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

	
}
