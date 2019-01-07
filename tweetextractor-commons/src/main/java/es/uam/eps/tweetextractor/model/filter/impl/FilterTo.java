/**
 * 
 */
package es.uam.eps.tweetextractor.model.filter.impl;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.FilterTypes;
import es.uam.eps.tweetextractor.model.filter.Filter;


/**
 * @author Jose Antonio García del Saz
 *
 */
@Entity
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_TO)
public class FilterTo extends Filter {
	@Column(name="to_nickname")
	private String nickName= "";
	
	public FilterTo (FilterTo filter) {
		this.summary="Replying to: @";
		this.setLABEL(Constants.STRING_FILTER_TO);
		if(filter!=null) {
			summary=filter.getSummary();
			summaryProperty.set(filter.getSummary());
			this.nickName=filter.getNickName();
		}
	}

	/**
	 * 
	 */
	public FilterTo() {
		this.summary="Replying to: @";
		this.setLABEL(Constants.STRING_FILTER_TO);
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

	@Override
	public String toQuery() {
		return null;
	}
	@Override
	public void loadXml() {
		
	}

}
