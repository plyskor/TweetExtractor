/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics.config;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;

/**
 * @author jose
 *
 */
public abstract class SpecificGraphicChartPreferencesController extends TweetExtractorFXController {
	private AnalyticsReportImageTypes chartTypeInput;
	
	private AnalyticsRepresentableReport reportInput;

	private TweetExtractorChartGraphicPreferences preferencesInput;
	
	private AnalyticsReportImage chart;
	/**
	 * 
	 */
	/**
	 * @param chartTypeInput
	 * @param reportInput
	 * @param preferencesInput
	 */
	public SpecificGraphicChartPreferencesController(AnalyticsReportImageTypes chartTypeInput,
			AnalyticsRepresentableReport reportInput, TweetExtractorChartGraphicPreferences preferencesInput) {
		super();
		this.chartTypeInput = chartTypeInput;
		this.reportInput = reportInput;
		this.preferencesInput = preferencesInput;
	}
	
	public SpecificGraphicChartPreferencesController() {
		super();
	}
	
	/**
	 * @return the chartTypeInput
	 */
	public AnalyticsReportImageTypes getChartTypeInput() {
		return chartTypeInput;
	}
	/**
	 * @param chartTypeInput the chartTypeInput to set
	 */
	public void setChartTypeInput(AnalyticsReportImageTypes chartTypeInput) {
		this.chartTypeInput = chartTypeInput;
	}

	/**
	 * @return the reportInput
	 */
	public AnalyticsRepresentableReport getReportInput() {
		return reportInput;
	}
	/**
	 * @param reportInput the reportInput to set
	 */
	public void setReportInput(AnalyticsRepresentableReport reportInput) {
		this.reportInput = reportInput;
	}
	/**
	 * @return the preferencesInput
	 */
	public TweetExtractorChartGraphicPreferences getPreferencesInput() {
		return preferencesInput;
	}
	/**
	 * @param preferencesInput the preferencesInput to set
	 */
	public void setPreferencesInput(TweetExtractorChartGraphicPreferences preferencesInput) {
		this.preferencesInput = preferencesInput;
	}

	/**
	 * @return the chart
	 */
	public AnalyticsReportImage getChart() {
		return chart;
	}

	/**
	 * @param chart the chart to set
	 */
	public void setChart(AnalyticsReportImage chart) {
		this.chart = chart;
	}


}
