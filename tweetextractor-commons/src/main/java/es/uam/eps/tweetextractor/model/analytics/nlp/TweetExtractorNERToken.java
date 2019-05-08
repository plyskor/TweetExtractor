/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.nlp;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
@NamedQuery(name="findNERTokenBySet", query="SELECT t from TweetExtractorNERToken t WHERE t.set.identifier=:setID")

/**
 * @author jose
 *
 */
@Entity
@Table(name = "perm_ner_token")
public class TweetExtractorNERToken implements Serializable {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -4047810742371988250L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int identifier;
	@Column(name="root")
	private String root;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_ner_token_terms_list", joinColumns=@JoinColumn(name="token"))
	@Column(name="term")
	private Set<String> terms = new HashSet<>();
	@Column(name="frequency")
	private int frequency = 0;
	@ManyToOne
	private TweetExtractorNERTokenSet set;
	
	/**
	 * 
	 */
	public TweetExtractorNERToken() {
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
	/**
	 * @return the set
	 */
	public TweetExtractorNERTokenSet getSet() {
		return set;
	}
	/**
	 * @param set the set to set
	 */
	public void setSet(TweetExtractorNERTokenSet set) {
		this.set = set;
	}

}
