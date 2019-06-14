/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register.impl;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;

/**
 * @author jgarciadelsaz
 *
 */
@Entity
@Table(name="perm_tweet_volume_by_named_entities_report_register")
public class AnalyticsTweetVolumeByNLPReportRegister extends AnalyticsReportCategoryRegister {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 5574902245502617485L;
	@Column(name="value")
	private int value=0;

	/**
	 * 
	 */
	public AnalyticsTweetVolumeByNLPReportRegister() {
		super();
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
