/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.response;

import java.util.ArrayList;
import java.util.List;

import es.uam.eps.tweetextractor.model.Extraction;

/**
 * @author jose
 *
 */
public class SelectExtractionFilterDialogResponse extends TweetExtractorFXDialogResponse {
	private List<Extraction> filter;

	/**
	 * 
	 */
	public SelectExtractionFilterDialogResponse() {
		super();
		filter= new ArrayList<>();
	}

	/**
	 * @param intValue
	 * @param stringValue
	 */
	public SelectExtractionFilterDialogResponse(int intValue, String stringValue) {
		super(intValue, stringValue);
		filter= new ArrayList<>();
	}

	/**
	 * @return the filter
	 */
	public List<Extraction> getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(List<Extraction> filter) {
		this.filter = filter;
	}
	
}
