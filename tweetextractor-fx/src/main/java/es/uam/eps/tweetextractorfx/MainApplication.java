package es.uam.eps.tweetextractorfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.filter.*;
import es.uam.eps.tweetextractor.model.filter.impl.*;
import es.uam.eps.tweetextractorfx.view.HomeScreenControl;
import es.uam.eps.tweetextractorfx.view.RootLayoutControl;
import es.uam.eps.tweetextractorfx.view.WelcomeScreenControl;
import es.uam.eps.tweetextractorfx.view.credentials.ManageCredentialsControl;
import es.uam.eps.tweetextractorfx.view.dialog.LoadingDialogControl;
import es.uam.eps.tweetextractorfx.view.extraction.ExtractionDetailsControl;
import es.uam.eps.tweetextractorfx.view.extraction.QueryConstructorControl;
import es.uam.eps.tweetextractorfx.view.extraction.ShowUserExtractionsControl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import twitter4j.TwitterException;

public class MainApplication extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	/* Available filters for Queries */
	private ObservableList<Filter> availableFilters = FXCollections.observableArrayList();
	private List<User> userList = new ArrayList<User>();
	private User currentUser = null;
	private RootLayoutControl rootLayoutController;
	public MainApplication() {
		initAvailableFilters();
		/*
		 * Cargamos la lista de usuarios List<User> readList =XMLManager.loadUserList();
		 * if(readList!=null)userList.addAll(readList);
		 */
	}
	
		


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("tweetextractor");
		
		initRootLayout();

		// showPersonOverview();
	}

	/* Initialize the RootLayout */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			rootLayoutController = loader.getController();
			rootLayoutController.setMainApplication(this);
			showWelcomeScreen();
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/* Mostrando la pantalla de bienvenida */

	public void showWelcomeScreen() {
		try {
			// Load elcome screen.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/WelcomeScreen.fxml"));
			AnchorPane welcomeScreen = (AnchorPane) loader.load();

			// Set welcome screen into the center of root layout.
			rootLayout.setCenter(welcomeScreen);

			// Give the controller access to the main app.
			WelcomeScreenControl controller = loader.getController();
			controller.setMainApplication(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showQueryConstructor() {
		try {
			// Load query constructor
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/extraction/QueryConstructor.fxml"));

			AnchorPane queryConstructor = (AnchorPane) loader.load();
			// Set query constructor into the center of root layout.
			rootLayout.setCenter(queryConstructor);

			// Give the controller access to the main app.
			QueryConstructorControl controller = loader.getController();
			controller.setMainApplication(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showExtractionDetails(Extraction extraction,boolean executeQuery) {
		try {
			// Load query constructor
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/extraction/ExtractionDetails.fxml"));
			AnchorPane queryDetails = (AnchorPane) loader.load();
			// Give the controller access to the main app.
			ExtractionDetailsControl controller = loader.getController();
			controller.setExtraction(extraction);
			controller.setMainApplication(this);
			try {
				if(executeQuery) {
					controller.executeQuery();
					controller.getTweetObservableList().addAll(controller.getExtraction().getTweetList());
				}else {
					controller.refreshTweetObservableList();
				}
				// Set query constructor into the center of root layout.
				rootLayout.setCenter(queryDetails);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Stage showLoadingDialog(String title) {
		try {

			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/LoadingDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.initOwner(this.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.setTitle(title);
			// Set the dialogStage to the controller.
			LoadingDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			return	dialogStage;		
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void showHomeScreen() {
		try {
			// Load query constructor
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/HomeScreen.fxml"));
			AnchorPane homeScreen = (AnchorPane) loader.load();
			// Give the controller access to the main app.
			HomeScreenControl controller = loader.getController();
			controller.setMainApplication(this);
			// Set query constructor into the center of root layout.
			rootLayout.setCenter(homeScreen);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showManageCredentials() {
		try {
			// Load query constructor
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/credentials/ManageCredentials.fxml"));
			AnchorPane homeScreen = (AnchorPane) loader.load();
			// Give the controller access to the main app.
			ManageCredentialsControl controller = loader.getController();
			controller.setMainApplication(this);
			// Set query constructor into the center of root layout.
			rootLayout.setCenter(homeScreen);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showUserExtractions() {
		try {
			// Load query constructor
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/extraction/ShowUserExtractions.fxml"));
			AnchorPane homeScreen = (AnchorPane) loader.load();
			// Give the controller access to the main app.
			ShowUserExtractionsControl controller = loader.getController();
			controller.setMainApplication(this);
			// Set query constructor into the center of root layout.
			rootLayout.setCenter(homeScreen);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initAvailableFilters() {
		availableFilters.add(new FilterContains());
		availableFilters.add(new FilterContainsExact());
		availableFilters.add(new FilterMention());
		availableFilters.add(new FilterHashtag());
		availableFilters.add(new FilterFrom());
		availableFilters.add(new FilterTo());
		availableFilters.add(new FilterList());
		// availableFilters.add(new FilterAttitude());
		availableFilters.add(new FilterSince());
		availableFilters.add(new FilterUntil());
		availableFilters.add(new FilterUrl());
		// availableFilters.add(new FilterQuestion());
	}

	/**
	 * @return the availableFilters
	 */
	public ObservableList<Filter> getAvailableFilters() {
		return availableFilters;
	}

	/**
	 * @param availableFilters the availableFilters to set
	 */
	public void setAvailableFilters(ObservableList<Filter> availableFilters) {
		this.availableFilters = availableFilters;
	}

	/**
	 * @return the primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * @return the userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * @param primaryStage the primaryStage to set
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * @return the rootLayout
	 */
	public BorderPane getRootLayout() {
		return rootLayout;
	}

	/**
	 * @param rootLayout the rootLayout to set
	 */
	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void updateUserList() {
		/*List<User> readList = XMLManager.loadUserList();
		if (readList != null) {
			this.userList.clear();
			userList.addAll(readList);
		}*/
	}

	public boolean existsUser(String nickName) {
		updateUserList();
		for (User user : this.getUserList()) {
			if (user.getNickname().equals(nickName))
				return true;
		}
		return false;
	}

	public User getUser(String nickName) {
		if (nickName != null) {
			for (User user : this.getUserList()) {
				if (user.getNickname().equals(nickName))
					return user;
			}
		}
		return null;
	}

	/**
	 * @return the rootLayoutController
	 */
	public RootLayoutControl getRootLayoutController() {
		return rootLayoutController;
	}

	/**
	 * @param rootLayoutController the rootLayoutController to set
	 */
	public void setRootLayoutController(RootLayoutControl rootLayoutController) {
		this.rootLayoutController = rootLayoutController;
	}

	
	
}
