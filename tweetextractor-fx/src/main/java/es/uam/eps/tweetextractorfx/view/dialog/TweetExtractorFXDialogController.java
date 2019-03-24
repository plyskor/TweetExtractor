/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog;

import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.stage.Stage;

/**
 * @author jose
 *
 */
public abstract class TweetExtractorFXDialogController extends TweetExtractorFXController {
	protected Stage dialogStage;

	/**
	 * 
	 */
	public TweetExtractorFXDialogController() {
		super();
	}

	/**
	 * @return the dialogStage
	 */
	public Stage getDialogStage() {
		return dialogStage;
	}

	/**
	 * @param dialogStage the dialogStage to set
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
