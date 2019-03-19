/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.servertask.ScheduledServerTask;
import es.uam.eps.tweetextractor.model.servertask.ServerTaskInfo;
import es.uam.eps.tweetextractor.model.service.DeleteServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.GetUserServerTasksResponse;
import es.uam.eps.tweetextractor.model.service.InterruptServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.LaunchServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.ScheduleServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.SetServerTaskReadyResponse;
import es.uam.eps.tweetextractor.service.DeleteServerTask;
import es.uam.eps.tweetextractor.service.GetUserServerTasks;
import es.uam.eps.tweetextractor.service.InterruptServerTask;
import es.uam.eps.tweetextractor.service.LaunchServerTask;
import es.uam.eps.tweetextractor.service.ScheduleServerTask;
import es.uam.eps.tweetextractor.service.SetServerTaskReady;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import es.uam.eps.tweetextractorfx.view.HomeScreenControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateAnalyticsServerTaskSelectTypeDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.ScheduleServerTaskDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.ScheduleServerTaskSelectDateDialogControl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public class ManageServerTasksControl {
	/* Reference to the MainApplication */
	private MainApplication mainApplication;
	@FXML
	private TableView<ServerTaskInfo> serverTaskTable;
	@FXML
	private TableColumn<ServerTaskInfo, String> serverTaskID;
	@FXML
	private TableColumn<ServerTaskInfo, String> serverTaskType;
	@FXML
	private TableColumn<ServerTaskInfo, String> serverTaskExtraction;
	@FXML
	private TableColumn<ServerTaskInfo, String> serverTaskStatus;
	private ObservableList<ServerTaskInfo> serverTasksList = FXCollections.observableArrayList();
	private ServerTaskInfo selectedServerTask;

	/**
	 * 
	 */
	public ManageServerTasksControl() {
		super();
	}

	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		serverTaskID.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().getId()));
		serverTaskExtraction.setCellValueFactory(
				cellData -> new SimpleStringProperty("" + cellData.getValue().getExtractionSummary()));
		serverTaskStatus.setCellValueFactory(cellData -> new SimpleStringProperty(
				"" + Constants.TASK_STATUS_MAP.get(cellData.getValue().getStatus())));
		serverTaskType
				.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().getTaskType()));

		// Listen for selection changes and show the person details when changed.
		serverTaskTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedServerTask(newValue));

		selectedServerTask = null;
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
		serverTaskTable.setItems(serverTasksList);
		refreshTable();
	}

	/**
	 * @return the serverTaskTable
	 */
	public TableView<ServerTaskInfo> getServerTaskTable() {
		return serverTaskTable;
	}

	/**
	 * @param serverTaskTable the serverTaskTable to set
	 */
	public void setServerTaskTable(TableView<ServerTaskInfo> serverTaskTable) {
		this.serverTaskTable = serverTaskTable;
	}

	/**
	 * @return the serverTaskID
	 */
	public TableColumn<ServerTaskInfo, String> getServerTaskID() {
		return serverTaskID;
	}

	/**
	 * @param serverTaskID the serverTaskID to set
	 */
	public void setServerTaskID(TableColumn<ServerTaskInfo, String> serverTaskID) {
		this.serverTaskID = serverTaskID;
	}

	/**
	 * @return the serverTaskType
	 */
	public TableColumn<ServerTaskInfo, String> getServerTaskType() {
		return serverTaskType;
	}

	/**
	 * @param serverTaskType the serverTaskType to set
	 */
	public void setServerTaskType(TableColumn<ServerTaskInfo, String> serverTaskType) {
		this.serverTaskType = serverTaskType;
	}

	/**
	 * @return the serverTaskExtraction
	 */
	public TableColumn<ServerTaskInfo, String> getServerTaskExtraction() {
		return serverTaskExtraction;
	}

	/**
	 * @param serverTaskExtraction the serverTaskExtraction to set
	 */
	public void setServerTaskExtraction(TableColumn<ServerTaskInfo, String> serverTaskExtraction) {
		this.serverTaskExtraction = serverTaskExtraction;
	}

	/**
	 * @return the serverTaskStatus
	 */
	public TableColumn<ServerTaskInfo, String> getServerTaskStatus() {
		return serverTaskStatus;
	}

	/**
	 * @param serverTaskStatus the serverTaskStatus to set
	 */
	public void setServerTaskStatus(TableColumn<ServerTaskInfo, String> serverTaskStatus) {
		this.serverTaskStatus = serverTaskStatus;
	}

	/**
	 * @return the serverTasksList
	 */
	public ObservableList<ServerTaskInfo> getServerTasksList() {
		return serverTasksList;
	}

	/**
	 * @param serverTasksList the serverTasksList to set
	 */
	public void setServerTasksList(ObservableList<ServerTaskInfo> serverTasksList) {
		this.serverTasksList = serverTasksList;
	}

	/**
	 * @return the selectedServerTask
	 */
	public ServerTaskInfo getSelectedServerTask() {
		return selectedServerTask;
	}

	/**
	 * @param selectedServerTask the selectedServerTask to set
	 */
	public void setSelectedServerTask(ServerTaskInfo selectedServerTask) {
		this.selectedServerTask = selectedServerTask;
	}

	public void refreshTable() {
		GetUserServerTasks service = new GetUserServerTasks(
				TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		GetUserServerTasksResponse reply = service
				.getUserServerTasks(this.getMainApplication().getCurrentUser().getIdDB());
		if (reply.isError()) {
			ErrorDialog.showErrorRefreshServerTasksList(reply.getMessage());
			return;
		}
		serverTasksList.clear();
		serverTasksList.addAll(reply.getServerTasksList());
	}

	@FXML
	public void onRun() {
		if (selectedServerTask == null) {
			ErrorDialog.showErrorNoSelectedServerTask();
			return;
		}
		LaunchServerTask service = new LaunchServerTask(
				TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		LaunchServerTaskResponse reply = service.launchServerTask(selectedServerTask.getId());
		if (!reply.isError()) {
			ErrorDialog.showTaskHasStarted(selectedServerTask.getId());
			onRefresh();
		} else {
			ErrorDialog.showErrorPerformServerAction(reply.getMessage());
		}
	}

	@FXML
	public void onInterrupt() {
		if (selectedServerTask == null) {
			ErrorDialog.showErrorNoSelectedServerTask();
			return;
		}
		InterruptServerTask service = new InterruptServerTask(
				TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		InterruptServerTaskResponse reply = service.interruptServerTask(selectedServerTask.getId());
		if (!reply.isError()) {
			onRefresh();
		} else {
			ErrorDialog.showErrorPerformServerAction(reply.getMessage());
		}
	}

	@FXML
	public void onSetReady() {
		if (selectedServerTask == null) {
			ErrorDialog.showErrorNoSelectedServerTask();
			return;
		}
		SetServerTaskReady service = new SetServerTaskReady(
				TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		SetServerTaskReadyResponse reply = service.setServerTaskReady(selectedServerTask.getId());
		if (!reply.isError()) {
			onRefresh();
		} else {
			ErrorDialog.showErrorPerformServerAction(reply.getMessage());
		}
	}

	@FXML
	public void onDeleteTask() {
		if (selectedServerTask == null) {
			ErrorDialog.showErrorNoSelectedServerTask();
			return;
		}
		DeleteServerTask service = new DeleteServerTask(
				TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		DeleteServerTaskResponse reply = service.deleteServerTask(selectedServerTask.getId());
		if (!reply.isError()) {
			onRefresh();
		} else {
			ErrorDialog.showErrorPerformServerAction(reply.getMessage());
		}
	}

	@FXML
	public void onRefresh() {
		refreshTable();
	}

	@FXML
	public void onScheduleTask() {
		if (selectedServerTask == null) {
			ErrorDialog.showErrorNoSelectedServerTask();
			return;
		}
		int choice = showScheduleServerTaskDialog();
		switch (choice) {
		case Constants.SCHEDULE_GIVEN_DATE_TIME:
			Date date = showScheduleServerTaskSelectDateDialog();
			if(date==null) {
				return;
			}
			ScheduleServerTask service = new ScheduleServerTask(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
			ScheduleServerTaskResponse reply = service.scheduleServerTask(selectedServerTask.getId(), date);
			if(reply.isError()) {
				ErrorDialog.showErrorScheduleServerTask(reply.getMessage());
				return;
			}
			ErrorDialog.showSuccessScheduleServerTask();
			break;
		case Constants.SCHEDULE_DELAY:
			break;
		case Constants.SCHEDULE_PERIODICALLY:
			break;
		default:
			break;
		}
	}

	private int showScheduleServerTaskDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ManageServerTasksControl.class.getResource("dialog/ScheduleServerTaskDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			ScheduleServerTaskDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getToReturn();
		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
			return -1;
		}
	}
	private Date showScheduleServerTaskSelectDateDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ManageServerTasksControl.class.getResource("dialog/ScheduleServerTaskSelectDateDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			ScheduleServerTaskSelectDateDialogControl controller = loader.getController();
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
}
