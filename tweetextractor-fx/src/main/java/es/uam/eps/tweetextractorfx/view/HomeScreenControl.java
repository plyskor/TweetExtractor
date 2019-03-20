/**
 * 
 */
package es.uam.eps.tweetextractorfx.view;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTimelineVolumeReportResponse;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskUpdateExtractionIndefResponse;
import es.uam.eps.tweetextractor.service.CreateServerTaskTimelineVolumeReport;
import es.uam.eps.tweetextractor.service.CreateServerTaskUpdateExtractionIndef;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import es.uam.eps.tweetextractorfx.view.dialog.ServerPreferencesDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.credentials.AddCredentialsDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateAnalyticsServerTaskSelectTypeDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateExtractionServerTaskSelectExtractionDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateExtractionServerTaskSelectTaskTypeDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateServerTaskSelectTaskTypeDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateTimelineReportServerTaskSelectTypeDialogControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

	/**
	 * 
	 */
	public HomeScreenControl() {
		super();
	}

	@FXML
	private void initialize() {
		logoView.setImage(new Image("icon.png"));
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

		}
		this.getMainApplication().showQueryConstructor();
	}

	@FXML
	public void handleAddCredentials() {
		showAddCredentials();
	}

	@FXML
	public void handleManageCredentials() {
		this.getMainApplication().showManageCredentials();
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
			AnchorPane page = loader.load();

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

		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
		}
	}

	public void showManageServerPreferences() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class.getResource("dialog/ServerPreferencesDialog.fxml"));
			AnchorPane page = loader.load();

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

		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());

		}
	}

	public String showCreateServerTaskSelectTypeDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					HomeScreenControl.class.getResource("server/dialog/CreateServerTaskSelectTaskTypeDialog.fxml"));
			AnchorPane page = loader.load();
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
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
			return null;
		}
	}

	public Extraction showCreateExtractionServerTaskSelectExtractionDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class
					.getResource("server/dialog/CreateExtractionServerTaskSelectExtractionDialog.fxml"));
			AnchorPane page = loader.load();
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
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
			return null;
		}
	}

	public String showCreateExtractionServerTaskSelectTypeDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class
					.getResource("server/dialog/CreateExtractionServerTaskSelectTaskTypeDialog.fxml"));
			AnchorPane page = loader.load();
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
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
			return null;
		}
	}

	@FXML
	public void onCreateTask() {
		if (!this.getMainApplication().checkServer()) {
			ErrorDialog.showErrorConfigureServer();
			return;
		}
		String selectedTaskType;
		selectedTaskType = showCreateServerTaskSelectTypeDialog();
		if (selectedTaskType != null) {
			switch (selectedTaskType) {
			case (Constants.EXTRACTION_SERVER_TASK_TYPE):
				if (this.getMainApplication().getCurrentUser().getExtractionList().isEmpty()) {
					ErrorDialog.showErrorUserHasNoExtraction();
				} else {
					onCreateExtractionTask();
				}
				break;
			case (Constants.ANALYTICS_SERVER_TASK_TYPE):
				if (this.getMainApplication().getCurrentUser().getExtractionList().isEmpty()) {
					ErrorDialog.showErrorUserHasNoExtraction();
				} else {
					onCreateAnalyticsTask();
				}
				break;
			default:
				break;
			}
		}
	}

	private void onCreateAnalyticsTask() {
		String selectedType = showCreateAnalyticsTaskSelectTypeDialog();
		if (selectedType == null) {
			onCreateTask();
		} else {
			switch (selectedType) {
			case (Constants.TIMELINE_REPORT_SERVER_TASK_TYPE):
				String selectedTimelineReport = "";
			    selectedTimelineReport = showCreateTimelineReportSelectTypeDialog();
				switch (selectedTimelineReport) {
				case (Constants.TWEET_VOLUME_TIMELINE_REPORT):
					CreateServerTaskTimelineVolumeReport service = new CreateServerTaskTimelineVolumeReport(
							TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
					CreateServerTaskTimelineVolumeReportResponse reply = service
							.createServerTaskTimelineVolumeReport(this.getMainApplication().getCurrentUser().getIdDB());
					if (reply.isError()) {
						ErrorDialog.showErrorCreateServerTask(reply.getMessage());
					} else {
						ErrorDialog.showSuccessCreateServerTask(reply.getId());
					}
					break;
				case (Constants.OTHER_TIMELINE_REPORT):
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}

		}
	}

	private String showCreateTimelineReportSelectTypeDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class
					.getResource("server/dialog/CreateTimelineReportServerTaskSelectTypeDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			CreateTimelineReportServerTaskSelectTypeDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getToReturn();
		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
			return "";
		}
	}

	private String showCreateAnalyticsTaskSelectTypeDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class
					.getResource("server/dialog/CreateAnalyticsServerTaskSelectTypeDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			CreateAnalyticsServerTaskSelectTypeDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getToReturn();
		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
			return null;
		}
	}

	public void onCreateExtractionTask() {
		Extraction selectedExtraction;
		selectedExtraction = showCreateExtractionServerTaskSelectExtractionDialog();
		if (selectedExtraction == null) {
			onCreateTask();
		} else {
			String selectedTaskType = showCreateExtractionServerTaskSelectTypeDialog();
			switch (selectedTaskType) {
			case (Constants.UPDATE_EXTRACTION_INDEF_SERVER_TASK_TYPE):
				CreateServerTaskUpdateExtractionIndef service = new CreateServerTaskUpdateExtractionIndef(
						TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
				CreateServerTaskUpdateExtractionIndefResponse reply = service
						.createServerTaskUpdateExtractionIndef(selectedExtraction.getIdDB());
				if (reply.isError()) {
					ErrorDialog.showErrorCreateServerTask(reply.getMessage());
				} else {
					ErrorDialog.showSuccessCreateServerTask(reply.getId());
				}
				break;
			default:
				break;
			}

		}
	}

	@FXML
	public void onManageTasks() {
		if (!this.getMainApplication().checkServer()) {
			ErrorDialog.showErrorConfigureServer();
			return;
		}
		this.getMainApplication().showManageServerTasks();

	}

	@FXML
	public void onManageServerPreferences() {
		showManageServerPreferences();

	}
	/*Analytics menu*/
	@FXML
	public void onMyReports() {
		this.mainApplication.showMyReports();

	}
	@FXML
	public void onGraphicsForAnalytics() {
		//TODO à faire
	}

}
