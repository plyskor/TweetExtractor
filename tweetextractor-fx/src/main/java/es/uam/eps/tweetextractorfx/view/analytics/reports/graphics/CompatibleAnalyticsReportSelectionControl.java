/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
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
public class CompatibleAnalyticsReportSelectionControl extends TweetExtractorFXController {
	@FXML
	private TableView<AnalyticsCategoryReport> reportsTable;
	@FXML
	private TableColumn<AnalyticsCategoryReport, Integer> idColumn;
	@FXML
	private TableColumn<AnalyticsCategoryReport, String> typeColumn;
	@FXML
	private TableColumn<AnalyticsCategoryReport, LocalDate> createdOnColumn;
	@FXML
	private TableColumn<AnalyticsCategoryReport, LocalDate> lastUpdatedColumn;
	private AnalyticsReport selectedReport = null;
	private ObservableList<AnalyticsCategoryReport> reportList = FXCollections.observableArrayList();
	private AnalyticsReportServiceInterface arServ;
	private AnalyticsReportImageTypes input;
	private AnalyticsRepresentableReport toReturn;

	public CompatibleAnalyticsReportSelectionControl() {
		super();
	}
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Constants.REPORT_TYPES_GUI.get(cellData.getValue().getReportType())));
		createdOnColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
		lastUpdatedColumn.setCellValueFactory(cellData -> {
		if(cellData.getValue().getLastUpdatedDate()!=null) {
			return new SimpleObjectProperty<LocalDate>(cellData.getValue().getLastUpdatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}else {
			return null;
		}
		});

		// Listen for selection changes and show the person details when changed.
		reportsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedReport(newValue));

		selectedReport = null;
	}
	@Override	
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		reportsTable.setItems(reportList);
		arServ = this.mainApplication.getSpringContext().getBean(AnalyticsReportServiceInterface.class);
		refreshReportList();
	}
	public void refreshReportList() {
		
		if(arServ!=null) {
			List<AnalyticsCategoryReport> result = arServ.findByUserAndReportType(this.getMainApplication().getCurrentUser(), Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(input));
			reportList.clear();
			reportList.addAll(result);
		}
	}
	/**
	 * @return the reportsTable
	 */
	public TableView<AnalyticsCategoryReport> getReportsTable() {
		return reportsTable;
	}

	/**
	 * @param reportsTable the reportsTable to set
	 */
	public void setReportsTable(TableView<AnalyticsCategoryReport> reportsTable) {
		this.reportsTable = reportsTable;
	}

	
	
	/**
	 * @return the idColumn
	 */
	public TableColumn<AnalyticsCategoryReport, Integer> getIdColumn() {
		return idColumn;
	}
	/**
	 * @param idColumn the idColumn to set
	 */
	public void setIdColumn(TableColumn<AnalyticsCategoryReport, Integer> idColumn) {
		this.idColumn = idColumn;
	}
	/**
	 * @return the typeColumn
	 */
	public TableColumn<AnalyticsCategoryReport, String> getTypeColumn() {
		return typeColumn;
	}
	/**
	 * @param typeColumn the typeColumn to set
	 */
	public void setTypeColumn(TableColumn<AnalyticsCategoryReport, String> typeColumn) {
		this.typeColumn = typeColumn;
	}
	/**
	 * @return the createdOnColumn
	 */
	public TableColumn<AnalyticsCategoryReport, LocalDate> getCreatedOnColumn() {
		return createdOnColumn;
	}
	/**
	 * @param createdOnColumn the createdOnColumn to set
	 */
	public void setCreatedOnColumn(TableColumn<AnalyticsCategoryReport, LocalDate> createdOnColumn) {
		this.createdOnColumn = createdOnColumn;
	}
	/**
	 * @return the lastUpdatedColumn
	 */
	public TableColumn<AnalyticsCategoryReport, LocalDate> getLastUpdatedColumn() {
		return lastUpdatedColumn;
	}
	/**
	 * @param lastUpdatedColumn the lastUpdatedColumn to set
	 */
	public void setLastUpdatedColumn(TableColumn<AnalyticsCategoryReport, LocalDate> lastUpdatedColumn) {
		this.lastUpdatedColumn = lastUpdatedColumn;
	}
	/**
	 * @return the reportList
	 */
	public ObservableList<AnalyticsCategoryReport> getReportList() {
		return reportList;
	}
	/**
	 * @param reportList the reportList to set
	 */
	public void setReportList(ObservableList<AnalyticsCategoryReport> reportList) {
		this.reportList = reportList;
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
	 * @return the arServ
	 */
	public AnalyticsReportServiceInterface getArServ() {
		return arServ;
	}

	/**
	 * @param arServ the arServ to set
	 */
	public void setArServ(AnalyticsReportServiceInterface arServ) {
		this.arServ = arServ;
	}
	/**
	 * @return the input
	 */
	public AnalyticsReportImageTypes getInput() {
		return input;
	}
	/**
	 * @param input the input to set
	 */
	public void setInput(AnalyticsReportImageTypes input) {
		this.input = input;
	}
	/**
	 * @return the toReturn
	 */
	public AnalyticsRepresentableReport getToReturn() {
		return toReturn;
	}
	/**
	 * @param toReturn the toReturn to set
	 */
	public void setToReturn(AnalyticsRepresentableReport toReturn) {
		this.toReturn = toReturn;
	}
	@FXML
	public void onBack() {
		this.mainApplication.showScreenInCenterOfRootLayout("view/analytics/reports/graphics/MyGraphics.fxml");
	}
	@FXML 
	public void onNext() {
		if(selectedReport==null) {
			ErrorDialog.showErrorNoSelectedReport();
		}else if(selectedReport.isEmpty()){
			ErrorDialog.showErrorEmptyReport();
		}else {
			if (input.equals(AnalyticsReportImageTypes.WCC)) {
				this.getMainApplication().showWordCloudChartPreferences((AnalyticsRepresentableReport)selectedReport);
			}else {
				this.mainApplication.showChartGraphicsPreferences(input,(AnalyticsRepresentableReport)selectedReport);
			}
		}
	}
}
