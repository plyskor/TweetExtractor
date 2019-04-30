/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.CustomStopWordsListServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;

import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineTopNHashtagsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineVolumeReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingHashtagsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingUserMentionsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingUsersReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingWordsReport;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.task.DeleteAnalyticsReportTask;
import es.uam.eps.tweetextractorfx.task.UpdateAnalyticsReportTask;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTimelineTopNHashtagsReportDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTrendsReportServerTaskPreferencesDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.SelectExtractionFilterDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.TweetExtractorFXDialogResponse;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateAnalyticsReportSelectTypeDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateTImelineTopNHashtagsReportSelectNDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateTimelineReportServerTaskSelectTypeDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateTrendsReportServerTaskPreferencesDialogControl;
import es.uam.eps.tweetextractorfx.view.server.dialog.SelectExtractionFilterDialogControl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

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
	private ReferenceAvailableLanguagesServiceInterface languageServ;
	private CustomStopWordsListServiceInterface swlServ;
	private TweetServiceInterface tServ;
	private Stage loadingDialog = null;
	private Alert alertUpdate = null;
	private Alert alertDelete = null;
	private Logger logger = LoggerFactory.getLogger(MyReportsControl.class);


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
		typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
				Constants.REPORT_TYPES_GUI.get(cellData.getValue().getReportType())));
		createdOnColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(
				cellData.getValue().getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
		lastUpdatedColumn.setCellValueFactory(cellData -> {
			if (cellData.getValue().getLastUpdatedDate() != null) {
				return new SimpleObjectProperty<LocalDate>(cellData.getValue().getLastUpdatedDate().toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate());
			} else {
				return null;
			}
		});
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
		tServ=this.mainApplication.getSpringContext().getBean(TweetServiceInterface.class);
		swlServ= this.mainApplication.getSpringContext().getBean(CustomStopWordsListServiceInterface.class);
		languageServ=this.mainApplication.getSpringContext().getBean(ReferenceAvailableLanguagesServiceInterface.class);
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
		if (arServ != null) {
			List<AnalyticsCategoryReport> result = arServ.findByUser(this.getMainApplication().getCurrentUser());
			reportList.clear();
			reportList.addAll(result);
		}
	}

	@FXML
	public void onCreateNewReport() {
		TweetExtractorFXDialogResponse response = this.getMainApplication().showDialogLoadFXML("view/server/dialog/CreateAnalyticsReportSelectTypeDialog.fxml", CreateAnalyticsReportSelectTypeDialogControl.class);
		if(response==null||response.getStringValue()==null||StringUtils.isBlank(response.getStringValue())) {
			return;
		}
		switch(response.getStringValue()) {
		case "TR":
			onCreateTimelineReport();
			break;
		case "TRR":
			onCreateTrendsReport();
			break;
		default:
			break;
		}
	}

	private void onCreateTrendsReport() {
		SelectExtractionFilterDialogResponse extractionFilterReply = showSelectExtractionFilterDialog();
		if (extractionFilterReply==null||extractionFilterReply.getIntValue() == Constants.ERROR) {
			return;
		}
		CreateTrendsReportServerTaskPreferencesDialogResponse trendsReportReply = showCreateTrendsReportServerTaskPreferencesDialog();
		if (trendsReportReply==null||trendsReportReply.getIntValue() ==Constants.ERROR) {
			return;
		}
		TrendsReport report = null;
		String word ="";
		switch (trendsReportReply.getType()) {
			case TRHR:
				report = new TrendingHashtagsReport();
				word ="hashtags";
				break;
			case TRUR:
				report = new TrendingUsersReport();
				word ="users";
				break;
			case TRUMR:
				report = new TrendingUserMentionsReport();
				word ="user mentions";
				break;
			case TRWR:
				report = new TrendingWordsReport();
				CustomStopWordsListID id = new CustomStopWordsListID(languageServ.findById(trendsReportReply.getLanguageID()), this.getMainApplication().getCurrentUser(), trendsReportReply.getCustomStopWordsListName());
				((TrendingWordsReport) report).setStopWordsList(swlServ.findById(id));
				((TrendingWordsReport) report).setLanguage(id.getLanguage());
				if(((TrendingWordsReport) report).getLanguage()==null) {
					logger.warn("No language found for ID: "+trendsReportReply.getLanguageID());
					return;
				}
				if(((TrendingWordsReport) report).getStopWordsList()==null) {
					logger.warn("No stop words list found with name: "+trendsReportReply.getCustomStopWordsListName());
					return ;
				}
				word ="words";
				break;
			default:
				break;
		}
		report.setReportType(trendsReportReply.getType());
		report.setUser(this.getMainApplication().getCurrentUser());
		report.setN(trendsReportReply.getIntValue());
		report.setExtractions(extractionFilterReply.getFilter());
		report.setStringFilterList(trendsReportReply.getFilterList());
    	arServ.persist(report);
    	AnalyticsReportCategory category = new AnalyticsReportCategory("Trending "+ word);
		category.setReport(report);
		report.getCategories().add(category);
		arServ.saveOrUpdate(report);
		ErrorDialog.showSuccessCreateNewReport(report.getId());
		logger.info("New trending "+word+" report created with ID: "+report.getId());
	}
	private SelectExtractionFilterDialogResponse showSelectExtractionFilterDialog() {
		TweetExtractorFXDialogResponse result = this.mainApplication.showDialogLoadFXML("view/server/dialog/SelectExtractionFilterDialog.fxml",SelectExtractionFilterDialogControl.class);
		if(result!=null) {
			return (SelectExtractionFilterDialogResponse) result;
		}
		return null;
	}
	private CreateTrendsReportServerTaskPreferencesDialogResponse showCreateTrendsReportServerTaskPreferencesDialog() {
		TweetExtractorFXDialogResponse result = this.mainApplication.showDialogLoadFXML("view/server/dialog/CreateTrendsReportServerTaskPreferencesDialog.fxml",CreateTrendsReportServerTaskPreferencesDialogControl.class);
		if(result!=null) {
			return (CreateTrendsReportServerTaskPreferencesDialogResponse) result;
		}
		return null;
	}
	private void onCreateTimelineReport() {
		TweetExtractorFXDialogResponse response = this.getMainApplication().showDialogLoadFXML("view/server/dialog/CreateTimelineReportServerTaskSelectTypeDialog.fxml", CreateTimelineReportServerTaskSelectTypeDialogControl.class);
		if(response==null||response.getStringValue()==null||StringUtils.isBlank(response.getStringValue())) {
			return;
		}
		switch(response.getStringValue()) {
		case Constants.TWEET_VOLUME_TIMELINE_REPORT:
			onCreateTVR();
			break;
		case Constants.TOP_N_HASHTAGS_VOLUME_TIMELINE_REPORT:
			onCreateTTNHR();
			break;
		case Constants.OTHER_TIMELINE_REPORT:
			ErrorDialog.showErrorNotAvailableYet();
			break;
		default:
				break;
				
		}
		refreshReportList();
	}


	
	@FXML
	public void onSeeRawData() {
		if (selectedReport != null) {
			Class<?> clazz = selectedReport.getClass();
			switch (clazz.getName()) {
			case "es.uam.eps.tweetextractor.model.analytics.report.TimelineVolumeReport":
				this.getMainApplication().showReportRawDataInCenterOfRootLayout(
						"view/analytics/reports/ShowRawData.fxml", ShowRawDataTimelineVolumeReportControl.class,
						selectedReport);
				break;
			default:
				break;
			}
		} else {
			ErrorDialog.showErrorNoSelectedReport();
		}
	}

	@FXML
	public void onUpdateReport() {
		if (selectedReport != null) {
			UpdateAnalyticsReportTask updateTask = new UpdateAnalyticsReportTask(
					this.getMainApplication().getSpringContext(), (AnalyticsCategoryReport) selectedReport);
			updateTask.setOnSucceeded(e -> {
				if (loadingDialog != null) {
					loadingDialog.close();
				}
				alertUpdate = updateTask.getValue().equals("SUCCESS")
						? ErrorDialog.showSuccessUpdateReport(selectedReport.getId())
						: ErrorDialog.showGenericMessageUpdateReport(updateTask.getValue());
			});
			updateTask.setOnFailed(e -> {
				if (loadingDialog != null) {
					loadingDialog.close();
				}
				alertUpdate = ErrorDialog.showGenericMessageUpdateReport(updateTask.getValue());
			});
			Thread thread = new Thread(updateTask);
			thread.setName(updateTask.getClass().getCanonicalName());
			thread.start();
			loadingDialog = mainApplication.showLoadingDialog("Updating report " + selectedReport.getId());
			loadingDialog.showAndWait();
			if (alertUpdate != null)
				alertUpdate.showAndWait();
			refreshReportList();
		} else {
			ErrorDialog.showErrorNoSelectedReport();
		}
	}

	@FXML
	public void onDeleteReport() {
		if (selectedReport != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "This action will delete the report "
					+ selectedReport.getId() + " and all its data. Are you sure you want to continue?", ButtonType.YES,
					ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				DeleteAnalyticsReportTask deleteTask = new DeleteAnalyticsReportTask(
						this.getMainApplication().getSpringContext(), (AnalyticsCategoryReport) selectedReport);
				deleteTask.setOnSucceeded(e -> {
					if (loadingDialog != null) {
						loadingDialog.close();
					}
					alertDelete = deleteTask.getValue()== Constants.SUCCESS ? ErrorDialog.showSuccessDeleteReport(selectedReport.getId()) : ErrorDialog.showErrorDeleteReport();
				});
				deleteTask.setOnFailed(e -> {
					if (loadingDialog != null) {
						loadingDialog.close();
					}
					alertDelete=ErrorDialog.showErrorDeleteReport();
				});
				Thread thread = new Thread(deleteTask);
				thread.setName(deleteTask.getClass().getCanonicalName());
				thread.start();
				loadingDialog = mainApplication.showLoadingDialog("Deleting report " + selectedReport.getId());
				loadingDialog.showAndWait();
				if (alertDelete != null)
					alertDelete.showAndWait();
				refreshReportList();
			}
		} else {
			ErrorDialog.showErrorNoSelectedReport();
		}
	}
	private void onCreateTVR() {
		TimelineVolumeReport report = new TimelineVolumeReport();
		AnalyticsReportCategory nTweetsCategory = new AnalyticsReportCategory("Number of tweets");
		nTweetsCategory.setReport(report);
		report.getCategories().add(nTweetsCategory);
		report.setUser(this.getMainApplication().getCurrentUser());
		arServ.persist(report);
		ErrorDialog.showSuccessCreateNewReport(report.getId());
		logger.info("New timeline volume report created with ID: "+report.getId());
	}
	private void onCreateTTNHR() {
		CreateTimelineTopNHashtagsReportDialogResponse reply = showCreateTopNHashTagsReportSelectN();
		if (reply==null||reply.getIntValue() == -1) {
			return;
		}
		TimelineTopNHashtagsReport report = new TimelineTopNHashtagsReport(reply.getIntValue());
		report.setUser(this.getMainApplication().getCurrentUser());
		List<String> topNHashtags=null;
		List<String> hashtagFilter = reply.getFilterList();
		topNHashtags = (hashtagFilter!=null&&!hashtagFilter.isEmpty()) ? tServ.findTopNHashtagsFiltered(reply.getIntValue(), hashtagFilter):tServ.findTopNHashtags(reply.getIntValue());
		for(String hashtag:topNHashtags) {
			AnalyticsReportCategory category = new AnalyticsReportCategory(hashtag);
			category.setReport(report);
			report.getCategories().add(category);
		}
		arServ.persist(report);
		ErrorDialog.showSuccessCreateNewReport(report.getId());
		logger.info("New top "+reply.getIntValue() +" hashtags timeline report created with ID: "+report.getId());
	}
	private CreateTimelineTopNHashtagsReportDialogResponse showCreateTopNHashTagsReportSelectN() {
		TweetExtractorFXDialogResponse result = this.mainApplication.showDialogLoadFXML("view/server/dialog/CreateTImelineTopNHashtagsReportSelectNDialog.fxml", CreateTImelineTopNHashtagsReportSelectNDialogControl.class);
		if(result!=null) {
			return (CreateTimelineTopNHashtagsReportDialogResponse) result;
		}
		return null;
	}

}
