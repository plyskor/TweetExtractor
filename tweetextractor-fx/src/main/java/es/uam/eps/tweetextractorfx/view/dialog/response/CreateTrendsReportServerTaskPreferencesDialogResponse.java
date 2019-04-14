/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.response;

import java.util.List;

import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;

/**
 * @author jose
 *
 */
public class CreateTrendsReportServerTaskPreferencesDialogResponse extends TweetExtractorFXDialogResponse  {
	private List<String> filterList;
	private AnalyticsReportTypes type;
	/**
	 * 
	 */
	public CreateTrendsReportServerTaskPreferencesDialogResponse() {
		super();
	}

	/**
	 * @param intValue
	 * @param stringValue
	 */
	public CreateTrendsReportServerTaskPreferencesDialogResponse(int intValue, String stringValue) {
		super(intValue, stringValue);
	}
	/**
	 * @return the filterList
	 */
	public List<String> getFilterList() {
		return filterList;
	}

	/**
	 * @param filterList the filterList to set
	 */
	public void setFilterList(List<String> filterList) {
		this.filterList = filterList;
	}

	/**
	 * @return the type
	 */
	public AnalyticsReportTypes getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AnalyticsReportTypes type) {
		this.type = type;
	}
	
}
