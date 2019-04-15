/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;

@NamedQuery(name="findAnalyticsReportCategoryRegisterByCategory", query="SELECT reg from AnalyticsReportCategoryRegister reg where reg.category.identifier=:id")


/**
 * @author jose
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AnalyticsReportCategoryRegister extends AnalyticsReportRegister {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 5944175957287962911L;
	@ManyToOne
	private AnalyticsReportCategory category;
	/**
	 * 
	 */
	public AnalyticsReportCategoryRegister() {
		super();
	}
	/**
	 * @return the category
	 */
	@XmlTransient
	public AnalyticsReportCategory getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(AnalyticsReportCategory category) {
		this.category = category;
	}
	
}
