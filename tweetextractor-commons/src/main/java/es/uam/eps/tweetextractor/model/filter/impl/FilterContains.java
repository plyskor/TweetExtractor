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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
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
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_CONTAINS)
@XmlRootElement(name="filterContains")
public class FilterContains extends Filter {
	@XmlTransient
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_filter_contains_word_list", joinColumns=@JoinColumn(name="filter"))
	@Column(name="keyword_list", length=20)
	private List<String> keywordsList=new ArrayList<>();
	public FilterContains() {
		this.summary="Contains: ";
		this.setLABEL(Constants.STRING_FILTER_CONTAINS);
	}

	public FilterContains(FilterContains filter) {
		this.summary="Contains: ";
		if(filter!=null) {
			for(String word:filter.getKeywordsList()){
				keywordsList.add(word);
			}
			summary=filter.getSummary();
			summaryProperty.set(filter.getSummary());
		}
	}


	/**
	 * @param keywordsList the keywordsList to set
	 */
	public void setKeywordsList(ObservableList<String> keywordsList) {
		this.keywordsList = keywordsList;
	}

	/**
	 * @return the keywordsXmlList
	 */
	
	@XmlElementWrapper(name = "keywordList")
    @XmlElement(name = "keyword")
	public List<String> getKeywordsList() {
		return keywordsList;
	}

	/**
	 * @param keywordsXmlList the keywordsXmlList to set
	 */
	public void setKeywordsList(List<String> keywordsXmlList) {
		this.keywordsList = keywordsXmlList;
	}
	
	public void addKeywordWord(String word) {
		loadKeywordWord(word);
	}
	public void loadKeywordWord(String word) {
		if(keywordsList.isEmpty()) {
			summary=summary.concat(word);
			summaryProperty.set(summary);
		}else {
			summary=summary.concat(", "+word);
			summaryProperty.set(summary);
		}
		keywordsList.add(word);
	}
	@Override
	public String toQuery() {
		String ret = "";
		if(keywordsList!=null) {
			for(String keyWord:keywordsList) {
				ret=ret.concat(keyWord+" ");
			}
			return ret;
		}else {
			return null;
		}	
	}

	@Override
	public void loadXml() {
		for(String keyWord:keywordsList) {
			loadKeywordWord(keyWord);
		}
	}

}