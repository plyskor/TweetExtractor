package es.uam.eps.tweetextractorfx.view.dialog.auth;



import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.task.RegisterAccountTask;
import javafx.fxml.FXML;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.task.status.RegisterStatus;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserDialogControl {
	private Stage dialogStage;
	@FXML
	private TextField userNameField;
	@FXML
	private PasswordField passwordField1;
	@FXML
	private PasswordField passwordField2;
	private MainApplication mainApplication;
	private Stage loadingDialog = null;
	private Alert alertRegister=null; 
	public NewUserDialogControl() {
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
	/**
	 * @return the userNameField
	 */
	public TextField getUserNameField() {
		return userNameField;
	}
	/**
	 * @param userNameField the userNameField to set
	 */
	public void setUserNameField(TextField userNameField) {
		this.userNameField = userNameField;
	}
	/**
	 * @return the passwordField1
	 */
	public PasswordField getPasswordField1() {
		return passwordField1;
	}
	/**
	 * @param passwordField1 the passwordField1 to set
	 */
	public void setPasswordField1(PasswordField passwordField1) {
		this.passwordField1 = passwordField1;
	}
	/**
	 * @return the passwordField2
	 */
	public PasswordField getPasswordField2() {
		return passwordField2;
	}
	/**
	 * @param passwordField2 the passwordField2 to set
	 */
	public void setPasswordField2(PasswordField passwordField2) {
		this.passwordField2 = passwordField2;
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
	public void handleCreateUser() {
		RegisterAccountTask registerTask = new RegisterAccountTask(userNameField.getText().trim(), passwordField1.getText(), passwordField2.getText(),getMainApplication().getSpringContext());
		registerTask.setOnSucceeded(e->{
			RegisterStatus registerResult = registerTask.getValue();
			if (registerResult==null) {
				if(loadingDialog!=null)loadingDialog.close();
				alertRegister=ErrorDialog.showErrorUnknownRegister();
				return;
			}else {
				switch(registerResult.getStatus()){
				case(Constants.UNKNOWN_REGISTER_ERROR):
					alertRegister=ErrorDialog.showErrorUnknownRegister();
					if(loadingDialog!=null)loadingDialog.close();
					break;
				case(Constants.EMPTY_USER_REGISTER_ERROR):
					alertRegister=ErrorDialog.showErrorEmptyUser();
					if(loadingDialog!=null)loadingDialog.close();
					break;
				case(Constants.PASSWORD_MISMATCH_REGISTER_ERROR):
					alertRegister=ErrorDialog.showErrorPasswordMismatch();
					if(loadingDialog!=null)loadingDialog.close();
					break;
				case(Constants.EXIST_USER_REGISTER_ERROR):
					alertRegister=ErrorDialog.showErrorExistingUser();
					if(loadingDialog!=null)loadingDialog.close();
					break;			
				case(Constants.UNSAFE_PASSWORD_REGISTER_ERROR):
					alertRegister=ErrorDialog.showErrorPasswordCheck();
				    if(loadingDialog!=null)loadingDialog.close();
					break;
				case(Constants.SUCCESS_REGISTER):
					this.getMainApplication().getUserList().add(registerResult.getUser());
					alertRegister=ErrorDialog.showSuccessCreateUser();
					if(loadingDialog!=null)loadingDialog.close();
					this.dialogStage.close();
					break;
				default:
					break;
				}
			}
		});
		registerTask.setOnFailed(e->{
			if(loadingDialog!=null)loadingDialog.close();
			alertRegister=ErrorDialog.showErrorUnknownRegister();
		});
		Thread thread = new Thread(registerTask);
        thread.setName(registerTask.getClass().getCanonicalName());
        thread.start();
        loadingDialog=this.getMainApplication().showLoadingDialog("Creating account...");    
        loadingDialog.showAndWait();
        if(alertRegister!=null)alertRegister.showAndWait();
	}
	
	
}
