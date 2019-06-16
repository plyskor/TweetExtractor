/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
@NamedQuery(name="findAnalyticsReportRegisterByReport", query="SELECT r from AnalyticsReportRegister r WHERE r.category.report=:report")

/**
 * @author jose
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AnalyticsReportRegister implements Serializable{
	@Transient
	private static final long serialVersionUID = -1129809341413845048L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "identifier")
	private int identifier;
	public AnalyticsReportRegister() {
		super();
	}
	/**
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
}
