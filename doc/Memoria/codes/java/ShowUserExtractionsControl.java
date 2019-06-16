package es.uam.eps.tweetextractorfx.view.extraction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.xml.ws.WebServiceException;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.filter.Filter;
import es.uam.eps.tweetextractor.model.service.GetUserServerTasksResponse;
import es.uam.eps.tweetextractor.service.DeleteServerTask;
import es.uam.eps.tweetextractor.service.GetUserServerTasks;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import es.uam.eps.tweetextractorfx.util.XMLManager;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTaskInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class ShowUserExtractionsControl extends TweetExtractorFXController {
	@FXML
	private TreeTableView<String> extractionTableView;
	@FXML
	private TreeTableColumn<String, String> extractionColumn;
	private Extraction selectedExtraction;

	public ShowUserExtractionsControl() {
		super();
	}

	@FXML
	public void initialize() {
		extractionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
		extractionTableView.getSelectionModel().selectedItemProperty().addListener((c, oldValue, newValue) -> {
			if ((newValue != null && newValue.getValue() != null && !newValue.getValue().startsWith("Extraction"))
					|| newValue == null || newValue.getValue() == null
					|| newValue.getValue().contains("of the account")) {
				Platform.runLater(() -> extractionTableView.getSelectionModel().clearSelection());
				selectedExtraction = null;
			} else {
				if (newValue != null) {
					selectedExtraction = this.getMainApplication().getCurrentUser()
							.getExtraction(Integer.parseInt(newValue.getValue().substring(11)));
				}
			}
		});
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		this.updateTreeTableView();

	}

	@SuppressWarnings("unchecked")
	private void updateTreeTableView() {
		TreeItem<String> root = new TreeItem<>(
				"Extractions of the account " + this.getMainApplication().getCurrentUser().getNickname());
		extractionTableView.setRoot(root);
		for (Extraction extraction : this.getMainApplication().getCurrentUser().getExtractionList()) {
			if (extraction != null) {
				TreeItem<String> extractionNode = new TreeItem<>("Extraction " + extraction.getIdDB());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TreeItem<String> createdAt = new TreeItem<>("Created On: " + df.format(extraction.getCreationDate()));
				TreeItem<String> lastModified = new TreeItem<>(
						"Last updated: " + df.format(extraction.getLastModificationDate()));
				TreeItem<String> filtersNode = new TreeItem<>("Filters");
				for (Filter filter : extraction.getFilterList()) {
					TreeItem<String> filterItem = new TreeItem<>(filter.getSummary());
					filtersNode.getChildren().add(filterItem);
				}
				extractionNode.getChildren().addAll(createdAt, lastModified, filtersNode);
				root.getChildren().add(extractionNode);
			}
		}
	}

	/**
	 * @return the extractionTableView
	 */
	public TreeTableView<String> getExtractionTableView() {
		return extractionTableView;
	}

	/**
	 * @param extractionTableView the extractionTableView to set
	 */
	public void setExtractionTableView(TreeTableView<String> extractionTableView) {
		this.extractionTableView = extractionTableView;
	}

	/**
	 * @return the extractionColumn
	 */
	public TreeTableColumn<String, String> getExtractionColumn() {
		return extractionColumn;
	}

	/**
	 * @param extractionColumn the extractionColumn to set
	 */
	public void setExtractionColumn(TreeTableColumn<String, String> extractionColumn) {
		this.extractionColumn = extractionColumn;
	}

	/**
	 * @return the selectedExtraction
	 */
	public Extraction getSelectedExtraction() {
		return selectedExtraction;
	}

	/**
	 * @param selectedExtraction the selectedExtraction to set
	 */
	public void setSelectedExtraction(Extraction selectedExtraction) {
		this.selectedExtraction = selectedExtraction;
	}

	@FXML
	public void handleBack() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/HomeScreen.fxml");
	}

	@FXML
	public void handleAddExtraction() {
		if (!this.getMainApplication().getCurrentUser().hasAnyCredentials()) {
			ErrorDialog.showErrorNoCredentials();
			return;
		}
		this.getMainApplication().showScreenInCenterOfRootLayout("view/extraction/QueryConstructor.fxml");
	}

	@FXML
	public void handleEditExtraction() {
		if (selectedExtraction == null) {
			ErrorDialog.showErrorNoSelectedExtraction();
			return;
		}
		this.getMainApplication().showExtractionDetails(selectedExtraction, false);
	}

	@FXML
	public void handleRemoveExtraction() {
		if (selectedExtraction == null) {
			ErrorDialog.showErrorNoSelectedExtraction();
		} else {
			deleteExtraction();
		}
	}

	public void deleteExtraction() {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"This action will delete the extraction " + selectedExtraction.getIdDB()
						+ ", and also every tweet or server task related to it. Are you sure you want to continue?",
				ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			this.getMainApplication().getCurrentUser().removeExtractionFromList(selectedExtraction);
			XMLManager.deleteExtraction(selectedExtraction);
			ExtractionServiceInterface extractionService = mainApplication.getSpringContext()
					.getBean(ExtractionServiceInterface.class);
			extractionService.deleteById(selectedExtraction.getIdDB());
			try {
				GetUserServerTasks getTasksService = new GetUserServerTasks(
						TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
				GetUserServerTasksResponse reply = getTasksService
						.getUserServerTasks(mainApplication.getCurrentUser().getIdDB());
				if (!reply.isError()) {
					for (ServerTaskInfo task : reply.getServerTasksList()) {
						if (selectedExtraction.getIdDB() == task.getExtractionId()) {
							DeleteServerTask deleteTaskService = new DeleteServerTask(TweetExtractorFXPreferences
									.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
							deleteTaskService.deleteServerTask(task.getId());
						}
					}
				}
			} catch (WebServiceException exception) {
				exception.printStackTrace();
			}
			selectedExtraction = null;
			this.updateTreeTableView();
		}
	}
}
