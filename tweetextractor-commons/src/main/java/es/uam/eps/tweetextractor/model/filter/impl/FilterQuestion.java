/**
 * 
 */
package es.uam.eps.tweetextractor.model.filter.impl;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.filter.Filter;


/**
 * @author Jose Antonio García del Saz
 *
 */
public class FilterQuestion extends Filter {
	/**
	 * 
	 */
	public FilterQuestion() {
		this.summary=new String();
		this.setLABEL(Constants.STRING_FILTER_QUESTION);	
		}


	@Override
	public String toQuery() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void loadXml() {
		// TODO Auto-generated method stub
		
	}

}
