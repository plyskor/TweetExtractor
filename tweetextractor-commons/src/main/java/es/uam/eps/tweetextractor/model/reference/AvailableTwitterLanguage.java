/**
 * 
 */
package es.uam.eps.tweetextractor.model.reference;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Controller;
@NamedQuery(name="findByShortCode", query="SELECT l from AvailableTwitterLanguage l WHERE l.shortName=:shortCode")

/**
 * @author jgarciadelsaz
 *
 */
@Entity
@Controller
@Table(name = "ref_twitter_language")
public class AvailableTwitterLanguage implements Serializable{
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -9222557650707536086L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int identifier;
	@Column(name = "short_name",unique=true, length=6)
	private String shortName;
	@Column(name = "long_name",unique=true, length=20)
	private String longName;
	
	/**
	 * 
	 */
	public AvailableTwitterLanguage() {
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
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}
	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

}
