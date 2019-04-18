/**
 * 
 */
package es.uam.eps.tweetextractor.model.reference.nlp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
@NamedQuery(name="findByList", query="SELECT w from StopWord w WHERE w.list.identifier=:fk")

/**
 * @author jgarciadelsaz
 *
 */
@Entity
@Table(name = "perm_custom_stop_words")
public class StopWord implements Serializable {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -8045590113573644637L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int identifier;
	@Column(name="stop_word")
	private String stopWord;
	@ManyToOne
	private CustomStopWordsList list;
	/**
	 * 
	 */
	public StopWord() {
		super();
	}
	/**
	 * @return the stopWord
	 */
	public String getStopWord() {
		return stopWord;
	}
	/**
	 * @param stopWord the stopWord to set
	 */
	public void setStopWord(String stopWord) {
		this.stopWord = stopWord;
	}
	/**
	 * @return the list
	 */
	public CustomStopWordsList getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(CustomStopWordsList list) {
		this.list = list;
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

}
