/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.nlp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author jose
 *
 */
@Entity
@Table(name = "perm_ner_configuration")
public class TweetExtractorNERConfiguration implements Serializable{
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 6678436048105188080L;
	@EmbeddedId
	private TweetExtractorNERConfigurationID identifier;
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="configuration")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TweetExtractorNamedEntity> namedEntities = new ArrayList<>();
	/**
	 * 
	 */
	public TweetExtractorNERConfiguration() {
		super();
	}
	
	/**
	 * @param identifier
	 */
	public TweetExtractorNERConfiguration(TweetExtractorNERConfigurationID identifier) {
		super();
		this.identifier = identifier;
	}

	/**
	 * @return the identifier
	 */
	public TweetExtractorNERConfigurationID getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(TweetExtractorNERConfigurationID identifier) {
		this.identifier = identifier;
	}
	/**
	 * @return the namedEntities
	 */
	public List<TweetExtractorNamedEntity> getNamedEntities() {
		return namedEntities;
	}
	/**
	 * @param namedEntities the namedEntities to set
	 */
	public void setNamedEntities(List<TweetExtractorNamedEntity> namedEntities) {
		this.namedEntities = namedEntities;
	}
	
}
