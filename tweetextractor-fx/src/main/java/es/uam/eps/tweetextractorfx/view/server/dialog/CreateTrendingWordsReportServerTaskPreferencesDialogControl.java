/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uam.eps.tweetextractor.dao.service.inter.CustomStopWordsListServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTrendsReportServerTaskPreferencesDialogResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author jose
 *
 */
public class CreateTrendingWordsReportServerTaskPreferencesDialogControl extends TweetExtractorFXDialogController {
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
	private Logger logger = LoggerFactory.getLogger(CreateTrendingWordsReportServerTaskPreferencesDialogControl.class);
	/**
	 * 
	 */
	public CreateTrendingWordsReportServerTaskPreferencesDialogControl() {
		super();
	}
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		customStopWordsListTable.setItems(tableList);
		this.setResponse(new CreateTrendsReportServerTaskPreferencesDialogResponse());
		languageServ = this.getMainApplication().getSpringContext()
				.getBean(ReferenceAvailableLanguagesServiceInterface.class);
		swlServ = this.getMainApplication().getSpringContext().getBean(CustomStopWordsListServiceInterface.class);
		availableLanguagesList = languageServ.findAll();
		initializeLanguageChoiceBox();
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
	private void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	private void onDone() {
		if(selectedCustomStopWordsList==null) {
			ErrorDialog.showErrorNoSelectedStopWordsList();
		}else {
			((CreateTrendsReportServerTaskPreferencesDialogResponse) this.getResponse()).setLanguageID(selectedAvailableTwitterLanguage.getIdentifier());
			((CreateTrendsReportServerTaskPreferencesDialogResponse) this.getResponse()).setCustomStopWordsListName(selectedCustomStopWordsList.getName());
			this.dialogStage.close();
		}
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
	private AvailableTwitterLanguage findLanguageByLongName(String longName) {
		for (AvailableTwitterLanguage availableLanguage : availableLanguagesList) {
			if (longName.equals(availableLanguage.getLongName())) {
				return availableLanguage;
			}
		}
		return null;
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
	public void setCustomStopWordsListNameColumn(TableColumn<CustomStopWordsList, String> customStopWordsListNameColumn) {
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
	
}
