/**
 * 
 */
package es.uam.eps.tweetextractorfx.view;

import es.uam.eps.tweetextractorfx.MainApplication;

/**
 * @author jose
 *
 */
public abstract class TweetExtractorFXController {
	/* Reference to the MainApplication */
	protected MainApplication mainApplication;

	public TweetExtractorFXController() {
		super();
	}

	/**
	 * @return the mainApplication
	 */
	public MainApplication getMainApplication() {
		return mainApplication;
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	public void setMainApplication(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
	}

}
