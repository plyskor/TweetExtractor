/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
@DiscriminatorValue(value = AnalyticsReportTypes.Values.TYPE_TIMELINE_VOLUME_REPORT)
@XmlRootElement(name = "timelineVolumeReport")
public class TimelineVolumeReport extends TimelineReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 2185283684438833279L;

	/**
	 * 
	 */
	public TimelineVolumeReport() {
		super();
		this.setCategories(new ArrayList<>());
	}

	@Override
	public DefaultCategoryDataset constructDefaultCategoryDataset(List<PlotStrokeConfiguration> categoriesConf) {
		if (this.getCategories() != null && categoriesConf != null) {
			PlotStrokeConfiguration nTweetsCategory = categoriesConf.get(0);
			DefaultCategoryDataset ret = new DefaultCategoryDataset();
			Calendar c = Calendar.getInstance();
			Date auxDate;
			for (AnalyticsReportCategory category : this.getCategories()) {
				for (AnalyticsReportRegister register : category.getResult()) {
					TimelineReportVolumeRegister castedRegister = (TimelineReportVolumeRegister)register;
					c.set(castedRegister.getYear(), castedRegister.getMonth() - 1, castedRegister.getDay(), 0, 0);
					auxDate = c.getTime();
					ret.addValue((Number) castedRegister.getValue(), nTweetsCategory.getCategoryLabel(), auxDate);
				}
			}
			return ret;
		}
		return null;
	}

	@Override
	public XYDataset constructXYDataset(List<PlotStrokeConfiguration> categoriesConf) {
		if (this.getCategories() != null && categoriesConf != null) {
			PlotStrokeConfiguration nTweetsCategory = categoriesConf.get(0);
			TimeSeries series = new TimeSeries(nTweetsCategory.getCategoryLabel());
			final XYDataset dataset = new TimeSeriesCollection(series);
			Calendar c = Calendar.getInstance();
			Date auxDate;
			series.clear();
			for (AnalyticsReportCategory category : this.getCategories()) {
				for (AnalyticsReportRegister register : category.getResult()) {
					TimelineReportVolumeRegister castedRegister = (TimelineReportVolumeRegister)register;
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
	public IntervalXYDataset constructIntervalXYDataset(List<PlotStrokeConfiguration> categoriesConf) {
		if (this.getCategories() != null && categoriesConf != null) {
			PlotStrokeConfiguration nTweetsCategory = categoriesConf.get(0);
			TimeSeries series = new TimeSeries(nTweetsCategory.getCategoryLabel());
			final IntervalXYDataset dataset = new TimeSeriesCollection(series);
			Calendar c = Calendar.getInstance();
			Date auxDate;
			series.clear();
			for (AnalyticsReportCategory category : this.getCategories()) {
				for (AnalyticsReportRegister register : category.getResult()) {
					TimelineReportVolumeRegister castedRegister = (TimelineReportVolumeRegister)register;
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
	public PieDataset constructPieDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}
}
