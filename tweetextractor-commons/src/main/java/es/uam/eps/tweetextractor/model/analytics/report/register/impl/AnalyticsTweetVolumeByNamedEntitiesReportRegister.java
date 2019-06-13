/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;

/**
 * @author jgarciadelsaz
 *
 */
@Entity
@Table(name="perm_tweet_volume_by_named_entities_report_register")
public class AnalyticsTweetVolumeByNamedEntitiesReportRegister extends AnalyticsReportCategoryRegister {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 5574902245502617485L;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_tweet_volume_by_named_entities_register_topic_list", joinColumns=@JoinColumn(name="report"))
	@Column(name="register")
	private List<AnalyticsTweetVolumeByNamedEntitiesReportTopicRegister> topicsVolumeList;
	
	/**
	 * 
	 */
	public AnalyticsTweetVolumeByNamedEntitiesReportRegister() {
		super();
		topicsVolumeList = new ArrayList<>();
	}

	public List<AnalyticsTweetVolumeByNamedEntitiesReportTopicRegister> getTopicsVolumeList() {
		return topicsVolumeList;
	}

	public void setTopicsVolumeList(List<AnalyticsTweetVolumeByNamedEntitiesReportTopicRegister> topicsVolumeList) {
		this.topicsVolumeList = topicsVolumeList;
	}

}
