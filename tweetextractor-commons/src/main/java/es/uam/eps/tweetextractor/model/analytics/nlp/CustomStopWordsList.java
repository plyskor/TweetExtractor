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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

@NamedQuery(name="findByUserAndLanguage", query="SELECT l from CustomStopWordsList l WHERE l.identifier.language=:language AND l.identifier.user=:user")

/**
 * @author jgarciadelsaz
 *
 */
@Entity
@Table(name = "perm_custom_stop_words_list")
public class CustomStopWordsList implements Serializable{
	@XmlTransient
	@Transient
	private static final long serialVersionUID = 1936133348463639661L;
	@EmbeddedId
	private CustomStopWordsListID identifier;
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="list")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<StopWord> list;
	
	/**
	 * 
	 */
	public CustomStopWordsList() {
		super();
		this.identifier = new CustomStopWordsListID();
		this.list= new ArrayList<>();
	}
	/**
	 * @return the identifier
	 */
	@XmlTransient
	public CustomStopWordsListID getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(CustomStopWordsListID identifier) {
		this.identifier = identifier;
	}
	/**
	 * @return the list
	 */
	@XmlTransient
	public List<StopWord> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<StopWord> list) {
		this.list = list;
	}

	public void setUser(User user) {
		this.identifier.setUser(user);
	}
	@XmlTransient
	public User getUser() {
		return this.identifier.getUser();
	}
	public void setLanguage(AvailableTwitterLanguage language) {
		this.identifier.setLanguage(language);;
	}
	@XmlTransient
	public AvailableTwitterLanguage getLanguage() {
		return this.identifier.getLanguage();
	}
	@XmlTransient
	public String getName() {
		return this.identifier.getName();
	}
	public void setName(String name) {
		this.identifier.setName(name);
	}

}
