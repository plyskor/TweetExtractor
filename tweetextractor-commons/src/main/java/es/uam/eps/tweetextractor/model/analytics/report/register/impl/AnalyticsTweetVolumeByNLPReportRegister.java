/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author jgarciadelsaz
 *
 */
@Entity
@Table(name="perm_tweet_volume_by_named_entities_report_register_values")
public class AnalyticsTweetVolumeByNLPReportRegister implements Serializable {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 7631225359403315240L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "identifier")
	private int identifier;
	@Column(name="topic_label")
	private String topicLabel;
	@Column(name="value")
	private int value=0;
	@ManyToOne
	private AnalyticsTweetVolumeByNLPReportRegisterWrapper register;
	public AnalyticsTweetVolumeByNLPReportRegister() {
		super();
	}
	
	public AnalyticsTweetVolumeByNLPReportRegister(String topicLabel, int value) {
		super();
		this.topicLabel = topicLabel;
		this.value = value;
	}

	public String getTopicLabel() {
		return topicLabel;
	}

	public void setTopicLabel(String topicLabel) {
		this.topicLabel = topicLabel;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public AnalyticsTweetVolumeByNLPReportRegisterWrapper getRegister() {
		return register;
	}

	public void setRegister(AnalyticsTweetVolumeByNLPReportRegisterWrapper register) {
		this.register = register;
	}

}
