/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
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
public abstract class TimelineReport extends AnalyticsReport implements Serializable {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -7250000628622657293L;
	@OneToMany(fetch=FetchType.EAGER,cascade = {CascadeType.ALL},mappedBy="report")
	protected List<TimelineReportRegister> result;
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

	/**
	 * @return the result
	 */
	public List<TimelineReportRegister> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<TimelineReportRegister> result) {
		this.result = result;
	}

}
