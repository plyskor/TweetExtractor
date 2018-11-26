/**
 * 
 */
package es.uam.eps.tweetextractor.model.filter.impl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.FilterTypes;
import es.uam.eps.tweetextractor.model.filter.Filter;


/**
 * @author Jose Antonio García del Saz
 *
 */
@Entity
@Table(name="perm_fiter_attitude")
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_ATTITUDE)
public class FilterAttitude extends Filter {
	/**
	 * 
	 */
	public FilterAttitude() {
		this.setLABEL(Constants.STRING_FILTER_ATTITUDE);
	}
	
	@Override
	public String toQuery() {
		return null;
	}
	@Override
	public void loadXml() {
		// TODO Auto-generated method stub
		
	}

}
