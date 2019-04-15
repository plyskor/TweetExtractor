/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;

/**
 * @author jose
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = AnalyticsReportTypes.Values.TYPE_TIMELINE_TOP_N_HASHTAGS_REPORT)
@XmlRootElement(name = "timelineTopNHashtagsReport")
public class TimelineTopNHashtagsReport extends TimelineReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -2391668213402450134L;
	@Column(name="n_hashtags")
	private int nHashtags = 0;

	public TimelineTopNHashtagsReport() {
		super();
	}

	/**
	 * @param nHashtags the number of top hashtags
	 */
	public TimelineTopNHashtagsReport(int nHashtags) {
		super();
		this.nHashtags = nHashtags;
	}

	@Override
	public DefaultCategoryDataset constructDefaultCategoryDataset(List<PlotStrokeConfiguration> categories) {
		if (this.getCategories() != null && categories != null) {
			DefaultCategoryDataset ret = new DefaultCategoryDataset();
			Calendar c = Calendar.getInstance();
			Date auxDate;
			for (PlotStrokeConfiguration category : categories) {
				AnalyticsReportCategory reportCategory = this.getCategoryByName(category.getCategoryName());
				for (AnalyticsReportRegister register : reportCategory.getResult()) {
					TimelineReportVolumeRegister castedRegister = (TimelineReportVolumeRegister) register;
					c.set(castedRegister.getYear(), castedRegister.getMonth() - 1, castedRegister.getDay(), 0, 0);
					auxDate = c.getTime();
					ret.addValue((Number) castedRegister.getValue(), category.getCategoryLabel(), auxDate);
				}

			}
			return ret;
		}
		return null;
	}

	@Override
	public XYDataset constructXYDataset(List<PlotStrokeConfiguration> categories) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		if (this.getCategories() != null && categories != null) {
			for (PlotStrokeConfiguration category : categories) {
				TimeSeries series = new TimeSeries(category.getCategoryLabel());
				Calendar c = Calendar.getInstance();
				Date auxDate;
				series.clear();
				AnalyticsReportCategory reportCategory = this.getCategoryByName(category.getCategoryName());
				for (AnalyticsReportRegister register : reportCategory.getResult()) {
					TimelineReportVolumeRegister castedRegister = (TimelineReportVolumeRegister) register;
					c.set(castedRegister.getYear(), castedRegister.getMonth() - 1, castedRegister.getDay(), 0, 0);
					auxDate = c.getTime();
					series.addOrUpdate(new Day(auxDate), (Number) castedRegister.getValue());
				}
			}
			return dataset;
		}
		return null;
	}

	@Override
	public IntervalXYDataset constructIntervalXYDataset(List<PlotStrokeConfiguration> categories) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		if (this.getCategories() != null && categories != null) {
			for (PlotStrokeConfiguration category : categories) {
				TimeSeries series = new TimeSeries(category.getCategoryLabel());
				dataset.addSeries(series);
				Calendar c = Calendar.getInstance();
				Date auxDate;
				series.clear();
				AnalyticsReportCategory reportCategory = this.getCategoryByName(category.getCategoryName());
				for (AnalyticsReportRegister register : reportCategory.getResult()) {
					TimelineReportVolumeRegister castedRegister = (TimelineReportVolumeRegister) register;
					c.set(castedRegister.getYear(), castedRegister.getMonth() - 1, castedRegister.getDay(), 0, 0);
					auxDate = c.getTime();
					series.addOrUpdate(new Day(auxDate), (Number) castedRegister.getValue());
				}

			}
			return dataset;
		}
		return null;
	}

	/**
	 * @return the nHashtags
	 */
	public int getnHashtags() {
		return nHashtags;
	}

	/**
	 * @param nHashtags the nHashtags to set
	 */
	public void setnHashtags(int nHashtags) {
		this.nHashtags = nHashtags;
	}

	@Override
	public PieDataset constructPieDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

}
