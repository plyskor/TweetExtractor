/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Controller;

import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;

/**
 * @author jose
 *
 */
@Entity
@Controller
@Table(name = "perm_analytics_report_category")
public class AnalyticsReportCategory implements Serializable {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 7318144657555791455L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int identifier;
	@Column(name = "category_name")
	private String categoryName;
	@OneToMany(fetch=FetchType.EAGER,cascade = {CascadeType.ALL},mappedBy="category")
	private List<AnalyticsReportCategoryRegister> result;
	@ManyToOne
	@XmlTransient
	private AnalyticsCategoryReport report;
	public AnalyticsReportCategory() {
		super();
		result = new ArrayList<>();
	}
	/**
	 * @param categoryName the name
	 */
	public AnalyticsReportCategory(String categoryName) {
		super();
		this.categoryName = categoryName;
		result = new ArrayList<>();
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
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the result
	 */
	public List<AnalyticsReportCategoryRegister> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(List<AnalyticsReportCategoryRegister> result) {
		this.result = result;
	}
	/**
	 * @return the report
	 */
	public AnalyticsCategoryReport getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsCategoryReport report) {
		this.report = report;
	}


}
