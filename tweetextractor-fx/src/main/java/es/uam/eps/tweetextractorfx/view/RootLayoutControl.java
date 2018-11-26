package es.uam.eps.tweetextractorfx.view;

import java.io.IOException;
import java.util.Properties;
import es.uam.eps.tweetextractorfx.MainApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RootLayoutControl {
	/*Reference to the MainApplication*/
    private MainApplication mainApplication;
    @FXML
    private Menu archivoMenu;
    
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
    	logoutmenuitem.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	        mainApplication.setCurrentUser(null);
    	        mainApplication.getRootLayoutController().getArchivoMenu().getItems().remove(mainApplication.getRootLayoutController().getLogoutmenuitem());
    	        mainApplication.showWelcomeScreen();
    	    }
    	});
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
		return archivoMenu;
	}

	/**
	 * @param archivoMenu the archivoMenu to set
	 */
	public void setArchivoMenu(Menu archivoMenu) {
		this.archivoMenu = archivoMenu;
	}

	/**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        String message= new String("Twitter data extractor with JavaFX\nAuthor: Jose Antonio García del Saz\nVersion: ");
		try {
			final Properties properties = new Properties();
			properties.load(this.getClass().getClassLoader().getResourceAsStream("tweetextractorfx.properties"));
			message=message.concat(properties.getProperty("tweetextractorfx.version"));
		} catch (IOException  e) {
			e.printStackTrace();
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
    	if(!archivoMenu.getItems().contains(logoutmenuitem)) {
    		archivoMenu.getItems().add(1, logoutmenuitem);
    	}
    }
    public void logOut() {
    	if(logoutmenuitem!=null) {
    		logoutmenuitem.fire();
    	}
    }
}
