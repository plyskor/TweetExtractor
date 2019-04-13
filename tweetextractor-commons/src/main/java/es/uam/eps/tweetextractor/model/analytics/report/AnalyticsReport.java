/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
@NamedQuery(name="findAnalyticsReportByUser", query="SELECT r from AnalyticsCategoryReport r where r.user=:user")
@NamedQuery(name="findAnalyticsReportByUserAndReportType", query="SELECT r from AnalyticsCategoryReport r where r.reportType in (:reportTypeList) and r.user=:user")

/**
 * @author jose
 *
 */
@Controller
@Entity
@Table(name="perm_analytics_report")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Polymorphism(type = PolymorphismType.IMPLICIT)
@DiscriminatorColumn(name = "report_type",length=6, discriminatorType = DiscriminatorType.STRING)
public abstract class AnalyticsReport implements Serializable{
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 3263875661727113958L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int id;
	@Column(name = "generation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;
	@ManyToOne
	@XmlTransient
	private User user;
	@XmlTransient
	@Column(name = "report_type", length=6 ,nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	public AnalyticsReportTypes reportType;

	/**
	 * 
	 */
	public AnalyticsReport() {
		super();
		this.creationDate= new Date();
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the reportType
	 */
	public AnalyticsReportTypes getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(AnalyticsReportTypes reportType) {
		this.reportType = reportType;
	}

}
