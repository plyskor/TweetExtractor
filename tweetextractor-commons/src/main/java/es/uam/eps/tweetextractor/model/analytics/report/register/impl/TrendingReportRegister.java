/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;

/**
 * @author jose
 *
 */
@Entity
@Table(name="perm_trending_report_register")
public class TrendingReportRegister extends AnalyticsReportCategoryRegister {
	private static final long serialVersionUID = 4255679154915712731L;
	@Column(name="label")
	private String label;
	@Column(name="volume")
	private int volume;
	public TrendingReportRegister() {
		super();
	}
	/**
	 * @param label
	 * @param volume
	 */
	public TrendingReportRegister(String label, int volume) {
		super();
		this.label = label;
		this.volume = volume;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the volume
	 */
	public int getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
}
