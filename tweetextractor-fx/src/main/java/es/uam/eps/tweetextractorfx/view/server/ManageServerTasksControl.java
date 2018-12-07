/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server;


import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractorfx.MainApplication;
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
	private TableView<ServerTask> serverTaskTable;
	@FXML
	private TableColumn<ServerTask, String> serverTaskID;
	@FXML
	private TableColumn<ServerTask, String> serverTaskType;
	@FXML
	private TableColumn<ServerTask, String> serverTaskExtraction;
	@FXML
	private TableColumn<ServerTask, String> serverTaskStatus;
	private ObservableList<ServerTask> serverTasksList = FXCollections.observableArrayList();
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
		serverTaskExtraction.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue()));
/*
		// Listen for selection changes and show the person details when changed.
		availableFiltersTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedAvailableFilter(newValue));

		selectedAvailableFilter = null;*/
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
	}
	/**
	 * @return the serverTaskTable
	 */
	public TableView<ServerTask> getServerTaskTable() {
		return serverTaskTable;
	}
	/**
	 * @param serverTaskTable the serverTaskTable to set
	 */
	public void setServerTaskTable(TableView<ServerTask> serverTaskTable) {
		this.serverTaskTable = serverTaskTable;
	}
	/**
	 * @return the serverTaskID
	 */
	public TableColumn<ServerTask, String> getServerTaskID() {
		return serverTaskID;
	}
	/**
	 * @param serverTaskID the serverTaskID to set
	 */
	public void setServerTaskID(TableColumn<ServerTask, String> serverTaskID) {
		this.serverTaskID = serverTaskID;
	}
	/**
	 * @return the serverTaskType
	 */
	public TableColumn<ServerTask, String> getServerTaskType() {
		return serverTaskType;
	}
	/**
	 * @param serverTaskType the serverTaskType to set
	 */
	public void setServerTaskType(TableColumn<ServerTask, String> serverTaskType) {
		this.serverTaskType = serverTaskType;
	}
	/**
	 * @return the serverTaskExtraction
	 */
	public TableColumn<ServerTask, String> getServerTaskExtraction() {
		return serverTaskExtraction;
	}
	/**
	 * @param serverTaskExtraction the serverTaskExtraction to set
	 */
	public void setServerTaskExtraction(TableColumn<ServerTask, String> serverTaskExtraction) {
		this.serverTaskExtraction = serverTaskExtraction;
	}
	/**
	 * @return the serverTaskStatus
	 */
	public TableColumn<ServerTask, String> getServerTaskStatus() {
		return serverTaskStatus;
	}
	/**
	 * @param serverTaskStatus the serverTaskStatus to set
	 */
	public void setServerTaskStatus(TableColumn<ServerTask, String> serverTaskStatus) {
		this.serverTaskStatus = serverTaskStatus;
	}
	/**
	 * @return the serverTasksList
	 */
	public ObservableList<ServerTask> getServerTasksList() {
		return serverTasksList;
	}
	/**
	 * @param serverTasksList the serverTasksList to set
	 */
	public void setServerTasksList(ObservableList<ServerTask> serverTasksList) {
		this.serverTasksList = serverTasksList;
	}
	
}
