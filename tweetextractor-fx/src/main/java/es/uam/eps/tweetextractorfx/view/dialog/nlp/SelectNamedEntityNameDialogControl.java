/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.nlp;

import org.apache.commons.lang3.StringUtils;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author jose
 *
 */
public class SelectNamedEntityNameDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private TextField nameField;
	private TweetExtractorNERConfiguration preferences;
	private TweetExtractorNamedEntity namedEntity;


	/**
	 * 
	 */
	public SelectNamedEntityNameDialogControl() {
		super();
		this.getResponse().setIntValue(Constants.ERROR);
	}
	@FXML
	private void onDone() {
		if(StringUtils.isBlank(nameField.getText())) {
			ErrorDialog.showErrorEmptyName("named entity");
			return;
		}
		if(nameField.getText().trim().length()>=Constants.MAX_CHARS_NAMED_ENTITY_NAME) {
			ErrorDialog.showErrorNameTooLong(Constants.MAX_CHARS_NAMED_ENTITY_NAME);
			return;
		}
		if(preferences.containsNamedEntity(nameField.getText().trim())) {
			ErrorDialog.showErrorNamedEntityAlreadyExists();
			return;
		}
		this.getResponse().setStringValue(nameField.getText().trim());
		this.getResponse().setIntValue(Constants.SUCCESS);
		this.dialogStage.close();
	}
	
	/**
	 * @return the nameField
	 */
	public TextField getNameField() {
		return nameField;
	}
	/**
	 * @param nameField the nameField to set
	 */
	public void setNameField(TextField nameField) {
		this.nameField = nameField;
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
	/**
	 * @return the namedEntity
	 */
	public TweetExtractorNamedEntity getNamedEntity() {
		return namedEntity;
	}
	/**
	 * @param namedEntity the namedEntity to set
	 */
	public void setNamedEntity(TweetExtractorNamedEntity namedEntity) {
		this.namedEntity = namedEntity;
	}


}
