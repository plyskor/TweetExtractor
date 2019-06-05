/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.nlp;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import es.uam.eps.tweetextractor.model.Constants;
@NamedQuery(name="findByNamedEntity", query="SELECT t from TweetExtractorTopic t WHERE t.namedEntity.identifier=:id")

/**
 * @author jose
 *
 */
@Entity
@Table(name = "perm_topics")
public class TweetExtractorTopic implements Serializable {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 3881672550244055655L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int identifier;
	@Column(name = "name",nullable=false,length=Constants.MAX_CHARS_TOPIC_NAME)
	private String name;
	@ManyToOne
	private TweetExtractorNamedEntity namedEntity;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "topic_token", joinColumns = {
			@JoinColumn(name = "topic_identifier") }, inverseJoinColumns = {
					@JoinColumn(name = "token_identifier") })
	private List<TweetExtractorNERToken> linkedTokens = new ArrayList<>();
	/**
	 * 
	 */
	public TweetExtractorTopic() {
		super();
	}
	
	/**
	 * @param name
	 * @param namedEntity
	 */
	public TweetExtractorTopic(String name, TweetExtractorNamedEntity namedEntity) {
		super();
		this.name = name;
		this.namedEntity = namedEntity;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the namedEntity
	 */
	public TweetExtractorNamedEntity getNamedEntity() {
		return namedEntity;
	}
	/**
	 * @param namedEntity the namedEntity to set
	 */
	public void setNamedEntity(TweetExtractorNamedEntity namedEntity) {
		this.namedEntity = namedEntity;
	}

	/**
	 * @return the linkedTokens
	 */
	public List<TweetExtractorNERToken> getLinkedTokens() {
		return linkedTokens;
	}

	/**
	 * @param linkedTokens the linkedTokens to set
	 */
	public void setLinkedTokens(List<TweetExtractorNERToken> linkedTokens) {
		this.linkedTokens = linkedTokens;
	}

}
