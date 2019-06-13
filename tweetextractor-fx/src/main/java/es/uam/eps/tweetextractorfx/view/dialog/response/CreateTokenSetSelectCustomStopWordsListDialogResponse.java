/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.response;

import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsList;

/**
 * @author jose
 *
 */
public class CreateTokenSetSelectCustomStopWordsListDialogResponse extends TweetExtractorFXDialogResponse {
	private CustomStopWordsList stopWordsList;
	/**
	 * 
	 */
	public CreateTokenSetSelectCustomStopWordsListDialogResponse() {
	}

	/**
	 * @param intValue
	 * @param stringValue
	 */
	public CreateTokenSetSelectCustomStopWordsListDialogResponse(int intValue, String stringValue) {
		super(intValue, stringValue);
	}

	/**
	 * @return the stopWordsList
	 */
	public CustomStopWordsList getStopWordsList() {
		return stopWordsList;
	}

	/**
	 * @param stopWordsList the stopWordsList to set
	 */
	public void setStopWordsList(CustomStopWordsList stopWordsList) {
		this.stopWordsList = stopWordsList;
	}

}
