/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import es.uam.eps.tweetextractor.model.analytics.report.register.TimelineReportRegister;

/**
 * @author jose
 *
 */

@Entity
@Table(name="perm_timeline_report_volume_register")
public class TimelineReportVolumeRegister extends TimelineReportRegister<Integer> {
	
	public TimelineReportVolumeRegister() {
		super();
	}
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -6155124908206248220L;
	@Column(name="value")
	private Integer value;
	@Override
	public Integer getValue() {
		return value;
	}
	@Override
	public void setValue(Integer value) {
		this.value=value;
	}
	
}
