/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.nlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.CustomStopWordsListServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorfx.view.dialog.nlp.CreateCustomStopWordsListDialogControl;
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
 * @author joseantoniogarciadelsaz
 *
 */
public class ManageCustomStopWordsListControl extends TweetExtractorFXController {
	@FXML
	private TableView<CustomStopWordsList> customStopWordsListTable;
	@FXML
	private TableColumn<CustomStopWordsList, String> customStopWordsListNameColumn;
	@FXML
	private ChoiceBox<String> languageChoiceBox;
	private ObservableList<CustomStopWordsList> tableList = FXCollections.observableArrayList();
	private CustomStopWordsList selectedCustomStopWordsList = null;
	private ReferenceAvailableLanguagesServiceInterface languageServ;
	private CustomStopWordsListServiceInterface swlServ;
	private List<AvailableTwitterLanguage> availableLanguagesList = new ArrayList<>();
	private AvailableTwitterLanguage selectedAvailableTwitterLanguage = null;
	private Logger logger = LoggerFactory.getLogger(ManageCustomStopWordsListControl.class);

	public ManageCustomStopWordsListControl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#
	 * setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		customStopWordsListTable.setItems(tableList);
		languageServ = this.getMainApplication().getSpringContext()
				.getBean(ReferenceAvailableLanguagesServiceInterface.class);
		swlServ = this.getMainApplication().getSpringContext().getBean(CustomStopWordsListServiceInterface.class);
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
					loadStopWordsLists();
				});
		setSelectedAvailableTwitterLanguage(findLanguageByLongName("English"));
		languageChoiceBox.setValue(selectedAvailableTwitterLanguage.getLongName());
	}

	private void loadStopWordsLists() {
		if (selectedAvailableTwitterLanguage != null) {
			tableList.clear();
			List<CustomStopWordsList> list = swlServ.findByUserAndLanguage(this.getMainApplication().getCurrentUser(),
					selectedAvailableTwitterLanguage);
			if (list != null && !list.isEmpty()) {
				tableList.addAll(list);
			}
		}
	}

	@FXML
	private void initialize() {
		customStopWordsListNameColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		customStopWordsListTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedCustomStopWordsList(newValue));
		selectedCustomStopWordsList = null;
	}

	@FXML
	private void onDone() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/NLPPreferencesHome.fxml");
	}

	@FXML
	private void onNew() {
		showCreateNewCustomListDialog();
		loadStopWordsLists();
	}

	@FXML
	private void onEdit() {
		if (selectedCustomStopWordsList == null) {
			ErrorDialog.showErrorNoSelectedStopWordsList();
		} else {
			showEditCustomStopWordsList(selectedCustomStopWordsList);
		}
	}

	@FXML
	private void onDelete() {
		if (selectedCustomStopWordsList == null) {
			ErrorDialog.showErrorNoSelectedStopWordsList();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"This action will delete the list " + selectedCustomStopWordsList.getName() + "("+selectedAvailableTwitterLanguage.getShortName()+")" 
							+ ", and also all its content. Are you sure you want to continue?",
					ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				swlServ.delete(selectedCustomStopWordsList);
				loadStopWordsLists();
			}
		}
	}

	/**
	 * @return the customStopWordsListTable
	 */
	public TableView<CustomStopWordsList> getCustomStopWordsListTable() {
		return customStopWordsListTable;
	}

	/**
	 * @param customStopWordsListTable the customStopWordsListTable to set
	 */
	public void setCustomStopWordsListTable(TableView<CustomStopWordsList> customStopWordsListTable) {
		this.customStopWordsListTable = customStopWordsListTable;
	}

	/**
	 * @return the customStopWordsListNameColumn
	 */
	public TableColumn<CustomStopWordsList, String> getCustomStopWordsListNameColumn() {
		return customStopWordsListNameColumn;
	}

	/**
	 * @param customStopWordsListNameColumn the customStopWordsListNameColumn to set
	 */
	public void setCustomStopWordsListNameColumn(
			TableColumn<CustomStopWordsList, String> customStopWordsListNameColumn) {
		this.customStopWordsListNameColumn = customStopWordsListNameColumn;
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
	public ObservableList<CustomStopWordsList> getTableList() {
		return tableList;
	}

	/**
	 * @param tableList the tableList to set
	 */
	public void setTableList(ObservableList<CustomStopWordsList> tableList) {
		this.tableList = tableList;
	}

	/**
	 * @return the languageServ
	 */
	public ReferenceAvailableLanguagesServiceInterface getLanguageServ() {
		return languageServ;
	}

	/**
	 * @param languageServ the languageServ to set
	 */
	public void setLanguageServ(ReferenceAvailableLanguagesServiceInterface languageServ) {
		this.languageServ = languageServ;
	}

	/**
	 * @return the swlServ
	 */
	public CustomStopWordsListServiceInterface getSwlServ() {
		return swlServ;
	}

	/**
	 * @param swlServ the swlServ to set
	 */
	public void setSwlServ(CustomStopWordsListServiceInterface swlServ) {
		this.swlServ = swlServ;
	}

	/**
	 * @return the selectedCustomStopWordsList
	 */
	public CustomStopWordsList getSelectedCustomStopWordsList() {
		return selectedCustomStopWordsList;
	}

	/**
	 * @param selectedCustomStopWordsList the selectedCustomStopWordsList to set
	 */
	public void setSelectedCustomStopWordsList(CustomStopWordsList selectedCustomStopWordsList) {
		this.selectedCustomStopWordsList = selectedCustomStopWordsList;
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
	 * @param selectedAvailableTwitterLanguage the selectedAvailableTwitterLanguage
	 *                                         to set
	 */
	public void setSelectedAvailableTwitterLanguage(AvailableTwitterLanguage selectedAvailableTwitterLanguage) {
		this.selectedAvailableTwitterLanguage = selectedAvailableTwitterLanguage;
	}

	public Stage showCreateNewCustomListDialog() {
		// Load the fxml file and create a new stage for the popup dialog.
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MainApplication.class.getResource("view/dialog/nlp/CreateCustomStopWordsListDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.initOwner(this.getMainApplication().getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Create New Custom Stop Words List");
			// Set the dialogStage to the controller.
			CreateCustomStopWordsListDialogControl controller = loader.getController();
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
	public void showEditCustomStopWordsList(CustomStopWordsList listInput) {
		try {	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/nlp/EditCustomStopWordsList.fxml"));
			Node rootNode = loader.load();
			// Set query constructor into the center of root layout.
			this.getMainApplication().getRootLayout().setCenter(rootNode);
			// Give the controller access to the main app.
			EditCustomStopWordsListControl controller = loader.getController();
			controller.setListInput(listInput);
			controller.setMainApplication(this.getMainApplication());
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
