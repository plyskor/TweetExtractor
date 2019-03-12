/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service;

import org.springframework.stereotype.Service;

import es.uam.eps.tweetextractor.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

/**
 * @author jose
 *
 */
@Service
public class AnalyticsReportService extends GenericService<AnalyticsReport, Integer> implements AnalyticsReportServiceInterface{

	/**
	 * 
	 */
	public AnalyticsReportService() {
		super();
	}

}
