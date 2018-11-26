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
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_HASHTAG)
@XmlRootElement(name="filterHashtag")
public class FilterHashtag extends Filter {
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_filter_hashtag_list", joinColumns=@JoinColumn(name="filter"))
	@Column(name="hashtag_list", length=40)
	private List<String> hashtagList = new ArrayList<String>();
	public FilterHashtag(FilterHashtag filter) {
		this.summary=new String("Hashtags: ");
		this.setLABEL(Constants.STRING_FILTER_HASHTAG);
		if(filter!=null) {
			for(String word:filter.getHashtagList()){
				hashtagList.add(word);
			}
			summary=filter.getSummary();
			summaryProperty.set(filter.getSummary());
		}
	}
	/**
	 * 
	 */
	public FilterHashtag() {
		this.summary=new String("Hashtags: ");
		this.setLABEL(Constants.STRING_FILTER_HASHTAG);
	}


	/**
	 * @return the hashtagList
	 */
	@XmlTransient
	public List<String> getHashtagList() {
		return hashtagList;
	}

	/**
	 * @param hashtagList the hashtagList to set
	 */
	public void setHashtagList(ObservableList<String> hashtagList) {
		this.hashtagList = hashtagList;
	}

	/**
	 * @param hashtag the hashtag to add
	 */
	public void addHashtag(String hashtag) {
		loadHashtag(hashtag);
	}
public void loadHashtag(String hashtag) {
	if(hashtagList.isEmpty()) {
		summary=summary.concat("#"+hashtag);
		summaryProperty.set(summary);
	}else {
		summary=summary.concat(", #"+hashtag);
		summaryProperty.set(summary);
	}
	hashtagList.add(hashtag);
}
	@Override
	public String toQuery() {
		if(hashtagList==null) {
			return null;
		}else {
			String ret=new String("");
			for(String hashtag:hashtagList) {
				ret=ret.concat("#"+hashtag+" ");
			}
			return ret;
		}
	}
	@Override
	public void loadXml() {
			
	}
}
