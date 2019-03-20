/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author jose
 *
 */
@Entity
@Table(name="perm_analytics_report_image")
public class AnalyticsReportImage {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int id;
	@Column(name="image")
	private Blob image;
	@ManyToOne
	private AnalyticsReport report;
	/**
	 * 
	 */
	public AnalyticsReportImage() {
		super();
	}
	/**
	 * @return the image
	 */
	public Blob getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Blob image) {
		this.image = image;
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
	 * @return the report
	 */
	public AnalyticsReport getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsReport report) {
		this.report = report;
	}
	
}
