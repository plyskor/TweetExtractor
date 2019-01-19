/**
 * 
 */
package es.uam.eps.tweetextractorfx.view;

import java.io.IOException;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskUpdateExtractionIndefResponse;
import es.uam.eps.tweetextractor.service.CreateServerTaskUpdateExtractionIndef;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.task.DeleteAccountTask;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import es.uam.eps.tweetextractorfx.view.dialog.ServerPreferencesDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.auth.ChangePasswordDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.credentials.AddCredentialsDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateExtractionServerTaskSelectExtractionDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateExtractionServerTaskSelectTaskTypeDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateServerTaskSelectTaskTypeDialogControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class HomeScreenControl {
	/* Reference to the MainApplication */
	private MainApplication mainApplication;
	@FXML
	private ImageView logoView;
	@FXML
	private Text userView;

	private Stage loadingDialog = null;

	/**
	 * 
	 */
	public HomeScreenControl() {
	}

	@FXML
	private void initialize() {
		Image logo = new Image("icon.png");
		if (logo != null)
			logoView.setImage(logo);
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
		this.getMainApplication().getRootLayoutController().addLogOut();
		String nickName = this.getMainApplication().getCurrentUser().getNickname().substring(0, 1).toUpperCase()
				+ this.getMainApplication().getCurrentUser().getNickname().substring(1);
		userView.setText(nickName);
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

	@FXML
	public void handleCreateExtraction() {
		if (!this.getMainApplication().getCurrentUser().hasAnyCredentials()) {
			ErrorDialog.showErrorNoCredentials();
			return;
		}
		this.getMainApplication().showQueryConstructor();
	}

	@FXML
	public void handleAddCredentials() {
		showAddCredentials();
	}

	@FXML
	public void handleLogOut() {
		this.getMainApplication().getRootLayoutController().logOut();
	}

	@FXML
	public void handleDeleteUser() {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"This action will delete the account " + this.getMainApplication().getCurrentUser().getNickname()
						+ ", and also every extraction owned by it. Are you sure you want to continue?",
				ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			DeleteAccountTask deleteTask = new DeleteAccountTask(this.getMainApplication().getCurrentUser(),mainApplication.getSpringContext());
			deleteTask.setOnSucceeded(e -> {
				this.handleLogOut();
				if (loadingDialog != null)
					loadingDialog.close();
			});
			deleteTask.setOnFailed(e -> {
				if (loadingDialog != null)
					loadingDialog.close();
			});
			Thread thread = new Thread(deleteTask);
			thread.setName(deleteTask.getClass().getCanonicalName());
			thread.start();
			loadingDialog = mainApplication.showLoadingDialog("Deleting account...");
			loadingDialog.showAndWait();
		}
	}

	@FXML
	public void handleManageCredentials() {
		this.getMainApplication().showManageCredentials();
	}

	@FXML
	public void handleChangePassword() {
		showUpdatePassword();
	}

	@FXML
	public void handleManageExtractions() {
		this.getMainApplication().showUserExtractions();
	}
	/* DIALOGOS */

	public void showAddCredentials() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class.getResource("dialog/credentials/AddCredentialsDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the dialogStage to the controller.
			AddCredentialsDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();

			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showUpdatePassword() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class.getResource("dialog/auth/ChangePasswordDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			ChangePasswordDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();

			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showManageServerPreferences() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class.getResource("dialog/ServerPreferencesDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the dialogStage to the controller.
			ServerPreferencesDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();

			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public String showCreateServerTaskSelectTypeDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					HomeScreenControl.class.getResource("server/dialog/CreateServerTaskSelectTaskTypeDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			CreateServerTaskSelectTaskTypeDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getToReturn();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Extraction showCreateExtractionServerTaskSelectExtractionDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					HomeScreenControl.class.getResource("server/dialog/CreateExtractionServerTaskSelectExtractionDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			CreateExtractionServerTaskSelectExtractionDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getToReturn();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public String showCreateExtractionServerTaskSelectTypeDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					HomeScreenControl.class.getResource("server/dialog/CreateExtractionServerTaskSelectTaskTypeDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			CreateExtractionServerTaskSelectTaskTypeDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getToReturn();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	@FXML
	public void onCreateTask() {
		if(!this.getMainApplication().checkServer()){
			ErrorDialog.showErrorConfigureServer();
			return;
		}
		String selectedTaskType;
		selectedTaskType = showCreateServerTaskSelectTypeDialog();
		if (selectedTaskType != null) {
			switch (selectedTaskType) {
			case (Constants.EXTRACTION_SERVER_TASK_TYPE):
				if(this.getMainApplication().getCurrentUser().getExtractionList().isEmpty()) {
					ErrorDialog.showErrorUserHasNoExtraction();
				}else {
					onCreateExtractionTask();
				}
				break;
			default:
				break;
			}
		} else {
			return;
		}

	}
	public void onCreateExtractionTask() {
		Extraction selectedExtraction;
		selectedExtraction = showCreateExtractionServerTaskSelectExtractionDialog();
		if (selectedExtraction == null) {
			onCreateTask();
		} else {
			String selectedTaskType = showCreateExtractionServerTaskSelectTypeDialog();
			switch(selectedTaskType) {
			case(Constants.UPDATE_EXTRACTION_INDEF_SERVER_TASK_TYPE):
				CreateServerTaskUpdateExtractionIndef service = new CreateServerTaskUpdateExtractionIndef(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
				CreateServerTaskUpdateExtractionIndefResponse reply = service.createServerTaskUpdateExtractionIndef(selectedExtraction.getIdDB());
				if(reply.isError()) {
					ErrorDialog.showErrorCreateServerTask(reply.getMessage());
				}else {
					ErrorDialog.showSuccessCreateServerTask(reply.getId());
				}
				break;
			default:
				break;
			}
			return;
		}
	}
	@FXML
	public void onManageTasks() {
		if(!this.getMainApplication().checkServer()){
			ErrorDialog.showErrorConfigureServer();
			return;
		}
		this.getMainApplication().showManageServerTasks();
		return;
	}

	@FXML
	public void onManageServerPreferences() {
		showManageServerPreferences();
		return;
	}

}
