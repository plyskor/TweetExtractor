/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERConfigurationServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.SelectNERPreferencesDialogControlResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author jgarciadelsaz
 *
 */
public class SelectNERPreferencesDialogControl extends TweetExtractorFXDialogController {
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
	private Logger logger = LoggerFactory.getLogger(SelectNERPreferencesDialogControl.class);
	
	public SelectNERPreferencesDialogControl() {
		super();
		setResponse(new SelectNERPreferencesDialogControlResponse());
		getResponse().setIntValue(Constants.ERROR);
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
		if(selectedPreferences==null) {
			ErrorDialog.showErrorNoSelectedNERPreferences();
			return;
		}
		getResponse().setIntValue(Constants.SUCCESS);
		((SelectNERPreferencesDialogControlResponse) getResponse()).setPreferences(selectedPreferences);
		this.dialogStage.close();
	}
	@FXML
	private void onCancel() {
		this.dialogStage.close();
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
	 * @return the pServ
	 */
	public TweetExtractorNERConfigurationServiceInterface getpServ() {
		return pServ;
	}

	/**
	 * @param pServ the pServ to set
	 */
	public void setpServ(TweetExtractorNERConfigurationServiceInterface pServ) {
		this.pServ = pServ;
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
	private AvailableTwitterLanguage findLanguageByLongName(String longName) {
		for (AvailableTwitterLanguage availableLanguage : availableLanguagesList) {
			if (longName.equals(availableLanguage.getLongName())) {
				return availableLanguage;
			}
		}
		return null;
	}
	
}
