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
@DiscriminatorValue(value=AnalyticsReportImageTypes.Values.TYPE_PIE_CHART)
public class PieChartConfiguration extends TweetExtractorChartGraphicPreferences {

	@XmlTransient
	@Transient
	private static final long serialVersionUID = 7719996866748292765L;

	/**
	 * @param name
	 */
	public PieChartConfiguration(String name) {
		super(name);
	}

	/**
	 * 
	 */
	public PieChartConfiguration() {
	}

}
