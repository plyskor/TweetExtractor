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
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReport;

/**
 * @author jose
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = AnalyticsReportTypes.Values.TYPE_TIMELINE_TOP_N_HASHTAGS_REPORT)
@XmlRootElement(name = "timelineVolumeReport")
public class TimelineTopNHashtagsReport extends TimelineReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -2391668213402450134L;
	private int nHashtags=0;

	
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
		return null;
	}

	@Override
	public XYDataset constructXYDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	@Override
	public IntervalXYDataset constructIntervalXYDataset(List<PlotStrokeConfiguration> categories) {
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

}
