/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports;

import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractorfx.MainApplication;

/**
 * @author jose
 *
 */
public abstract class ShowRawDataControl {
	/* Reference to the MainApplication */
	private MainApplication mainApplication;
	private AnalyticsReport report;
	/**
	 * 
	 */
	public ShowRawDataControl() {
		super();
	}
	/**
	 * @return the mainApplication
	 */
	public MainApplication getMainApplication() {
		return mainApplication;
	}
	/**
	 * @param mainApplication the mainApplication to set
	 */
	public void setMainApplication(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
	}
	/**
	 * @return the report
	 */
	public AnalyticsReport getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsReport report) {
		this.report = report;
	}

}
