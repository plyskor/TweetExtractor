package es.uam.eps.tweetextractorfx.view;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.task.DeleteAccountTask;
import es.uam.eps.tweetextractorfx.view.dialog.auth.ChangePasswordDialogControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RootLayoutControl {
	/*Reference to the MainApplication*/
    private MainApplication mainApplication;
    @FXML
    private Menu tweetExtractorMenu;
    @FXML
    private Menu optionsMenu;
    
    private Stage loadingDialog = null;

    private MenuItem logoutmenuitem ;
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
		logoutmenuitem = new MenuItem("Log out");
    	logoutmenuitem.setOnAction(event -> {
    	        mainApplication.setCurrentUser(null);
    	        mainApplication.getRootLayoutController().getArchivoMenu().getItems().remove(mainApplication.getRootLayoutController().getLogoutmenuitem());
    	        mainApplication.showWelcomeScreen();
    	});
	}
	@FXML 
	public void initialize() {
		removeOptionsMenu();
	}
	/**
	 * @return the logoutmenuitem
	 */
	public MenuItem getLogoutmenuitem() {
		return logoutmenuitem;
	}

	/**
	 * @param logoutmenuitem the logoutmenuitem to set
	 */
	public void setLogoutmenuitem(MenuItem logoutmenuitem) {
		this.logoutmenuitem = logoutmenuitem;
	}

	/**
	 * @return the archivoMenu
	 */
	public Menu getArchivoMenu() {
		return tweetExtractorMenu;
	}

	/**
	 * @param archivoMenu the archivoMenu to set
	 */
	public void setArchivoMenu(Menu archivoMenu) {
		this.tweetExtractorMenu = archivoMenu;
	}

	/**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        String message="Twitter data extractor with JavaFX\nAuthor: Jose Antonio García del Saz\nVersion: ";
		try {
			final Properties properties = new Properties();
			properties.load(this.getClass().getClassLoader().getResourceAsStream("tweetextractorfx.properties"));
			message=message.concat(properties.getProperty("tweetextractorfx.version"));
		} catch (IOException  e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
		}
        Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("TweetExtractorFX");
    	alert.setHeaderText("About...");
    	alert.setContentText(message);
    	alert.showAndWait();
    }

    /**
     * Gets you to the home/welcome screen
     */
    @FXML
    private void handleHome() {
    	if(this.getMainApplication().getCurrentUser()==null) {
        	this.getMainApplication().showWelcomeScreen();
    	}else {
    		this.getMainApplication().showHomeScreen();
    	}
    }
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    /**
     * Opens the birthday statistics.
     */
    public void addLogOut() {
    	if(!tweetExtractorMenu.getItems().contains(logoutmenuitem)) {
    		tweetExtractorMenu.getItems().add(1, logoutmenuitem);
    	}
    }
    public void logOut() {
    	if(logoutmenuitem!=null) {
    		logoutmenuitem.fire();
    	}
    }
	public void showUpdatePassword() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class.getResource("dialog/auth/ChangePasswordDialog.fxml"));
			AnchorPane page =loader.load();
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

			
		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
			
		}
	}
	@FXML
	public void handleChangePassword() {
		showUpdatePassword();
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
				this.logOut();
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
	public void addOptionsMenu() {
		optionsMenu.setVisible(true);
	}
	public void removeOptionsMenu() {
		optionsMenu.setVisible(false);
	}
}
