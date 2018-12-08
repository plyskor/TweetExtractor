/**
 * 
 */
package es.uam.eps.tweetextractor.util;

import java.util.List;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.filter.Filter;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class FilterManager {

	/**
	 * 
	 */
	public FilterManager() {
		
	}
	public static boolean isFilterListLogic(List<Filter> filterList) {
		if(filterList==null) {
			return false;
		}else {
			boolean ret =true;
			for(Filter filter:filterList) {
				if(!filter.getClass().getCanonicalName().equals(Constants.CLASS_FILTER_NOT)&&!filter.getClass().getCanonicalName().equals(Constants.CLASS_FILTER_OR)) {
					ret=false;
				}
			}
			return ret;
		}
		
	}
	public static String getQueryFromFilters(List<Filter>filterList) {
		if(filterList==null) {
			return null;
		}else {
			String ret= new String("");
			for(Filter filter:filterList) {
				ret=ret.concat(filter.toQuery());
			}
			return ret;
		}
	}

}
