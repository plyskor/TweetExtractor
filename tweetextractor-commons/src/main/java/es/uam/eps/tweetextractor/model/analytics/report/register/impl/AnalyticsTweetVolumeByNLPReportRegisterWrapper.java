/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
public class AnalyticsTweetVolumeByNLPReportRegisterWrapper extends AnalyticsReportCategoryRegister {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 5574902245502617485L;
	@OneToMany(fetch=FetchType.EAGER,cascade = {CascadeType.ALL},mappedBy="register")
	private List<AnalyticsTweetVolumeByNLPReportRegister> volumeList;
	
	/**
	 * 
	 */
	public AnalyticsTweetVolumeByNLPReportRegisterWrapper() {
		super();
		volumeList = new ArrayList<>();
	}

	public List<AnalyticsTweetVolumeByNLPReportRegister> getVolumeList() {
		return volumeList;
	}

	public void setVolumeList(List<AnalyticsTweetVolumeByNLPReportRegister> topicsVolumeList) {
		this.volumeList = topicsVolumeList;
	}
	
}
