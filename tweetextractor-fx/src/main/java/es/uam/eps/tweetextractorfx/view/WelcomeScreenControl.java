package es.uam.eps.tweetextractorfx.view;


import java.io.IOException;

import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.dialog.auth.LoginDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.auth.NewUserDialogControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WelcomeScreenControl {
	/*Reference to the MainApplication*/
    private MainApplication mainApplication;
    @FXML
    private ImageView logoView;
	/**
	 * @return the mainApplication
	 */
	public MainApplication getMainApplication() {
		return mainApplication;
	}
	@FXML
	private void initialize() {
        Image logo = new Image("icon.png");
        if(logo!=null)logoView.setImage(logo);
	}
	/**
	 * @param mainApplication the mainApplication to set
	 */
	public void setMainApplication(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
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
	public void handleLogIn() {
		showLogInDialog();
	}
	@FXML
	public void handleNewAccount() {
		showNewAccountDialog();
	}
	@FXML
	public void handleExit() {
		System.exit(0);
	}
	public void showLogInDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(WelcomeScreenControl.class.getResource("dialog/auth/LoginDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			LoginDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setWelcomeScreenControl(this);
			dialogStage.showAndWait();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	public void showNewAccountDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(WelcomeScreenControl.class.getResource("dialog/auth/NewUserDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			NewUserDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(getMainApplication());
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
