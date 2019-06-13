/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author jgarciadelsaz
 *
 */
public class AnalyticsTweetVolumeByNamedEntitiesReportTopicRegister implements Serializable {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 7631225359403315240L;
	@Column(name="topic_label")
	private String topicLabel;
	@Column(name="value")
	private int value=0;
	public AnalyticsTweetVolumeByNamedEntitiesReportTopicRegister() {
		super();
	}
	
	public AnalyticsTweetVolumeByNamedEntitiesReportTopicRegister(String topicLabel, int value) {
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

}
