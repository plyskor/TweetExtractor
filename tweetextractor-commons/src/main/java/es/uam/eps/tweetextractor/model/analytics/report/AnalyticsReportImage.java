/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Type;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
@NamedQuery(name="findAnalyticsReportImageByUser", query="SELECT i from AnalyticsReportImage i where i.report.user=:user")
/**
 * @author jose
 *
 */
@Entity
@Table(name="perm_analytics_report_image")
public class AnalyticsReportImage implements Serializable{
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -3309435775765852075L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int id;
	@Lob
	@Column(name="image")
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] image;
	@Column(name = "generation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@ManyToOne
	private AnalyticsRepresentableReport report;
	@XmlTransient
	@Column(name = "graphic_type", length=5,nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	public AnalyticsReportImageTypes chartType;
	/**
	 * 
	 */
	public AnalyticsReportImage() {
		super();
		if(creationDate==null) {
			creationDate= new Date();
		}
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the report
	 */
	public AnalyticsRepresentableReport getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsRepresentableReport report) {
		this.report = report;
	}
	/**
	 * @return the chartType
	 */
	public AnalyticsReportImageTypes getChartType() {
		return chartType;
	}
	/**
	 * @param chartType the chartType to set
	 */
	public void setChartType(AnalyticsReportImageTypes chartType) {
		this.chartType = chartType;
	}
	
}
