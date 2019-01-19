package es.uam.eps.tweetextractorfx.view.dialog.credentials;

import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractor.dao.service.inter.CredentialsServiceInterface;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.model.Credentials;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCredentialsDialogControl {
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
	private MainApplication mainApplication;
	
	public AddCredentialsDialogControl() {
		super();
	}
	/**
	 * @return the consumerKeyField
	 */
	public TextField getConsumerKeyField() {
		return consumerKeyField;
	}
	/**
	 * @param consumerKeyField the consumerKeyField to set
	 *
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
		accountScreenNameField.setText("jgarciasaz");
		consumerKeyField.setText("WHnn9ajf9fRiEjoQ400vJjR28");
		consumerSecretField.setText("YmtYa3xLn8bhix0mqq90We3ldVGfX2laqDlIhxY31X07Psz7Bp");
		accessTokenField.setText("985480472896724997-9pXqJgxLfDseps3ZvVRaz2IQjtht13j");
		accessTokenSecretField.setText("TWffQRGH4WwPnSgjCvlRVwHN9gpsbn1TelzktzSu2NHWa");
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
		Credentials credentials = new Credentials();
		credentials.setAccountScreenName(accountScreenNameField.getText().trim());
		credentials.setConsumerKey(consumerKeyField.getText().trim());
		credentials.setConsumerSecret(consumerSecretField.getText().trim());
		credentials.setAccessToken(accessTokenField.getText().trim());
		credentials.setAccessTokenSecret(accessTokenSecretField.getText().trim());
		if(this.getMainApplication().getCurrentUser().hasCredentials(credentials.getAccountScreenName())||this.getMainApplication().getCurrentUser().hasCredentials(credentials)) {
				ErrorDialog.showErrorExistingCredentials();
				return;
		}
		this.getMainApplication().getCurrentUser().addCredentials(credentials);
		try {
			CredentialsServiceInterface credentialsService = mainApplication.getSpringContext().getBean(CredentialsServiceInterface.class);
			credentialsService.persist(credentials);
		} catch (Exception e) {
			ErrorDialog.showErrorSaveCredentials(e.getMessage());
			return;
		}
		this.dialogStage.close();
	}
	public void setCredentials(Credentials credentials) {
		if (credentials==null) {
			accountScreenNameField.setText("");
			consumerKeyField.setText("");
			consumerSecretField.setText("");
			accessTokenField.setText("");
			accessTokenSecretField.setText("");
			return;
		}
		accountScreenNameField.setText(credentials.getAccountScreenName());
		consumerKeyField.setText(credentials.getConsumerKey());
		consumerSecretField.setText(credentials.getConsumerSecret());
		accessTokenField.setText(credentials.getAccessToken());
		accessTokenSecretField.setText(credentials.getAccessTokenSecret());
	}
	
}
