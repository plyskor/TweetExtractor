/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.response;

import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;

/**
 * @author jose
 *
 */
public class SelectNERTokenSetDialogResponse extends TweetExtractorFXDialogResponse {
	private TweetExtractorNERTokenSet tokenSet=null;
	/**
	 * 
	 */
	public SelectNERTokenSetDialogResponse() {
		super();
	}

	/**
	 * @param intValue
	 * @param stringValue
	 */
	public SelectNERTokenSetDialogResponse(int intValue, String stringValue) {
		super(intValue, stringValue);
	}

	/**
	 * @return the tokenSet
	 */
	public TweetExtractorNERTokenSet getTokenSet() {
		return tokenSet;
	}

	/**
	 * @param tokenSet the tokenSet to set
	 */
	public void setTokenSet(TweetExtractorNERTokenSet tokenSet) {
		this.tokenSet = tokenSet;
	}

}
