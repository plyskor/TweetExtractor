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
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_URL)
public class FilterUrl extends Filter {
	@Column(name="url")
	private String url= new String();
//new String("Con una URL que contiene: ");
	/**
	 * 
	 */
	public FilterUrl() {
		this.summary=new String("With an URL that contains: ");
		this.setLABEL(Constants.STRING_FILTER_URL);
	}
	public FilterUrl(FilterUrl filter) {
		this.summary=new String("With an URL that contains: ");
		this.setLABEL(Constants.STRING_FILTER_URL);
		if(filter!=null) {
			summary=filter.getSummary();
			summaryProperty.set(filter.getSummary());
			this.url=filter.getUrl();
			}
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url=url;
		summary=summary.concat("'"+url+"'");
		summaryProperty.set(summary);
	}

	@Override
	public String toQuery() {
		if(url==null) {
			return null;
		}else {
			return new String("url:"+url+" ");
		}
	}
	@Override
	public void loadXml() {
		// TODO Auto-generated method stub
		
	}

}
