/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.nlp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import es.uam.eps.tweetextractor.model.Extraction;
@NamedQuery(name="findNERTokenSetByUserAndLanguage", query="SELECT s from TweetExtractorNERTokenSet s WHERE s.identifier.language=:language AND s.identifier.user=:user")

/**
 * @author jose
 *
 */
@Entity
@Table(name = "perm_ner_token_set")
public class TweetExtractorNERTokenSet implements Serializable{
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -5905398943416871040L;
	@EmbeddedId
	private TweetExtractorNERTokenSetID identifier;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "set")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TweetExtractorNERToken> tokenList = new ArrayList<>();
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "token_set_extraction", 
		joinColumns = {@JoinColumn(name = "token_set_language_identifier",referencedColumnName="language_identifier"),@JoinColumn(name = "token_set_user_identifier",referencedColumnName="user_identifier"),@JoinColumn(name = "token_set_name",referencedColumnName="name") }, 
		inverseJoinColumns = {@JoinColumn(name = "extraction_identifier") })
	private List<Extraction> extractions = new ArrayList<>();
	@Column(name = "last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	/**
	 * 
	 */
	public TweetExtractorNERTokenSet() {
		super();
		identifier = new TweetExtractorNERTokenSetID();
	}

	/**
	 * @return the identifier
	 */
	public TweetExtractorNERTokenSetID getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(TweetExtractorNERTokenSetID identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the tokenList
	 */
	public List<TweetExtractorNERToken> getTokenList() {
		return tokenList;
	}

	/**
	 * @param tokenList the tokenList to set
	 */
	public void setTokenList(List<TweetExtractorNERToken> tokenList) {
		this.tokenList = tokenList;
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
	 * @return the lastUpdated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
}
