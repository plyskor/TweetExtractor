/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.impl;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;

/**
 * @author jgarciadelsaz
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = AnalyticsReportTypes.Values.TYPE_TWEET_VOLUME_BY_NAMED_ENTITIES)
@XmlRootElement(name = "timelineTopNHashtagsReport")
public class AnalyticsTweetVolumeByNamedEntitiesReport extends AnalyticsCategoryReport{
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -6150550673392561105L;
	@ManyToOne
	private TweetExtractorNERConfiguration preferences;
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

	@Override
	public PieDataset constructPieDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	/**
	 * @return the preferences
	 */
	public TweetExtractorNERConfiguration getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(TweetExtractorNERConfiguration preferences) {
		this.preferences = preferences;
	}

}
