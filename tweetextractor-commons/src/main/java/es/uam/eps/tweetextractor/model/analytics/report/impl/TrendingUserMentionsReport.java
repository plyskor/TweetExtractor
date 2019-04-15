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
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;

/**
 * @author jose
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = AnalyticsReportTypes.Values.TYPE_TRENDING_USERS_MENTION_REPORT)
@XmlRootElement(name = "trendingUserMentionsReport")
public class TrendingUserMentionsReport extends TrendsReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -909759620369014298L;

}
