/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server;


import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.servertask.ServerTaskInfo;
import es.uam.eps.tweetextractor.model.service.GetUserServerTasksResponse;
import es.uam.eps.tweetextractor.service.GetUserServerTasks;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
		serverTaskID.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getId()));
		serverTaskExtraction.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getExtractionSummary()));
		serverTaskStatus.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getStatus()));
		serverTaskType.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getTaskType()));
				
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
		GetUserServerTasks service = new GetUserServerTasks(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		GetUserServerTasksResponse reply = service.getUserServerTasks(this.getMainApplication().getCurrentUser().getIdDB());
		if(reply.isError()) {
			ErrorDialog.showErrorRefreshServerTasksList(reply.getMessage());
			return;
		}
		serverTasksList.clear();
		serverTasksList.addAll(reply.getServerTasksList());
	}
}