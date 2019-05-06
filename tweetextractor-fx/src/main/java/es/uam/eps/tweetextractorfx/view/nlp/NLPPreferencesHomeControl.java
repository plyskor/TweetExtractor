/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.nlp;



import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorfx.view.dialog.nlp.ShowSupportedLanguagesReferenceDialogControl;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public class NLPPreferencesHomeControl extends TweetExtractorFXController {
	@FXML
	private ImageView logoView;
	@FXML
	private Text userView;
	public NLPPreferencesHomeControl() {
		super();
	}
	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		String nickName = this.getMainApplication().getCurrentUser().getNickname().substring(0, 1).toUpperCase()
				+ this.getMainApplication().getCurrentUser().getNickname().substring(1);
		userView.setText(nickName);
	}
	@FXML
	private void initialize() {
		logoView.setImage(new Image("icon.png"));
	}
	@FXML
	private void onSupportedTwitterLanguages() {
		this.getMainApplication().showDialogLoadFXML("view/dialog/nlp/ShowSupportedLanguagesReferenceDialog.fxml", ShowSupportedLanguagesReferenceDialogControl.class);
	}
	@FXML
	private void onManageStopWordsLists() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/ManageCustomStopWordsList.fxml");
	}
	@FXML
	private void onManageNERPreferences() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/MyNERPreferences.fxml");
	}
	@FXML
	private void onBack() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/HomeScreen.fxml"); 
	}
	/**
	 * @return the logoView
	 */
	public ImageView getLogoView() {
		return logoView;
	}
	/**
	 * @param logoView the logoView to set
	 */
	public void setLogoView(ImageView logoView) {
		this.logoView = logoView;
	}
	/**
	 * @return the userView
	 */
	public Text getUserView() {
		return userView;
	}
	/**
	 * @param userView the userView to set
	 */
	public void setUserView(Text userView) {
		this.userView = userView;
	}
	
}
