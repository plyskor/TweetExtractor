/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.io.File;
import org.jboss.com.sun.corba.se.impl.io.TypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.analytics.graphics.constructor.TweetExtractorChartConstructor;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;

/**
 * @author jose
 *
 */
public class GenerateWordCloudChartTask extends TweetExtractorFXTask<Integer> {
	private AnalyticsRepresentableReport report;
	private AnalyticsReportImage chart;
	private AnalyticsReportImageTypes chartType;
	private TweetExtractorChartGraphicPreferences config;
	private Logger logger = LoggerFactory.getLogger(GenerateWordCloudChartTask.class);
	private File pixelBoundaryFile;
	public GenerateWordCloudChartTask(AnnotationConfigApplicationContext springContext) {
		super(springContext);
	}
	
	/**
	 * @param springContext
	 * @param report
	 * @param chart
	 * @param chartType
	 * @param config
	 */
	public GenerateWordCloudChartTask(AnnotationConfigApplicationContext springContext,
			AnalyticsRepresentableReport report, AnalyticsReportImage chart, AnalyticsReportImageTypes chartType,
			TweetExtractorChartGraphicPreferences config,File pixelBoundaryFile) {
		super(springContext);
		this.report = report;
		this.chart = chart;
		this.chartType = chartType;
		this.config = config;
		this.pixelBoundaryFile=pixelBoundaryFile;
	}

	@Override
	protected Integer call() throws Exception {
		TweetExtractorChartConstructor constructor = new TweetExtractorChartConstructor(report, chart, AnalyticsReportImageTypes.WCC, config);
		try {
			switch(report.getReportType()) {
			case TVT:
			case TVNE:
				constructor.constructWordCloudChartFromNLPReport(pixelBoundaryFile);
				break;
			case TRWR:
				constructor.constructWordCloudChartFromTrendingWordsReport(pixelBoundaryFile);
				break;
			default:
				throw new TypeMismatchException("Report is not compatible with this type of chart");
			}
		} catch (Exception e) {
			logger.warn("An exception has been thrown creating word cloud chart : "+e.getMessage());
			return Constants.ERROR;
		}
		return Constants.SUCCESS;
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

	/**
	 * @return the chartType
	 */
	public AnalyticsReportImageTypes getChartType() {
		return chartType;
	}

	/**
	 * @param chartType the chartType to set
	 */
	public void setChartType(AnalyticsReportImageTypes chartType) {
		this.chartType = chartType;
	}

	/**
	 * @return the config
	 */
	public TweetExtractorChartGraphicPreferences getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(TweetExtractorChartGraphicPreferences config) {
		this.config = config;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * @return the pixelBoundaryFile
	 */
	public File getPixelBoundaryFile() {
		return pixelBoundaryFile;
	}

	/**
	 * @param pixelBoundaryFile the pixelBoundaryFile to set
	 */
	public void setPixelBoundaryFile(File pixelBoundaryFile) {
		this.pixelBoundaryFile = pixelBoundaryFile;
	}
	
}
