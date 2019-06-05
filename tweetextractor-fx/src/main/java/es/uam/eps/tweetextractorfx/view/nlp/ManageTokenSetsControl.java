/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.nlp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERTokenSetServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.nlp.CreateTokenSetSelectNameDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.response.SelectExtractionFilterDialogResponse;
import es.uam.eps.tweetextractorfx.view.dialog.response.TweetExtractorFXDialogResponse;
import es.uam.eps.tweetextractorfx.view.server.dialog.SelectExtractionFilterDialogControl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
 * @author jgarciadelsaz
 *
 */
public class ManageTokenSetsControl extends TweetExtractorFXDialogController {
	@FXML
	private TableView<TweetExtractorNERTokenSet> tokenSetListTable;
	@FXML
	private TableColumn<TweetExtractorNERTokenSet, String> tokenSetNameColumn;
	@FXML
	private TableColumn<TweetExtractorNERTokenSet, Integer> tokenSetWordsNumberColumn;
	@FXML
	private TableColumn<TweetExtractorNERTokenSet, LocalDate> tokenSetLastUpdatedColumn;
	@FXML
	private ChoiceBox<String> languageChoiceBox;
	private ObservableList<TweetExtractorNERTokenSet> tableList = FXCollections.observableArrayList();
	private TweetExtractorNERTokenSet selectedTokenSet = null;
	private ReferenceAvailableLanguagesServiceInterface languageServ;
	private TweetExtractorNERTokenSetServiceInterface tsServ;
	private List<AvailableTwitterLanguage> availableLanguagesList = new ArrayList<>();
	private AvailableTwitterLanguage selectedAvailableTwitterLanguage = null;
	private Logger logger = LoggerFactory.getLogger(ManageTokenSetsControl.class);

	public ManageTokenSetsControl() {
		super();
	}

	@FXML
	public void initialize() {
		tokenSetNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentifier().getName()));
		tokenSetWordsNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTokenList().size()).asObject());
		tokenSetLastUpdatedColumn.setCellValueFactory(cellData -> {
			if(cellData.getValue().getLastUpdated()!=null) {
				return new SimpleObjectProperty<LocalDate>(cellData.getValue().getLastUpdated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			}
			return new SimpleObjectProperty<LocalDate>();
		});
		tokenSetListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setSelectedTokenSet(newValue));
		setSelectedTokenSet(null);
	}

	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		tokenSetListTable.setItems(tableList);
		languageServ = this.mainApplication.getSpringContext().getBean(ReferenceAvailableLanguagesServiceInterface.class);
		availableLanguagesList.addAll(languageServ.findAll());
		tsServ = this.mainApplication.getSpringContext().getBean(TweetExtractorNERTokenSetServiceInterface.class);
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
					loadTokenSetsList();
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

	private void loadTokenSetsList() {
		if (tsServ != null) {
			List<TweetExtractorNERTokenSet> result = tsServ.findByUserAndLanguage(
					this.getMainApplication().getCurrentUser(), selectedAvailableTwitterLanguage);
			tableList.clear();
			tableList.addAll(result);
		}
	}
	@FXML
	public void onDone() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/NLPPreferencesHome.fxml");
	}
	@FXML
	public void onNew() {
		SelectExtractionFilterDialogResponse extractionsChoice = showSelectExtractionFilterDialog();
		TweetExtractorFXDialogResponse nameChoice = showCreateTokenSetSelectNameDialog(selectedAvailableTwitterLanguage);
		if(extractionsChoice!=null&&nameChoice!=null&&extractionsChoice.getIntValue()==Constants.SUCCESS&&nameChoice.getIntValue()==Constants.SUCCESS) {
			TweetExtractorNERTokenSet newTokenSet = new TweetExtractorNERTokenSet();
			newTokenSet.getExtractions().addAll(extractionsChoice.getFilter());
			newTokenSet.getIdentifier().setUser(this.getMainApplication().getCurrentUser());
			newTokenSet.getIdentifier().setLanguage(selectedAvailableTwitterLanguage);
			newTokenSet.getIdentifier().setName(nameChoice.getStringValue());
			tsServ.persist(newTokenSet);
			loadTokenSetsList();
		}
		
	}

	@FXML
	public void onTokenize() {
		
	}
	@FXML
	public void onDelete() {
		if (selectedTokenSet == null) {
			ErrorDialog.showErrorNoSelectedStopWordsList();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"This action will delete the token set " + selectedTokenSet.getIdentifier().getName() + "("+selectedAvailableTwitterLanguage.getShortName()+")" 
							+ ", and also all its content. Are you sure you want to continue?",
					ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				tsServ.delete(selectedTokenSet);
				loadTokenSetsList();
			}
		}
	}
	public TableView<TweetExtractorNERTokenSet> getTokenSetListTable() {
		return tokenSetListTable;
	}

	public void setTokenSetListTable(TableView<TweetExtractorNERTokenSet> tokenSetListTable) {
		this.tokenSetListTable = tokenSetListTable;
	}

	public TableColumn<TweetExtractorNERTokenSet, String> getTokenSetNameColumn() {
		return tokenSetNameColumn;
	}

	public void setTokenSetNameColumn(TableColumn<TweetExtractorNERTokenSet, String> tokenSetNameColumn) {
		this.tokenSetNameColumn = tokenSetNameColumn;
	}

	public TableColumn<TweetExtractorNERTokenSet, Integer> getTokenSetWordsNumberColumn() {
		return tokenSetWordsNumberColumn;
	}

	public void setTokenSetWordsNumberColumn(
			TableColumn<TweetExtractorNERTokenSet, Integer> tokenSetWordsNumberColumn) {
		this.tokenSetWordsNumberColumn = tokenSetWordsNumberColumn;
	}

	public TableColumn<TweetExtractorNERTokenSet, LocalDate> getTokenSetLastUpdatedColumn() {
		return tokenSetLastUpdatedColumn;
	}

	public void setTokenSetLastUpdatedColumn(
			TableColumn<TweetExtractorNERTokenSet, LocalDate> tokenSetLastUpdatedColumn) {
		this.tokenSetLastUpdatedColumn = tokenSetLastUpdatedColumn;
	}

	public ChoiceBox<String> getLanguageChoiceBox() {
		return languageChoiceBox;
	}

	public void setLanguageChoiceBox(ChoiceBox<String> languageChoiceBox) {
		this.languageChoiceBox = languageChoiceBox;
	}

	public ObservableList<TweetExtractorNERTokenSet> getTableList() {
		return tableList;
	}

	public void setTableList(ObservableList<TweetExtractorNERTokenSet> tableList) {
		this.tableList = tableList;
	}

	public TweetExtractorNERTokenSet getSelectedTokenSet() {
		return selectedTokenSet;
	}

	public void setSelectedTokenSet(TweetExtractorNERTokenSet selectedTokenSet) {
		this.selectedTokenSet = selectedTokenSet;
	}

	public ReferenceAvailableLanguagesServiceInterface getLanguageServ() {
		return languageServ;
	}

	public void setLanguageServ(ReferenceAvailableLanguagesServiceInterface languageServ) {
		this.languageServ = languageServ;
	}

	public TweetExtractorNERTokenSetServiceInterface getTsServ() {
		return tsServ;
	}

	public void setTsServ(TweetExtractorNERTokenSetServiceInterface tsServ) {
		this.tsServ = tsServ;
	}

	public List<AvailableTwitterLanguage> getAvailableLanguagesList() {
		return availableLanguagesList;
	}

	public void setAvailableLanguagesList(List<AvailableTwitterLanguage> availableLanguagesList) {
		this.availableLanguagesList = availableLanguagesList;
	}

	public AvailableTwitterLanguage getSelectedAvailableTwitterLanguage() {
		return selectedAvailableTwitterLanguage;
	}

	public void setSelectedAvailableTwitterLanguage(AvailableTwitterLanguage selectedAvailableTwitterLanguage) {
		this.selectedAvailableTwitterLanguage = selectedAvailableTwitterLanguage;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	private SelectExtractionFilterDialogResponse showSelectExtractionFilterDialog() {
		TweetExtractorFXDialogResponse result = this.mainApplication.showDialogLoadFXML("view/server/dialog/SelectExtractionFilterDialog.fxml",SelectExtractionFilterDialogControl.class);
		if(result!=null) {
			return (SelectExtractionFilterDialogResponse) result;
		}
		return null;
	}
	private TweetExtractorFXDialogResponse showCreateTokenSetSelectNameDialog(AvailableTwitterLanguage language) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/dialog/nlp/CreateTokenSetSelectNameDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.getMainApplication().getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			CreateTokenSetSelectNameDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(this.getMainApplication());
			controller.setLanguage(language);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
}
