/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.util.ArrayList;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;

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
		result= new ArrayList<>();
	}

	
}
