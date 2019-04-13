/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.response;

import java.util.List;

/**
 * @author jose
 *
 */
public class CreateTimelineTopNHashtagsReportDialogResponse extends TweetExtractorFXDialogResponse {
	public CreateTimelineTopNHashtagsReportDialogResponse() {
		super();
	}
	public CreateTimelineTopNHashtagsReportDialogResponse(int intValue, String stringValue) {
		super(intValue, stringValue);
	}

	private List<String> filterList;

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
	
}
