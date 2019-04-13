/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog;

import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorfx.view.dialog.response.TweetExtractorFXDialogResponse;
import javafx.stage.Stage;

/**
 * @author jose
 *
 */
public abstract class TweetExtractorFXDialogController extends TweetExtractorFXController {
	protected Stage dialogStage;
	private TweetExtractorFXDialogResponse response;
	/**
	 * 
	 */
	public TweetExtractorFXDialogController() {
		super();
		response = new TweetExtractorFXDialogResponse();
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

	/**
	 * @return the response
	 */
	public TweetExtractorFXDialogResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(TweetExtractorFXDialogResponse response) {
		this.response = response;
	}

}
