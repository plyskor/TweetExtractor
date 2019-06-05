/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.nlp;
import org.apache.commons.lang3.StringUtils;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERTokenSetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author jgarciadelsaz
 *
 */
public class CreateTokenSetSelectNameDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private TextField nameTextField;
	private AvailableTwitterLanguage language = null;
	private TweetExtractorNERTokenSetServiceInterface tsServ;

	public CreateTokenSetSelectNameDialogControl() {
		super();
		this.getResponse().setIntValue(Constants.ERROR);
	}
	@FXML
	public void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	public void onDone() {
		if(StringUtils.isBlank(nameTextField.getText())) {
			ErrorDialog.showErrorEmptyName("token set");
			return;
		}
		TweetExtractorNERTokenSetID id = new TweetExtractorNERTokenSetID();
		id.setUser(this.getMainApplication().getCurrentUser());
		id.setLanguage(language);
		id.setName(nameTextField.getText());
		if(tsServ.existsAny(id)) {
			return;
		}
		this.getResponse().setIntValue(Constants.SUCCESS);
		this.getResponse().setStringValue(nameTextField.getText());
		this.dialogStage.close();
		
	}
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		tsServ = this.mainApplication.getSpringContext().getBean(TweetExtractorNERTokenSetServiceInterface.class);
	}
	/**
	 * @return the nameTextField
	 */
	public TextField getNameTextField() {
		return nameTextField;
	}
	/**
	 * @param nameTextField the nameTextField to set
	 */
	public void setNameTextField(TextField nameTextField) {
		this.nameTextField = nameTextField;
	}
	/**
	 * @return the language
	 */
	public AvailableTwitterLanguage getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(AvailableTwitterLanguage language) {
		this.language = language;
	}
}
