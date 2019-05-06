/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.nlp;

import org.apache.commons.lang3.StringUtils;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERConfigurationServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author jose
 *
 */
public class CreateNERPreferencesDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private TextField listNameTextField;
	private AvailableTwitterLanguage language;
	private TweetExtractorNERConfigurationServiceInterface pServ;
	private TweetExtractorNERConfiguration newPreferences = new TweetExtractorNERConfiguration();
	/**
	 * 
	 */
	public CreateNERPreferencesDialogControl() {
		super();
	}
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		pServ=this.getMainApplication().getSpringContext().getBean(TweetExtractorNERConfigurationServiceInterface.class);
	}
	@FXML
	private void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	private void onDone() {
		if(listNameTextField==null||StringUtils.isBlank(listNameTextField.getText())) {
			ErrorDialog.showErrorEmptyNERPreferencesName();
		}else {
			newPreferences.getIdentifier().setLanguage(language);
			newPreferences.getIdentifier().setUser(this.getMainApplication().getCurrentUser());
			newPreferences.getIdentifier().setName(listNameTextField.getText());
			if(pServ.existsAny(newPreferences.getIdentifier())) {
				ErrorDialog.showErrorNERPreferencesNameExists();
				return;
			}
			pServ.persist(newPreferences);
			this.dialogStage.close();
		}
	}
	/**
	 * @return the listNameTextField
	 */
	public TextField getListNameTextField() {
		return listNameTextField;
	}
	/**
	 * @param listNameTextField the listNameTextField to set
	 */
	public void setListNameTextField(TextField listNameTextField) {
		this.listNameTextField = listNameTextField;
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
	/**
	 * @return the newPreferences
	 */
	public TweetExtractorNERConfiguration getNewPreferences() {
		return newPreferences;
	}
	/**
	 * @param newPreferences the newPreferences to set
	 */
	public void setNewPreferences(TweetExtractorNERConfiguration newPreferences) {
		this.newPreferences = newPreferences;
	}

}
