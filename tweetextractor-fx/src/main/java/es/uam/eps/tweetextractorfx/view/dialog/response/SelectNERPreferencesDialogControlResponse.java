/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.response;

import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;

/**
 * @author jgarciadelsaz
 *
 */
public class SelectNERPreferencesDialogControlResponse extends TweetExtractorFXDialogResponse {
	private TweetExtractorNERConfiguration preferences;
	public SelectNERPreferencesDialogControlResponse() {
		super();
	}

	public SelectNERPreferencesDialogControlResponse(int intValue, String stringValue) {
		super(intValue, stringValue);
	}

	/**
	 * @return the preferences
	 */
	public TweetExtractorNERConfiguration getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(TweetExtractorNERConfiguration preferences) {
		this.preferences = preferences;
	}
	
}
