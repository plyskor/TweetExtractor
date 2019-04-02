/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.graphics;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;

/**
 * @author jose
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value=AnalyticsReportImageTypes.Values.TYPE_TIME_SERIES_CHART)
public class XYChartGraphicPreferences extends TweetExtractorChartGraphicPreferences {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 81784048182671939L;

	/**
	 * 
	 */
	public XYChartGraphicPreferences() {
		super();
	}

}
