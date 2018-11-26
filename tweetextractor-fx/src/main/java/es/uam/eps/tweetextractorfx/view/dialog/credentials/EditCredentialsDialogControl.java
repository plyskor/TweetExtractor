/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.credentials;

import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractor.dao.service.CredentialsService;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.model.Credentials;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class EditCredentialsDialogControl {

	@FXML
	private TextField accountScreenNameField;
	@FXML
	private TextField consumerKeyField;
	@FXML
	private TextField consumerSecretField;
	@FXML
	private TextField accessTokenField;
	@FXML
	private TextField accessTokenSecretField;
	private Stage dialogStage;
	private Credentials selectedCredentials;
	private MainApplication mainApplication;
	
	public EditCredentialsDialogControl() {
		
	}
	/**
	 * @return the consumerKeyField
	 */
	public TextField getConsumerKeyField() {
		return consumerKeyField;
	}
	/**
	 * @param consumerKeyField the consumerKeyField to set
	 */
	public void setConsumerKeyField(TextField consumerKeyField) {
		this.consumerKeyField = consumerKeyField;
	}
	/**
	 * @return the consumerSecretField
	 */
	public TextField getConsumerSecretField() {
		return consumerSecretField;
	}
	/**
	 * @param consumerSecretField the consumerSecretField to set
	 */
	public void setConsumerSecretField(TextField consumerSecretField) {
		this.consumerSecretField = consumerSecretField;
	}
	/**
	 * @return the accessTokenField
	 */
	public TextField getAccessTokenField() {
		return accessTokenField;
	}
	/**
	 * @param accessTokenField the accessTokenField to set
	 */
	public void setAccessTokenField(TextField accessTokenField) {
		this.accessTokenField = accessTokenField;
	}
	/**
	 * @return the accessTokenSecretField
	 */
	public TextField getAccessTokenSecretField() {
		return accessTokenSecretField;
	}
	
	/**
	 * @return the accountScreenNameField
	 */
	public TextField getAccountScreenNameField() {
		return accountScreenNameField;
	}
	
	/**
	 * @param accountScreenNameField the accountScreenNameField to set
	 */
	public void setAccountScreenNameField(TextField accountScreenNameField) {
		this.accountScreenNameField = accountScreenNameField;
	}
	/**
	 * @param accessTokenSecretField the accessTokenSecretField to set
	 */
	public void setAccessTokenSecretField(TextField accessTokenSecretField) {
		this.accessTokenSecretField = accessTokenSecretField;
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
	@FXML
	public void handleCancel() {
		this.getDialogStage().close();
	}
	@FXML
	public void handleDone() {
		if(accessTokenField.getText().trim().isEmpty()||consumerKeyField.getText().trim().isEmpty()||consumerSecretField.getText().trim().isEmpty()||accessTokenField.getText().trim().isEmpty()||accessTokenSecretField.getText().trim().isEmpty()) {
			ErrorDialog.showErrorEmptyCredentials();
			return;
		}
		Credentials aux = new Credentials();
		aux.setAccountScreenName(accountScreenNameField.getText().trim());
		aux.setConsumerKey(consumerKeyField.getText().trim());
		aux.setConsumerSecret(consumerSecretField.getText().trim());
		aux.setAccessToken(accessTokenField.getText().trim());
		aux.setAccessTokenSecret(accessTokenSecretField.getText().trim());
		if(this.getMainApplication().getCurrentUser().hasCredentials(aux)) {
			ErrorDialog.showErrorExistingCredentials();
			return;
	}
		selectedCredentials.setAccountScreenName(accountScreenNameField.getText().trim());
		selectedCredentials.setConsumerKey(consumerKeyField.getText().trim());
		selectedCredentials.setConsumerSecret(consumerSecretField.getText().trim());
		selectedCredentials.setAccessToken(accessTokenField.getText().trim());
		selectedCredentials.setAccessTokenSecret(accessTokenSecretField.getText().trim());
		try {
			CredentialsService credentialsService = new CredentialsService();
			credentialsService.update(selectedCredentials);
		} catch (Exception e) {
			ErrorDialog.showErrorSaveCredentials(e.getMessage());
			return;
		}
		this.dialogStage.close();
	}
	public void setCredentials(Credentials credentials) {
		if(credentials!=null) {
			selectedCredentials=credentials;
			accessTokenField.setText(credentials.getAccessToken());
			accessTokenSecretField.setText(credentials.getAccessTokenSecret());
			accountScreenNameField.setText(credentials.getAccountScreenName());
			consumerKeyField.setText(credentials.getConsumerKey());;
			consumerSecretField.setText(credentials.getConsumerSecret());
		}
	}
}
