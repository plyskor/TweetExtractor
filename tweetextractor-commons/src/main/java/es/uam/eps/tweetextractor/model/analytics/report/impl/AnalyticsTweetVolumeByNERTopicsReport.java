/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.impl;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsNLPReport;


/**
 * @author jgarciadelsaz
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = AnalyticsReportTypes.Values.TYPE_TWEET_VOLUME_BY_NER_TOPICS)
@XmlRootElement(name = "analyticsTweetVolumeByNERTopicsReport")
public class AnalyticsTweetVolumeByNERTopicsReport extends AnalyticsNLPReport{
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -6150550673392561105L;
	/**
	 * 
	 */
	public AnalyticsTweetVolumeByNERTopicsReport() {
		super();
	}
	
	
}
