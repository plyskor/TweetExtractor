/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Controller;
import javax.persistence.JoinColumn;
import es.uam.eps.tweetextractor.model.Extraction;

/**
 * @author jose
 *
 */

@Controller
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TrendsReport extends AnalyticsCategoryReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -5010570674578247720L;
	@Column(name="n")
	private int n;
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	   @JoinTable(name = "trends_report_extraction", 
	         joinColumns = { @JoinColumn(name = "report_identifier") }, 
	         inverseJoinColumns = { @JoinColumn(name = "extraction_identifier") })
    private List<Extraction> extractions = new ArrayList<>();
	@ElementCollection(fetch=FetchType.LAZY)
	@CollectionTable(name="perm_trend_report_filter_list", joinColumns=@JoinColumn(name="report"))
	@Column(name="filter_element")
	private List<String> stringFilterList= new ArrayList<>();
	/**
	 * 
	 */
	public TrendsReport() {
		super();
	}
	
	/**
	 * @param n
	 */
	public TrendsReport(int n) {
		super();
		this.n = n;
	}

	/**
	 * @return the n
	 */
	public int getN() {
		return n;
	}
	/**
	 * @param n the n to set
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * @return the extractions
	 */
	public List<Extraction> getExtractions() {
		return extractions;
	}

	/**
	 * @param extractions the extractions to set
	 */
	public void setExtractions(List<Extraction> extractions) {
		this.extractions = extractions;
	}

	/**
	 * @return the stringFilterList
	 */
	public List<String> getStringFilterList() {
		return stringFilterList;
	}

	/**
	 * @param stringFilterList the stringFilterList to set
	 */
	public void setStringFilterList(List<String> stringFilterList) {
		this.stringFilterList = stringFilterList;
	}

	
}
