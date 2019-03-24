package es.uam.eps.tweetextractorfx;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.filter.*;
import es.uam.eps.tweetextractor.model.filter.impl.*;
import es.uam.eps.tweetextractor.service.GetServerStatus;
import es.uam.eps.tweetextractor.spring.config.TweetExtractorSpringConfig;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import es.uam.eps.tweetextractorfx.view.RootLayoutControl;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorfx.view.dialog.LoadingDialogControl;
import es.uam.eps.tweetextractorfx.view.extraction.ExtractionDetailsControl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApplication extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	/* Available filters for Queries */
	private ObservableList<Filter> availableFilters = FXCollections.observableArrayList();
	private List<User> userList = new ArrayList<>();
	private User currentUser = null;
	private RootLayoutControl rootLayoutController;
	private AnnotationConfigApplicationContext springContext;
	private Logger logger = LoggerFactory.getLogger(MainApplication.class);

	public MainApplication() {
		initAvailableFilters();
	}

	@Override
	public void start(Stage primaryStage) {
		logger.info("Starting TweetExtractorFX...");
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("TweetExtractorFX");
		initRootLayout();
		TweetExtractorFXPreferences.initializePreferences();
		springContext = new AnnotationConfigApplicationContext(TweetExtractorSpringConfig.class);
	}

	/* Initialize the RootLayout */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			// Give the controller access to the main app.
			rootLayoutController = loader.getController();
			rootLayoutController.setMainApplication(this);
    		this.showScreenInCenterOfRootLayout("view/WelcomeScreen.fxml");			
    		primaryStage.show();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void showExtractionDetails(Extraction extraction, boolean executeQuery) {
		try {
			// Load query constructor
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/extraction/ExtractionDetails.fxml"));
			AnchorPane queryDetails = loader.load();
			// Give the controller access to the main app.
			ExtractionDetailsControl controller = loader.getController();
			controller.setExtraction(extraction);
			controller.setMainApplication(this);
			if (executeQuery) {
				controller.executeQuery();
				controller.getTweetObservableList().addAll(controller.getExtraction().getTweetList());
			} else {
				controller.refreshTweetObservableList();
			}
			// Set query constructor into the center of root layout.
			rootLayout.setCenter(queryDetails);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public Stage showLoadingDialog(String title) {
		try {

			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/LoadingDialog.fxml"));
			AnchorPane page = loader.load();
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
			return dialogStage;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public void showScreenInCenterOfRootLayout(String fxmlPath) {
		try {	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource(fxmlPath));
			Node rootNode = loader.load();
			// Set query constructor into the center of root layout.
			rootLayout.setCenter(rootNode);
			// Give the controller access to the main app.
			TweetExtractorFXController controller = loader.getController();
			controller.setMainApplication(this);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	public void showReportRawDataInCenterOfRootLayout(String fxmlPath,Class<?> clazz,AnalyticsReport report) {
		try {	
			//Loader
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource(fxmlPath));
			Node rootNode = loader.load();
			rootLayout.setCenter(rootNode);
			//Construct a controller from the provided class
			Class<?>[] params = new Class<?>[2];
			params[0]=Scene.class;
			params[1]=AnalyticsReport.class;
	        Object controller = clazz.getConstructor(params).newInstance(rootNode.getScene(),report);
	        loader.setController(controller);
	        //Run a method from the generic controller
	        Class<MainApplication> parameterType = MainApplication.class;
	        Method meth = clazz.getMethod("setMainApplication", parameterType);
	        meth.invoke(controller, this);
		} catch (Exception e) {
			logger.error(e.getMessage());
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

	

	public boolean existsUser(String nickName) {
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

	public boolean checkServer() {
		GetServerStatus service = new GetServerStatus(
				TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		return service.getServerStatus();
	}

	public AnnotationConfigApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(AnnotationConfigApplicationContext springContext) {
		this.springContext = springContext;
	}

}
