/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import es.uam.eps.tweetextractor.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author jose
 *
 */
public class MyReportsControl extends TweetExtractorFXController {
	@FXML
	private TableView<AnalyticsReport> reportsTable;
	@FXML
	private TableColumn<AnalyticsReport, Integer> idColumn;
	@FXML
	private TableColumn<AnalyticsReport, String> typeColumn;
	@FXML
	private TableColumn<AnalyticsReport, LocalDate> createdOnColumn;
	@FXML
	private TableColumn<AnalyticsReport, LocalDate> lastUpdatedColumn;
	private AnalyticsReport selectedReport = null;
	private ObservableList<AnalyticsReport> reportList = FXCollections.observableArrayList();
	private AnalyticsReportServiceInterface arServ;
	/**
	 * 
	 */
	public MyReportsControl() {
		super();
	}
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().toString()));
		createdOnColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
		lastUpdatedColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getLastUpdatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

		// Listen for selection changes and show the person details when changed.
		reportsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedReport(newValue));

		selectedReport = null;
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override	
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		reportsTable.setItems(reportList);
		arServ = this.mainApplication.getSpringContext().getBean(AnalyticsReportServiceInterface.class);
		refreshReportList();
	}

	/**
	 * @return the reportsTable
	 */
	public TableView<AnalyticsReport> getReportsTable() {
		return reportsTable;
	}

	/**
	 * @param reportsTable the reportsTable to set
	 */
	public void setReportsTable(TableView<AnalyticsReport> reportsTable) {
		this.reportsTable = reportsTable;
	}

	/**
	 * @return the idColumnd
	 */
	public TableColumn<AnalyticsReport, Integer> getIdColumn() {
		return idColumn;
	}

	/**
	 * @param idColumnd the idColumnd to set
	 */
	public void setIdColumn(TableColumn<AnalyticsReport, Integer> idColumnd) {
		this.idColumn = idColumnd;
	}

	/**
	 * @return the typeColumn
	 */
	public TableColumn<AnalyticsReport, String> getTypeColumn() {
		return typeColumn;
	}

	/**
	 * @param typeColumn the typeColumn to set
	 */
	public void setTypeColumn(TableColumn<AnalyticsReport, String> typeColumn) {
		this.typeColumn = typeColumn;
	}

	/**
	 * @return the createdOnColumn
	 */
	public TableColumn<AnalyticsReport, LocalDate> getCreatedOnColumn() {
		return createdOnColumn;
	}

	/**
	 * @param createdOnColumn the createdOnColumn to set
	 */
	public void setCreatedOnColumn(TableColumn<AnalyticsReport, LocalDate> createdOnColumn) {
		this.createdOnColumn = createdOnColumn;
	}

	/**
	 * @return the lastUpdatedColumn
	 */
	public TableColumn<AnalyticsReport, LocalDate> getLastUpdatedColumn() {
		return lastUpdatedColumn;
	}

	/**
	 * @param lastUpdatedColumn the lastUpdatedColumn to set
	 */
	public void setLastUpdatedColumn(TableColumn<AnalyticsReport, LocalDate> lastUpdatedColumn) {
		this.lastUpdatedColumn = lastUpdatedColumn;
	}
	/**
	 * @return the selectedReport
	 */
	public AnalyticsReport getSelectedReport() {
		return selectedReport;
	}
	/**
	 * @param selectedReport the selectedReport to set
	 */
	public void setSelectedReport(AnalyticsReport selectedReport) {
		this.selectedReport = selectedReport;
	}
	/**
	 * @return the reportList
	 */
	public ObservableList<AnalyticsReport> getReportList() {
		return reportList;
	}
	/**
	 * @param reportList the reportList to set
	 */
	public void setReportList(ObservableList<AnalyticsReport> reportList) {
		this.reportList = reportList;
	}
	public void refreshReportList() {
		if(arServ!=null) {
			List<AnalyticsReport> result = arServ.findByUser(this.getMainApplication().getCurrentUser());
			reportList.clear();
			reportList.addAll(result);
		}
	}
	@FXML
	public void onCreateNewReport() {
		
	}
	@FXML
	public void onSeeRawData() {
		if(selectedReport!=null) {
			Class<?> clazz = selectedReport.getClass();
			switch(clazz.getName()) {
			case "es.uam.eps.tweetextractor.model.analytics.report.TimelineVolumeReport":
				this.getMainApplication().showReportRawDataInCenterOfRootLayout("view/analytics/reports/ShowRawData.fxml",ShowRawDataTimelineVolumeReportControl.class,selectedReport);
				break;
			default:
				break;
			}
		}
	}
	@FXML
	public void onUpdateReport() {
		
	}
	@FXML
	public void onDeleteReport() {
		
	}
	
}
