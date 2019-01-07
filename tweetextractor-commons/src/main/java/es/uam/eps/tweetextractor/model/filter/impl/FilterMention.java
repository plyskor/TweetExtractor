/**
 * 
 */
package es.uam.eps.tweetextractor.model.filter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.FilterTypes;
import es.uam.eps.tweetextractor.model.filter.Filter;
import javafx.collections.ObservableList;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Entity
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_MENTION)
@XmlRootElement(name = "filterMention")
public class FilterMention extends Filter {
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_filter_mention_list", joinColumns=@JoinColumn(name="filter"))
	@Column(name="mention_list", length=50)
	private List<String> mentionList = new ArrayList<>();
	
	public FilterMention(FilterMention filter) {
		this.summary="Mentioning: ";
		this.setLABEL(Constants.STRING_FILTER_MENTION);
		if (filter != null) {
			for (String word : filter.getMentionList()) {
				mentionList.add(word);
			}
			summary= filter.getSummary();
			summaryProperty.set(filter.getSummary());
		}
	}

	/**
	 * @return the mentionList
	 */
	@XmlTransient
	public List<String> getMentionList() {
		return mentionList;
	}

	/**
	 * @param mentionList the mentionList to set
	 */
	public void setMentionList(ObservableList<String> mentionList) {
		this.mentionList = mentionList;
	}


	public FilterMention() {
		this.summary="Mentioning: ";
		this.setLABEL(Constants.STRING_FILTER_MENTION);
	}



	public void addMention(String mention) {
		loadMention(mention);
	}

	public void loadMention(String mention) {
		if (mentionList.isEmpty()) {
			summary = summary.concat("@" + mention);
			summaryProperty.set(summary);
		} else {
			summary = summary.concat(", @" + mention);
			summaryProperty.set(summary);
		}
		mentionList.add(mention);
	}

	@Override
	public String toQuery() {
		if (mentionList == null) {
			return null;
		} else {
			String ret = "";
			for (String mention : mentionList) {
				ret = ret.concat("@" + mention + " ");
			}
			return ret;
		}
	}
	@Override
	public void loadXml() {

	}
}
