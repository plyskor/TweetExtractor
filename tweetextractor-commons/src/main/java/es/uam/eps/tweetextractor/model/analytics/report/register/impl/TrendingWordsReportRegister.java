/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register.impl;

import java.util.HashSet;
import java.util.Set;

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
 * @author jose
 *
 */
@Entity
@Table(name="perm_trending_words_report_register")
public class TrendingWordsReportRegister extends AnalyticsReportCategoryRegister {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 6287732682775373508L;
	@Column(name="root")
	private String root;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_trending_words_report_register_terms", joinColumns=@JoinColumn(name="register"))
	@Column(name="term")
	private Set<String> terms = new HashSet<>();
	@Column(name="frequency")
	private int frequency = 0;
	/**
	 * 
	 */
	public TrendingWordsReportRegister() {
		super();
	}
	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}
	/**
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	/**
	 * @return the terms
	 */
	public Set<String> getTerms() {
		return terms;
	}
	/**
	 * @param terms the terms to set
	 */
	public void setTerms(Set<String> terms) {
		this.terms = terms;
	}
	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}
