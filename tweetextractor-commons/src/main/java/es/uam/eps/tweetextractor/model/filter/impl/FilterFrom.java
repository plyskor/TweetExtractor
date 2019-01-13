/**
 * 
 */
package es.uam.eps.tweetextractor.model.filter.impl;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.FilterTypes;
import es.uam.eps.tweetextractor.model.filter.Filter;


/**
 * @author Jose Antonio García del Saz
 *
 */
@Entity
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_FROM)
@XmlRootElement(name="filterFrom")
public class FilterFrom extends Filter {
	@Column(name="nickname")
	private String nickName = "";
	public FilterFrom(FilterFrom filter) {
		this.summary="Tweeted by: @";
		this.setLABEL(Constants.STRING_FILTER_FROM);
		if(filter!=null) {
			summary=filter.getSummary();
			summaryProperty.set(filter.getSummary());
			this.nickName=filter.getNickName();
		}
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName=nickName;
		summary=summary.concat(nickName);
		summaryProperty.set(summary);
	}
	/**
	 * 
	 */
	public FilterFrom() {
		this.summary="Tweeted by: @";
		this.setLABEL(Constants.STRING_FILTER_FROM);
	}

	@Override
	public String toQuery() {
		if(nickName==null) {
			return null;
		}else {
			return "from:"+nickName+" ";
		}
	}
	@Override
	public void loadXml() {
		throw(new UnsupportedOperationException());
	}

}
