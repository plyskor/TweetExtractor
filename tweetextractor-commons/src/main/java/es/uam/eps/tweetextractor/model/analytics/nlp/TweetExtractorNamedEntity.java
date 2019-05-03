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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
@NamedQuery(name="findByConfiguration", query="SELECT e from TweetExtractorNamedEntity e WHERE e.configuration.identifier=:fk")

/**
 * @author jose
 *
 */
@Entity
@Table(name = "perm_named_entities")
public class TweetExtractorNamedEntity implements Serializable {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -2795607404351387969L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int identifier;
	@Column(name = "name",length=75)
	private String name;
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="namedEntity")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TweetExtractorTopic> topics= new ArrayList<>();
	@ManyToOne
	private TweetExtractorNERConfiguration configuration;
	/**
	 * 
	 */
	public TweetExtractorNamedEntity() {
		super();
	}
	
	/**
	 * @param name
	 */
	public TweetExtractorNamedEntity(String name) {
		super();
		this.name = name;
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
	 * @return the topics
	 */
	public List<TweetExtractorTopic> getTopics() {
		return topics;
	}

	/**
	 * @param topics the topics to set
	 */
	public void setTopics(List<TweetExtractorTopic> topics) {
		this.topics = topics;
	}

	/**
	 * @return the configuration
	 */
	public TweetExtractorNERConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(TweetExtractorNERConfiguration configuration) {
		this.configuration = configuration;
	}

}
