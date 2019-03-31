/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportImageServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReportImage;

/**
 * @author jose
 *
 */
public class DeleteChartTask extends TwitterExtractorFXTask<Integer> {
	private AnalyticsReportImage chart;
	private Logger logger = LoggerFactory.getLogger(DeleteChartTask.class);

	/**
	 * 
	 */
	public DeleteChartTask(AnalyticsReportImage chart,AnnotationConfigApplicationContext context) {
		super(context);
		this.setChart(chart);
	}

	@Override
	protected Integer call() throws Exception {
		if (chart==null)return Constants.ERROR;
		AnalyticsReportImageServiceInterface service = springContext.getBean(AnalyticsReportImageServiceInterface.class);
		service.deleteById(chart.getId());
		logger.info("Chart with ID "+chart.getId()+" has been successfully deleted.");
		return Constants.SUCCESS;
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
