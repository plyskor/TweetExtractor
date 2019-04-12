/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.auth;


import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.task.status.LoginStatus;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.task.LogInTask;
import es.uam.eps.tweetextractorfx.view.WelcomeScreenControl;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class LoginDialogControl extends TweetExtractorFXDialogController{
	@FXML
	private TextField userField;
	@FXML
	private PasswordField passField;
	private WelcomeScreenControl welcomeScreenControl;
	private Stage loadingDialog = null;
	private Alert alertLogin=null; 


	/*
	 * 
	 */
	public LoginDialogControl() {
		super();
	}


	/**
	 * @return the userField
	 */
	public TextField getUserField() {
		return userField;
	}

	/**
	 * @param userField the userField to set
	 */
	public void setUserField(TextField userField) {
		this.userField = userField;
	}

	/**
	 * @return the passField
	 */
	public PasswordField getPassField() {
		return passField;
	}

	/**
	 * @param passField the passField to set
	 */
	public void setPassField(PasswordField passField) {
		this.passField = passField;
	}
	/**
	 * @return the welcomeScreenControl
	 */
	public WelcomeScreenControl getWelcomeScreenControl() {
		return welcomeScreenControl;
	}

	/**
	 * @param welcomeScreenControl the welcomeScreenControl to set
	 */
	public void setWelcomeScreenControl(WelcomeScreenControl welcomeScreenControl) {
		this.welcomeScreenControl = welcomeScreenControl;
	}

	@FXML
	public void handleCancel() {
		this.getDialogStage().close();
	}

	@FXML
	public void handleLogin() {
		alertLogin=null;
		LogInTask loginTask = new LogInTask(getWelcomeScreenControl().getMainApplication().getSpringContext(),userField.getText().trim(), passField.getText());
		loginTask.setOnSucceeded(e -> {
			LoginStatus loginResult = loginTask.getValue();
			if (loginResult == null) {
	    		if(loadingDialog!=null)loadingDialog.close();
				alertLogin=ErrorDialog.showErrorUnknownLogin();
				return;
			}else {
			switch (loginResult.getStatus()) {
			case (Constants.EMPTY_USER_LOGIN_ERROR):
	    		if(loadingDialog!=null)loadingDialog.close();
				alertLogin=ErrorDialog.showErrorUserEmpty();
				break;
			case (Constants.EXIST_USER_LOGIN_ERROR):
	    		if(loadingDialog!=null)loadingDialog.close();
				alertLogin=ErrorDialog.showErrorExistsUSer();
				break;
			case (Constants.INCORRECT_PASSWORD_LOGIN_ERROR):
	    		if(loadingDialog!=null)loadingDialog.close();
				alertLogin=ErrorDialog.showErrorLoginFailed();
				break;
			case (Constants.EMPTY_PASSWORD_LOGIN_ERROR):
	    		if(loadingDialog!=null)loadingDialog.close();
				alertLogin=ErrorDialog.showErrorPassEmpty();
				break;
			case (Constants.UNKNOWN_LOGIN_ERROR):
				if(loadingDialog!=null)loadingDialog.close();
				alertLogin=ErrorDialog.showErrorUnknownLogin();
				break;
			case (Constants.SUCCESS_LOGIN):
	    		if(loadingDialog!=null)loadingDialog.close();
				this.getWelcomeScreenControl().getMainApplication().setCurrentUser(loginResult.getUser());
				break;
			default:
				break;
				}
			}
		});
		loginTask.setOnFailed(e -> {
			if(loadingDialog!=null)loadingDialog.close();
			alertLogin=ErrorDialog.showErrorUnknownLogin();
		});
		Thread thread = new Thread(loginTask);
        thread.setName(loginTask.getClass().getCanonicalName());
        thread.start();
        loadingDialog=this.getWelcomeScreenControl().getMainApplication().showLoadingDialog("Logging in...");    
        loadingDialog.showAndWait();
        if(alertLogin!=null) {
        	alertLogin.showAndWait();
        }else {
        	this.getDialogStage().close();
        	this.getWelcomeScreenControl().getMainApplication().getRootLayoutController().addOptionsMenu();
    		this.getWelcomeScreenControl().getMainApplication().showScreenInCenterOfRootLayout("view/HomeScreen.fxml");        }
        
	}

	@FXML
	public void handleRegister() {
		this.getWelcomeScreenControl().showNewAccountDialog();
		this.getDialogStage().close();

	}

}
