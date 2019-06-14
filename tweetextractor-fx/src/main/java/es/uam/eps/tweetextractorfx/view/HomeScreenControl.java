/**
 * 
 */
package es.uam.eps.tweetextractorfx.view;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTimelineVolumeReportResponse;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTopNHashtagsReportResponse;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTrendsReportResponse;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTweetVolumeNLPReportResponse;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskUpdateExtractionIndefResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTweetVolumeByNamedEntitiesReportSei;
import es.uam.eps.tweetextractor.service.CreateServerTaskTimelineTopNHashtagsReport;
import es.uam.eps.tweetextractor.service.CreateServerTaskTimelineVolumeReport;
import es.uam.eps.tweetextractor.service.CreateServerTaskTweetVolumeByNERTopicsReport;
import es.uam.eps.tweetextractor.service.CreateServerTaskTweetVolumeByNamedEntitiesReport;
import es.uam.eps.tweetextractor.service.CreateServerTaskUpdateExtractionIndef;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.task.CreateTrendsReportTask;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateExtractionServerTaskSelectExtractionDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTimelineTopNHashtagsReportDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTrendsReportServerTaskPreferencesDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.SelectExtractionFilterDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.SelectNERPreferencesDialogControlResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.TweetExtractorFXDialogResponse;
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
public class HomeScreenControl extends TweetExtractorFXController {
	@FXML
	private ImageView logoView;
	@FXML
	private Text userView;

	private Stage loadingDialog = null;
	private CreateServerTaskTrendsReportResponse createServerTaskTrendsReportResponse;
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
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
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
		this.getMainApplication().showScreenInCenterOfRootLayout("view/extraction/QueryConstructor.fxml");
	}

	@FXML
	public void handleAddCredentials() {
		showAddCredentials();
	}

	@FXML
	public void handleManageCredentials() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/credentials/ManageCredentials.fxml");
	}

	@FXML
	public void handleManageExtractions() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/extraction/ShowUserExtractions.fxml");
	}
	/* DIALOGOS */

	public void showAddCredentials() {
		this.showDialogLoadFXML("dialog/credentials/AddCredentialsDialog.fxml");
	}

	public void showManageServerPreferences() {
		this.showDialogLoadFXML("dialog/ServerPreferencesDialog.fxml");
	}

	public String showCreateServerTaskSelectTypeDialog() {
		TweetExtractorFXDialogResponse result=this.showDialogLoadFXML("server/dialog/CreateServerTaskSelectTaskTypeDialog.fxml");
		if(result!=null) {
			return result.getStringValue();
		}
		return null;
	}
	public Extraction showCreateExtractionServerTaskSelectExtractionDialog() {
		TweetExtractorFXDialogResponse result=this.showDialogLoadFXML("server/dialog/CreateExtractionServerTaskSelectExtractionDialog.fxml");
		if(result!=null) {
			return ((CreateExtractionServerTaskSelectExtractionDialogResponse) result).getExtraction();
		}
		return null;
	}

	public String showCreateExtractionServerTaskSelectTypeDialog() {
		TweetExtractorFXDialogResponse result=this.showDialogLoadFXML("server/dialog/CreateExtractionServerTaskSelectTaskTypeDialog.fxml");
		if(result!=null) {
			return result.getStringValue();
		}
		return null;
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
				case (Constants.TOP_N_HASHTAGS_VOLUME_TIMELINE_REPORT):
					onCreateTopNHashtagsReport();
					break;
				case (Constants.OTHER_TIMELINE_REPORT):
					ErrorDialog.showErrorNotAvailableYet();
					break;
				default:
					break;
				}
				break;
			case (Constants.TRENDS_TIMELINE_REPORT_SERVER_TASK_TYPE):
				onCreateTrendsReport();
				break;
			case (Constants.NER_TOPICS_VOLUME_SERVER_TASK_TYPE):
				onCreateNERTopicsVolumeServerTaskType();
			case (Constants.NAMED_ENTITIES_VOLUME_SERVER_TASK_TYPE):
				onCreateNamedEntitiesVolumeServerTaskType();
			break;
			default:
				break;
			}

		}
	}

	private void onCreateNERTopicsVolumeServerTaskType() {
		SelectExtractionFilterDialogResponse extractionFilterReply = showSelectExtractionFilterDialog();
		if (extractionFilterReply==null||extractionFilterReply.getIntValue() == Constants.ERROR) {
			return;
		}
		List<Integer> extractionIdList = new ArrayList<>();
		for (Extraction e : extractionFilterReply.getFilter() ) {
			extractionIdList.add(e.getIdDB());
		}
		SelectNERPreferencesDialogControlResponse nerPreferencesChoice = showSelectNERPreferencesDialog();
		if (nerPreferencesChoice==null||nerPreferencesChoice.getIntValue() == Constants.ERROR) {
			return;
		}
		CreateServerTaskTweetVolumeByNERTopicsReport service = new CreateServerTaskTweetVolumeByNERTopicsReport(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		CreateServerTaskTweetVolumeNLPReportResponse reply = service.createServerTaskTweetVolumeByNERTopicsReport(this.getMainApplication().getCurrentUser().getIdDB(), extractionIdList, nerPreferencesChoice.getPreferences().getIdentifier().getLanguage().getIdentifier(), nerPreferencesChoice.getPreferences().getIdentifier().getName());
		if (reply.isError()) {
			ErrorDialog.showErrorCreateServerTask(reply.getMessage());
		} else {
			ErrorDialog.showSuccessCreateServerTask(reply.getId());
		}
	}

	private void onCreateNamedEntitiesVolumeServerTaskType() {
		SelectExtractionFilterDialogResponse extractionFilterReply = showSelectExtractionFilterDialog();
		if (extractionFilterReply==null||extractionFilterReply.getIntValue() == Constants.ERROR) {
			return;
		}
		List<Integer> extractionIdList = new ArrayList<>();
		for (Extraction e : extractionFilterReply.getFilter() ) {
			extractionIdList.add(e.getIdDB());
		}
		SelectNERPreferencesDialogControlResponse nerPreferencesChoice = showSelectNERPreferencesDialog();
		if (nerPreferencesChoice==null||nerPreferencesChoice.getIntValue() == Constants.ERROR) {
			return;
		}
		CreateServerTaskTweetVolumeByNamedEntitiesReport service = new CreateServerTaskTweetVolumeByNamedEntitiesReport(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		CreateServerTaskTweetVolumeNLPReportResponse reply = service.createServerTaskTweetVolumeByNamedEntitiesReport(this.getMainApplication().getCurrentUser().getIdDB(), extractionIdList, nerPreferencesChoice.getPreferences().getIdentifier().getLanguage().getIdentifier(), nerPreferencesChoice.getPreferences().getIdentifier().getName());
		if (reply.isError()) {
			ErrorDialog.showErrorCreateServerTask(reply.getMessage());
		} else {
			ErrorDialog.showSuccessCreateServerTask(reply.getId());
		}
	}

	

	private void onCreateTrendsReport() {
		SelectExtractionFilterDialogResponse extractionFilterReply = showSelectExtractionFilterDialog();
		if (extractionFilterReply==null||extractionFilterReply.getIntValue() == Constants.ERROR) {
			return;
		}
		List<Integer> extractionIdList = new ArrayList<>();
		for (Extraction e : extractionFilterReply.getFilter() ) {
			extractionIdList.add(e.getIdDB());
		}
		CreateTrendsReportServerTaskPreferencesDialogResponse trendsReportReply = showCreateTrendsReportServerTaskPreferencesDialog();
		if (trendsReportReply==null||trendsReportReply.getIntValue() ==Constants.ERROR) {
			return;
		}
		CreateTrendsReportTask fxTask = new CreateTrendsReportTask(this.getMainApplication().getSpringContext(),this.getMainApplication().getCurrentUser().getIdDB(),trendsReportReply.getType(),trendsReportReply.getIntValue(),extractionIdList,trendsReportReply.getFilterList());
		if(trendsReportReply.getType()==AnalyticsReportTypes.TRWR) {
			fxTask.setLanguageID(trendsReportReply.getLanguageID());
			fxTask.setStopWordsListName(trendsReportReply.getCustomStopWordsListName());
		}
		fxTask.setOnSucceeded(e -> {
			createServerTaskTrendsReportResponse =fxTask.getValue(); 
			if (loadingDialog != null)
				loadingDialog.close();
		});
		fxTask.setOnFailed(e -> {
			if (loadingDialog != null)
				loadingDialog.close();
		});
		Thread thread = new Thread(fxTask);
		thread.setName(fxTask.getClass().getCanonicalName());
		thread.start();
		loadingDialog = mainApplication.showLoadingDialog("Creating report and task...");
		loadingDialog.showAndWait();
		if(createServerTaskTrendsReportResponse==null) {
			return;
		}
		if (createServerTaskTrendsReportResponse.isError()) {
			ErrorDialog.showErrorCreateServerTask(createServerTaskTrendsReportResponse.getMessage());
		} else {
			ErrorDialog.showSuccessCreateServerTask(createServerTaskTrendsReportResponse.getId());
		}
	}

	private void onCreateTopNHashtagsReport() {
		CreateTimelineTopNHashtagsReportDialogResponse reply = showCreateTopNHashTagsReportSelectN();
		if (reply==null||reply.getIntValue() == -1) {
			return;
		}
		CreateServerTaskTimelineTopNHashtagsReport service = new CreateServerTaskTimelineTopNHashtagsReport(
				TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_ADDRESS));
		CreateServerTaskTopNHashtagsReportResponse servReply = service.createServerTaskTopNHashtagsReport(reply.getIntValue(),
				this.getMainApplication().getCurrentUser().getIdDB(),reply.getFilterList());
		if (servReply.isError()) {
			ErrorDialog.showErrorCreateServerTask(servReply.getMessage());
		} else {
			ErrorDialog.showSuccessCreateServerTask(servReply.getId());
		}
	}

	private String showCreateTimelineReportSelectTypeDialog() {
		TweetExtractorFXDialogResponse result=this.showDialogLoadFXML("server/dialog/CreateTimelineReportServerTaskSelectTypeDialog.fxml");
		if(result!=null) {
			return result.getStringValue();
		}
		return "";
	}

	private TweetExtractorFXDialogResponse showDialogLoadFXML(String fxmlPath) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class.getResource(fxmlPath));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			TweetExtractorFXDialogController controller = loader.getController();
			Method meth = controller.getClass().getMethod("setDialogStage", Stage.class);
			meth.invoke(controller, dialogStage);
			meth = controller.getClass().getMethod("setMainApplication", MainApplication.class);
			meth.invoke(controller, this.getMainApplication());
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getResponse();
		} catch (Exception e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}

	private String showCreateAnalyticsTaskSelectTypeDialog() {
		TweetExtractorFXDialogResponse result=this.showDialogLoadFXML("server/dialog/CreateAnalyticsServerTaskSelectTypeDialog.fxml");
		if(result!=null) {
			return result.getStringValue();
		}
		return null;
	}

	private CreateTimelineTopNHashtagsReportDialogResponse showCreateTopNHashTagsReportSelectN() {
		TweetExtractorFXDialogResponse result = this.showDialogLoadFXML("server/dialog/CreateTImelineTopNHashtagsReportSelectNDialog.fxml");
		if(result!=null) {
			return (CreateTimelineTopNHashtagsReportDialogResponse) result;
		}
		return null;
	}
	private SelectExtractionFilterDialogResponse showSelectExtractionFilterDialog() {
		TweetExtractorFXDialogResponse result = this.showDialogLoadFXML("server/dialog/SelectExtractionFilterDialog.fxml");
		if(result!=null) {
			return (SelectExtractionFilterDialogResponse) result;
		}
		return null;
	}
	private SelectNERPreferencesDialogControlResponse showSelectNERPreferencesDialog() {
		TweetExtractorFXDialogResponse result = this.showDialogLoadFXML("server/dialog/SelectNERPreferencesDialog.fxml");
		if(result!=null) {
			return  (SelectNERPreferencesDialogControlResponse) result;
		}
		return null;
	}
	private CreateTrendsReportServerTaskPreferencesDialogResponse showCreateTrendsReportServerTaskPreferencesDialog() {
		TweetExtractorFXDialogResponse result = this.showDialogLoadFXML("server/dialog/CreateTrendsReportServerTaskPreferencesDialog.fxml");
		if(result!=null) {
			return (CreateTrendsReportServerTaskPreferencesDialogResponse) result;
		}
		return null;
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
		this.getMainApplication().showScreenInCenterOfRootLayout("view/server/ManageServerTasks.fxml");

	}

	@FXML
	public void onManageServerPreferences() {
		showManageServerPreferences();

	}

	/* Analytics menu */
	@FXML
	public void onMyReports() {
		this.mainApplication.showScreenInCenterOfRootLayout("view/analytics/reports/MyReports.fxml");
	}

	@FXML
	public void onGraphicsForAnalytics() {
		this.mainApplication.showScreenInCenterOfRootLayout("view/analytics/reports/graphics/MyGraphics.fxml");
	}
	@FXML
	public void onNLPPreferences() {
		if (!this.getMainApplication().getCurrentUser().hasAnyCredentials()) {
			ErrorDialog.showErrorNoCredentials();
		}else {
			this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/NLPPreferencesHome.fxml");
		}
	}
	/**
	 * @return the userView
	 */
	public Text getUserView() {
		return userView;
	}

	/**
	 * @param userView the userView to set
	 */
	public void setUserView(Text userView) {
		this.userView = userView;
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
