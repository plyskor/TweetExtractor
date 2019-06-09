/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.nlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERConfigurationServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorfx.view.dialog.nlp.CreateNERPreferencesDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.nlp.SelectNERTokenSetDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTokenSetSelectCustomStopWordsListDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.SelectNERTokenSetDialogResponse;
import es.uam.eps.tweetextractorfx.view.server.dialog.CreateTokenSetSelectCustomStopWordsListDialogControl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
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
public class MyNERPreferencesControl extends TweetExtractorFXController {
	@FXML
	private TableView<TweetExtractorNERConfiguration> preferencesTable;
	@FXML
	private TableColumn<TweetExtractorNERConfiguration, String> preferencesNameColumn;
	@FXML
	private ChoiceBox<String> languageChoiceBox;
	private ObservableList<TweetExtractorNERConfiguration> tableList = FXCollections.observableArrayList();
	private TweetExtractorNERConfiguration selectedPreferences = null;
	private TweetExtractorNERConfigurationServiceInterface pServ;
	private List<AvailableTwitterLanguage> availableLanguagesList = new ArrayList<>();
	private AvailableTwitterLanguage selectedAvailableTwitterLanguage = null;
	private Logger logger = LoggerFactory.getLogger(MyNERPreferencesControl.class);

	/**
	 * 
	 */
	public MyNERPreferencesControl() {
		super();
	}
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		preferencesTable.setItems(tableList);
		ReferenceAvailableLanguagesServiceInterface languageServ = this.getMainApplication().getSpringContext()
				.getBean(ReferenceAvailableLanguagesServiceInterface.class);
		pServ=this.getMainApplication().getSpringContext()
				.getBean(TweetExtractorNERConfigurationServiceInterface.class);
		availableLanguagesList = languageServ.findAll();
		initializeLanguageChoiceBox();
	}
	private void initializeLanguageChoiceBox() {
		for (AvailableTwitterLanguage availableLanguage : availableLanguagesList) {
			languageChoiceBox.getItems().add(availableLanguage.getLongName());
		}
		/* Choice box language selector listener */
		languageChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					setSelectedAvailableTwitterLanguage(findLanguageByLongName(newValue));
					loadPreferences();
				});
		setSelectedAvailableTwitterLanguage(findLanguageByLongName("English"));
		languageChoiceBox.setValue(selectedAvailableTwitterLanguage.getLongName());
	}
	private void loadPreferences() {
		if (selectedAvailableTwitterLanguage != null) {
			tableList.clear();
			List<TweetExtractorNERConfiguration> list = pServ.findByUserAndLanguage(this.getMainApplication().getCurrentUser(),
					selectedAvailableTwitterLanguage);
			if (list != null && !list.isEmpty()) {
				tableList.addAll(list);
			}
		}
		 preferencesTable.getColumns().get(0).setVisible(false);
		 preferencesTable.getColumns().get(0).setVisible(true);
	}
	@FXML
	private void initialize() {
		preferencesNameColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentifier().getName()));
		preferencesTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedPreferences(newValue));
		selectedPreferences = null;
	}
	@FXML
	private void onDone() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/NLPPreferencesHome.fxml");
	}
	@FXML
	private void onEdit() {
		if (selectedPreferences == null) {
			ErrorDialog.showErrorNoSelectedNERPreferences();
		} else {
			showEditNERPreferences();
		}
	}
	@FXML
	private void onClassifyTokens() {
		if (selectedPreferences == null) {
			ErrorDialog.showErrorNoSelectedNERPreferences();
		} else {
			SelectNERTokenSetDialogResponse tokenSetChoice = showSelectTokenSetDialog(selectedAvailableTwitterLanguage);
			if(tokenSetChoice!=null&&tokenSetChoice.getIntValue()==Constants.SUCCESS) {
				showClassifyTokensScreen(selectedPreferences,tokenSetChoice.getTokenSet());
			}
		}
	}
	private void showClassifyTokensScreen(TweetExtractorNERConfiguration preferences,
			TweetExtractorNERTokenSet tokenSet) {
		try {	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/nlp/ClassifyTokensFromSet.fxml"));
			Node rootNode = loader.load();
			// Set query constructor into the center of root layout.
			this.getMainApplication().getRootLayout().setCenter(rootNode);
			// Give the controller access to the main app.
			ClassifyTokensFromSetControl controller = loader.getController();
			controller.setPreferences(preferences);
			controller.setTokenSetID(tokenSet.getIdentifier());
			controller.setMainApplication(this.getMainApplication());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	private SelectNERTokenSetDialogResponse showSelectTokenSetDialog(
			AvailableTwitterLanguage language) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class
					.getResource("view/dialog/nlp/SelectNERTokenSetDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.getMainApplication().getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			SelectNERTokenSetDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setLanguage(language);
			controller.setMainApplication(this.getMainApplication());
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return (SelectNERTokenSetDialogResponse) controller.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
	@FXML
	private void onNew() {
		showCreateNewNERPreferencesDialog();
		loadPreferences();
	}
	@FXML
	private void onDelete() {
		if (selectedPreferences == null) {
			ErrorDialog.showErrorNoSelectedNERPreferences();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"This action will delete the NER preferences set " + selectedPreferences.getIdentifier().getName() + "("+selectedAvailableTwitterLanguage.getShortName()+")" 
							+ ", and also all its content. Are you sure you want to continue?",
					ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				pServ.delete(selectedPreferences);
				loadPreferences();
			}
		}
	}
	/**
	 * @return the preferencesTable
	 */
	public TableView<TweetExtractorNERConfiguration> getPreferencesTable() {
		return preferencesTable;
	}

	/**
	 * @param preferencesTable the preferencesTable to set
	 */
	public void setPreferencesTable(TableView<TweetExtractorNERConfiguration> preferencesTable) {
		this.preferencesTable = preferencesTable;
	}

	/**
	 * @return the preferencesNameColumn
	 */
	public TableColumn<TweetExtractorNERConfiguration, String> getPreferencesNameColumn() {
		return preferencesNameColumn;
	}

	/**
	 * @param preferencesNameColumn the preferencesNameColumn to set
	 */
	public void setPreferencesNameColumn(TableColumn<TweetExtractorNERConfiguration, String> preferencesNameColumn) {
		this.preferencesNameColumn = preferencesNameColumn;
	}

	/**
	 * @return the languageChoiceBox
	 */
	public ChoiceBox<String> getLanguageChoiceBox() {
		return languageChoiceBox;
	}

	/**
	 * @param languageChoiceBox the languageChoiceBox to set
	 */
	public void setLanguageChoiceBox(ChoiceBox<String> languageChoiceBox) {
		this.languageChoiceBox = languageChoiceBox;
	}

	/**
	 * @return the tableList
	 */
	public ObservableList<TweetExtractorNERConfiguration> getTableList() {
		return tableList;
	}

	/**
	 * @param tableList the tableList to set
	 */
	public void setTableList(ObservableList<TweetExtractorNERConfiguration> tableList) {
		this.tableList = tableList;
	}

	/**
	 * @return the selectedPreferences
	 */
	public TweetExtractorNERConfiguration getSelectedPreferences() {
		return selectedPreferences;
	}

	/**
	 * @param selectedPreferences the selectedPreferences to set
	 */
	public void setSelectedPreferences(TweetExtractorNERConfiguration selectedPreferences) {
		this.selectedPreferences = selectedPreferences;
	}

	/**
	 * @return the availableLanguagesList
	 */
	public List<AvailableTwitterLanguage> getAvailableLanguagesList() {
		return availableLanguagesList;
	}

	/**
	 * @param availableLanguagesList the availableLanguagesList to set
	 */
	public void setAvailableLanguagesList(List<AvailableTwitterLanguage> availableLanguagesList) {
		this.availableLanguagesList = availableLanguagesList;
	}

	/**
	 * @return the selectedAvailableTwitterLanguage
	 */
	public AvailableTwitterLanguage getSelectedAvailableTwitterLanguage() {
		return selectedAvailableTwitterLanguage;
	}

	/**
	 * @param selectedAvailableTwitterLanguage the selectedAvailableTwitterLanguage to set
	 */
	public void setSelectedAvailableTwitterLanguage(AvailableTwitterLanguage selectedAvailableTwitterLanguage) {
		this.selectedAvailableTwitterLanguage = selectedAvailableTwitterLanguage;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public Stage showCreateNewNERPreferencesDialog() {
		// Load the fxml file and create a new stage for the popup dialog.
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MainApplication.class.getResource("view/dialog/nlp/CreateNERPreferencesDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.initOwner(this.getMainApplication().getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Create New Named Entity Recognition Preferences");
			// Set the dialogStage to the controller.
			CreateNERPreferencesDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			controller.setLanguage(selectedAvailableTwitterLanguage);
			dialogStage.showAndWait();
			// Show the dialog and wait until the user closes it, then add filter
			return dialogStage;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	private void showEditNERPreferences() {
		try {	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/nlp/EditNERPreferences.fxml"));
			Node rootNode = loader.load();
			// Set query constructor into the center of root layout.
			this.getMainApplication().getRootLayout().setCenter(rootNode);
			// Give the controller access to the main app.
			EditNERPreferencesControl controller = loader.getController();
			controller.setMainApplication(this.getMainApplication());
			controller.setPreferences(selectedPreferences);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	private AvailableTwitterLanguage findLanguageByLongName(String longName) {
		for (AvailableTwitterLanguage availableLanguage : availableLanguagesList) {
			if (longName.equals(availableLanguage.getLongName())) {
				return availableLanguage;
			}
		}
		return null;
	}
}
