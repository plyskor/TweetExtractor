/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;
import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;

/**
 * @author jose
 *
 */
@Controller
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value=AnalyticsReportTypes.Values.TYPE_TIMELINE_REPORT)
public abstract class TimelineReport extends AnalyticsCategoryReport implements Serializable {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -7250000628622657293L;

	/**
	 * 
	 */
	public TimelineReport() {
		super();
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
