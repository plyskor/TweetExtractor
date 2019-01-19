package es.uam.eps.tweetextractor.model.filter.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.filter.Filter;
/**
 * @author Jose Antonio García del Saz
 *
 */
@XmlRootElement(name="filterContainsExact")
public class FilterContainsExact extends Filter {
	@XmlTransient
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_filter_contains_exact_word_list", joinColumns=@JoinColumn(name="filter"))
	@Column(name="keyword_list", length=20)
	private List<String> keywordsList=new ArrayList<>();
	
	public FilterContainsExact() {
		this.summary="Contains exactly: ";
		this.setLABEL(Constants.STRING_FILTER_CONTAINS_EXACT);
	}
	
	public FilterContainsExact(FilterContainsExact filter) {
		this.summary="Contains exactly: ";
		this.setLABEL(Constants.STRING_FILTER_CONTAINS_EXACT);
		if(filter!=null) {
			for(String word:filter.getKeywordsList()){
				keywordsList.add(word);
			}
			summary=filter.getSummary();
			summaryProperty.set(filter.getSummary());
		}
	}

	/**
	 * @return the keywordsList
	 */
	@XmlTransient
	public List<String> getKeywordsList() {
		return keywordsList;
	}
	/**
	 * @param keywordsList the keywordsList to set
	 */
	public void setKeywordsList(List<String> keywordsList) {
		this.keywordsList = keywordsList;
	}
	public void addKeywordWord(String word) {
		loadKeywordWord(word);
	}
	public void loadKeywordWord(String word) {
		if(keywordsList.isEmpty()) {
			summary=summary.concat("\""+word+"\"");
			summaryProperty.set(summary);
		}else {
			summary=summary.concat(", \""+word+"\"");
			summaryProperty.set(summary);
		}
		keywordsList.add(word);
	}
	@Override
	public String toQuery() {
		String ret ="";
		if(keywordsList!=null) {
			for(String expression:keywordsList) {
				ret=ret.concat("\""+expression+"\" ");
			}
			return ret;
		}else {
			return null;
		}
	}
	@Override
	public void loadXml() {
		throw(new UnsupportedOperationException());
	}
}
