package es.uam.eps.tweetextractor.dao;

import org.springframework.stereotype.Repository;

import es.uam.eps.tweetextractor.dao.inter.AnalyticsReportDAOInterface;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;

@Repository
public class AnalyticsReportDAO extends AbstractGenericDAO<AnalyticsReport,Integer> implements AnalyticsReportDAOInterface<AnalyticsReport> {

	public AnalyticsReportDAO() {
		super();
	}
}
