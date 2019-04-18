/**
 * 
 */
package es.uam.eps.tweetextractor.model.reference.nlp;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author jgarciadelsaz
 *
 */
@Embeddable
public class CustomStopWordsListID implements Serializable{
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -5658827472489473561L;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "language_identifier")
	private AvailableTwitterLanguage language;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_identifier")
	private User user;
	@Column(name="name")
	private String name;
	/**
	 * 
	 */
	public CustomStopWordsListID() {
		super();
	}
	
	/**
	 * @param language
	 * @param user
	 * @param name
	 */
	public CustomStopWordsListID(AvailableTwitterLanguage language, User user, String name) {
		super();
		this.language = language;
		this.user = user;
		this.name = name;
	}

	/**
	 * @return the language
	 */
	public AvailableTwitterLanguage getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(AvailableTwitterLanguage language) {
		this.language = language;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (!(o instanceof CustomStopWordsListID)) return false;
        CustomStopWordsListID that = (CustomStopWordsListID) o;
        return Objects.equals(getLanguage().getShortName(), that.getLanguage().getShortName()) &&
         Objects.equals(getUser().getIdDB(), that.getUser().getIdDB())&&Objects.equals(getName(), that.getName()); 
	}
	@Override
	public int hashCode() {
        return Objects.hash(language.getShortName(), user.getIdDB(),name);
	}
	
}
