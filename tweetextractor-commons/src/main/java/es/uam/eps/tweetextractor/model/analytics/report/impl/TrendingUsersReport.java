/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.impl;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.springframework.stereotype.Controller;

import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;

/**
 * @author jose
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = AnalyticsReportTypes.Values.TYPE_TRENDING_USERS_REPORT)
@XmlRootElement(name = "trendingUsersReport")
public class TrendingUsersReport extends TrendsReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 5550672376324792758L;

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport#constructDefaultCategoryDataset(java.util.List)
	 */
	@Override
	public DefaultCategoryDataset constructDefaultCategoryDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport#constructXYDataset(java.util.List)
	 */
	@Override
	public XYDataset constructXYDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport#constructIntervalXYDataset(java.util.List)
	 */
	@Override
	public IntervalXYDataset constructIntervalXYDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	@Override
	public PieDataset constructPieDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

}
