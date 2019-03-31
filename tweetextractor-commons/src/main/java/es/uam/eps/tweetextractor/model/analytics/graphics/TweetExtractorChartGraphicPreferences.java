/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.graphics;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;

/**
 * @author jose
 *
 */
@Entity
@Table(name="perm_analytics_report_graphics_preferences")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Polymorphism(type = PolymorphismType.IMPLICIT)
@DiscriminatorColumn(name = "graphic_type",length=5, discriminatorType = DiscriminatorType.STRING)
public abstract class TweetExtractorChartGraphicPreferences {
	@XmlTransient
	@Column(name = "graphic_type", length=5,nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	public AnalyticsReportImageTypes chartType;
	/**
	 * 
	 */
	public TweetExtractorChartGraphicPreferences() {
		super();
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
