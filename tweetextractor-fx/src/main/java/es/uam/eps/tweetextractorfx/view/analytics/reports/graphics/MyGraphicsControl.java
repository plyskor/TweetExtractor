/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportImageServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.task.DeleteChartTask;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author jose
 *
 */
public class MyGraphicsControl extends TweetExtractorFXController {
	@FXML
	private TableView<AnalyticsReportImage> graphicsTable;
	@FXML
	private TableColumn<AnalyticsReportImage, Integer> graphicIDColumn;
	@FXML
	private TableColumn<AnalyticsReportImage, String> graphicTypeColumn;
	@FXML
	private TableColumn<AnalyticsReportImage, Integer> reportIDColumn;
	@FXML
	private TableColumn<AnalyticsReportImage, String> reportTypeColumn;
	@FXML
	private TableColumn<AnalyticsReportImage, LocalDate> generatedOnColumn;
	@FXML
	private AnalyticsReportImage selectedGraphic=null;
	private ObservableList<AnalyticsReportImage> graphicsList= FXCollections.observableArrayList();
	private AnalyticsReportImageServiceInterface ariServ;
	private Stage loadingDialog = null;


	/**
	 * 
	 */
	public MyGraphicsControl() {
		super();
	}
	@FXML
	private void initialize() {
		graphicIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		reportIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getReport().getId()).asObject());
		generatedOnColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
		graphicsTable.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> setSelectedGraphic(newValue));
	}
	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override	
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		graphicsTable.setItems(graphicsList);
		ariServ = this.mainApplication.getSpringContext().getBean(AnalyticsReportImageServiceInterface.class);
		refreshGraphicsList();
	}
	private void refreshGraphicsList() {
		if(ariServ!=null) {
			List<AnalyticsReportImage> result = ariServ.findByUser(this.getMainApplication().getCurrentUser());
			graphicsList.clear();
			graphicsList.addAll(result);
		}
	}
	public void showGraphicChartDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
			MyGraphicsControl.class.getResource("ShowGraphicChartDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			ShowGraphicChartDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setGraphic(selectedGraphic);
			controller.setMainApplication(mainApplication);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error(e.getMessage());
		}
	}
	@FXML
	public void onShowGraphicChart() {
		if(selectedGraphic!=null) {
			showGraphicChartDialog();
		}else {
			ErrorDialog.showErrorNoSelectedGraphics();
		}
	}
	@FXML
	public void onDeleteChart() {
		if(selectedGraphic!=null) {
			deleteGraphic();
		}else {
			ErrorDialog.showErrorNoSelectedGraphics();
		}
	}
	@FXML
	public void onCreateChart() {
		createChart();
	}
	private void createChart() {
		this.getMainApplication().showCreateChart();
	}
	public void deleteGraphic() {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"This action will delete the graphic " + selectedGraphic.getId()
						+ ". Are you sure you want to continue?",
				ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			selectedGraphic.getReport().getGraphics().remove(selectedGraphic);
			DeleteChartTask deleteTask = new DeleteChartTask(this.getMainApplication().getSpringContext(),selectedGraphic);
			deleteTask.setOnSucceeded(e -> {
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
			loadingDialog = mainApplication.showLoadingDialog("Deleting graphic...");
			loadingDialog.showAndWait();			
			selectedGraphic = null;
			this.refreshGraphicsList();
		}
	}
	/**
	 * @return the graphicsTable
	 */
	public TableView<AnalyticsReportImage> getGraphicsTable() {
		return graphicsTable;
	}
	/**
	 * @param graphicsTable the graphicsTable to set
	 */
	public void setGraphicsTable(TableView<AnalyticsReportImage> graphicsTable) {
		this.graphicsTable = graphicsTable;
	}
	/**
	 * @return the graphicIDColumn
	 */
	public TableColumn<AnalyticsReportImage, Integer> getGraphicIDColumn() {
		return graphicIDColumn;
	}
	/**
	 * @param graphicIDColumn the graphicIDColumn to set
	 */
	public void setGraphicIDColumn(TableColumn<AnalyticsReportImage, Integer> graphicIDColumn) {
		this.graphicIDColumn = graphicIDColumn;
	}
	/**
	 * @return the graphicTypeColumn
	 */
	public TableColumn<AnalyticsReportImage, String> getGraphicTypeColumn() {
		return graphicTypeColumn;
	}
	/**
	 * @param graphicTypeColumn the graphicTypeColumn to set
	 */
	public void setGraphicTypeColumn(TableColumn<AnalyticsReportImage, String> graphicTypeColumn) {
		this.graphicTypeColumn = graphicTypeColumn;
	}
	/**
	 * @return the reportIDColumn
	 */
	public TableColumn<AnalyticsReportImage, Integer> getReportIDColumn() {
		return reportIDColumn;
	}
	/**
	 * @param reportIDColumn the reportIDColumn to set
	 */
	public void setReportIDColumn(TableColumn<AnalyticsReportImage, Integer> reportIDColumn) {
		this.reportIDColumn = reportIDColumn;
	}
	/**
	 * @return the reportTypeColumn
	 */
	public TableColumn<AnalyticsReportImage, String> getReportTypeColumn() {
		return reportTypeColumn;
	}
	/**
	 * @param reportTypeColumn the reportTypeColumn to set
	 */
	public void setReportTypeColumn(TableColumn<AnalyticsReportImage, String> reportTypeColumn) {
		this.reportTypeColumn = reportTypeColumn;
	}
	/**
	 * @return the generatedOnColumn
	 */
	public TableColumn<AnalyticsReportImage, LocalDate> getGeneratedOnColumn() {
		return generatedOnColumn;
	}
	/**
	 * @param generatedOnColumn the generatedOnColumn to set
	 */
	public void setGeneratedOnColumn(TableColumn<AnalyticsReportImage, LocalDate> generatedOnColumn) {
		this.generatedOnColumn = generatedOnColumn;
	}
	/**
	 * @return the selectedGraphic
	 */
	public AnalyticsReportImage getSelectedGraphic() {
		return selectedGraphic;
	}
	/**
	 * @param selectedGraphic the selectedGraphic to set
	 */
	public void setSelectedGraphic(AnalyticsReportImage selectedGraphic) {
		this.selectedGraphic = selectedGraphic;
	}
	/**
	 * @return the graphicsList
	 */
	public ObservableList<AnalyticsReportImage> getGraphicsList() {
		return graphicsList;
	}
	/**
	 * @param graphicsList the graphicsList to set
	 */
	public void setGraphicsList(ObservableList<AnalyticsReportImage> graphicsList) {
		this.graphicsList = graphicsList;
	}
	/**
	 * @return the loadingDialog
	 */
	public Stage getLoadingDialog() {
		return loadingDialog;
	}
	/**
	 * @param loadingDialog the loadingDialog to set
	 */
	public void setLoadingDialog(Stage loadingDialog) {
		this.loadingDialog = loadingDialog;
	}
	
}
