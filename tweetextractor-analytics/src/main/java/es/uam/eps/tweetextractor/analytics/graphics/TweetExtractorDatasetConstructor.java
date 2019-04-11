/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.graphics;

import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;

import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;

/**
 * @author jgarciadelsaz
 *
 */
public class TweetExtractorDatasetConstructor {
	private AnalyticsRepresentableReport report;
	/**
	 * 
	 */
	public TweetExtractorDatasetConstructor(AnalyticsRepresentableReport report) {
		super();
		this.report=report;
	}
	/**
	 * @return the report
	 */
	public AnalyticsRepresentableReport getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsRepresentableReport report) {
		this.report = report;
	}
	public DefaultCategoryDataset constructDefaultCategoryDataset(List<PlotStrokeConfiguration> categories) {
		if(this.report==null) {
			return null;
		}else {
			return report.constructDefaultCategoryDataset(categories);
		}
	}
	public XYDataset constructXYDataset(List<PlotStrokeConfiguration> categories) {
		if(this.report==null) {
			return null;
		}else {
			return report.constructXYDataset(categories);
		}
	}
	public XYDataset constructIntervalXYDataset(List<PlotStrokeConfiguration> categories) {
		if(this.report==null) {
			return null;
		}else {
			return report.constructIntervalXYDataset(categories);
		}
	}
}
