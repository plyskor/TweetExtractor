/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.nlp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import es.uam.eps.tweetextractor.dao.service.inter.CustomStopWordsListServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.StopWordServiceInterface;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.reference.nlp.StopWord;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorfx.view.dialog.nlp.AddStopWordsToListDialogControl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public class EditCustomStopWordsListControl extends TweetExtractorFXController {
	@FXML
	private TableView<StopWord> stopWordsTable;
	@FXML
	private TableColumn<StopWord, String> stopWordsColumn;
	@FXML
	private Text titleText;
	@FXML 
	private TextField listNameTextField;
	private ObservableList<StopWord> stopWordsList=FXCollections.observableArrayList();
	private StopWord selectedStopWord = null;
	private CustomStopWordsList listInput;
	private StopWordServiceInterface swServ;
	private CustomStopWordsListServiceInterface swlServ;
	private Logger logger = LoggerFactory.getLogger(EditCustomStopWordsListControl.class);

	/**
	 * 
	 */
	public EditCustomStopWordsListControl() {
		super();
	}
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		stopWordsTable.setItems(stopWordsList);
		swlServ=this.getMainApplication().getSpringContext().getBean(CustomStopWordsListServiceInterface.class);
		swServ=this.getMainApplication().getSpringContext().getBean(StopWordServiceInterface.class);
		loadDataFromList();
	}
	private void loadDataFromList() {
		/*Initialize observable list*/
		stopWordsList.clear();
		stopWordsList.addAll(listInput.getList());
		listNameTextField.setText(listInput.getName());
		titleText.setText("Stop Words List: "+listInput.getName());
	}
	@FXML
	private void initialize () {
		stopWordsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStopWord()));
		stopWordsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setSelectedStopWord(newValue));
	}
	@FXML
	private void onAddWords () {
		listInput=showAddStopWordsToListDialog();
		loadDataFromList();
	}
	@FXML
	private void onDelete () {
		if(selectedStopWord==null) {
			ErrorDialog.showErrorNoSelectedStopWord();
		}else{
			listInput.getList().remove(selectedStopWord);
			swServ.delete(selectedStopWord);
			loadDataFromList();
		}
	}
	@FXML
	private void onBack () {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/ManageCustomStopWordsList.fxml");
	}
	@FXML
	private void onSaveName () {
		if(StringUtils.isBlank(listNameTextField.getText())) {
			ErrorDialog.showErrorEmptyStopWordsListName();
		}else {
			CustomStopWordsListID backUpID = new CustomStopWordsListID(listInput.getLanguage(), listInput.getUser(), listInput.getName());
			listInput.setName(listNameTextField.getText());
			if(swlServ.existsAny(listInput.getIdentifier())) {
				listInput.setName(backUpID.getName());
				ErrorDialog.showErrorCustomStopWordsListNameExists();
			}else {
				swlServ.saveOrUpdate(listInput);
				swlServ.deleteById(backUpID);
				loadDataFromList();
			}
		}
	}
	/**
	 * @return the stopWordsTable
	 */
	public TableView<StopWord> getStopWordsTable() {
		return stopWordsTable;
	}
	/**
	 * @param stopWordsTable the stopWordsTable to set
	 */
	public void setStopWordsTable(TableView<StopWord> stopWordsTable) {
		this.stopWordsTable = stopWordsTable;
	}
	/**
	 * @return the stopWordsColumn
	 */
	public TableColumn<StopWord, String> getStopWordsColumn() {
		return stopWordsColumn;
	}
	/**
	 * @param stopWordsColumn the stopWordsColumn to set
	 */
	public void setStopWordsColumn(TableColumn<StopWord, String> stopWordsColumn) {
		this.stopWordsColumn = stopWordsColumn;
	}
	/**
	 * @return the stopWordsList
	 */
	public ObservableList<StopWord> getStopWordsList() {
		return stopWordsList;
	}
	/**
	 * @param stopWordsList the stopWordsList to set
	 */
	public void setStopWordsList(ObservableList<StopWord> stopWordsList) {
		this.stopWordsList = stopWordsList;
	}
	/**
	 * @return the selectedStopWord
	 */
	public StopWord getSelectedStopWord() {
		return selectedStopWord;
	}
	/**
	 * @param selectedStopWord the selectedStopWord to set
	 */
	public void setSelectedStopWord(StopWord selectedStopWord) {
		this.selectedStopWord = selectedStopWord;
	}
	/**
	 * @return the titleText
	 */
	public Text getTitleText() {
		return titleText;
	}
	/**
	 * @param titleText the titleText to set
	 */
	public void setTitleText(Text titleText) {
		this.titleText = titleText;
	}
	/**
	 * @return the listInput
	 */
	public CustomStopWordsList getListInput() {
		return listInput;
	}
	/**
	 * @param listInput the listInput to set
	 */
	public void setListInput(CustomStopWordsList listInput) {
		this.listInput = listInput;
	}
	/**
	 * @return the listNameTextField
	 */
	public TextField getListNameTextField() {
		return listNameTextField;
	}
	/**
	 * @param listNameTextField the listNameTextField to set
	 */
	public void setListNameTextField(TextField listNameTextField) {
		this.listNameTextField = listNameTextField;
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
	public CustomStopWordsList showAddStopWordsToListDialog() {
		// Load the fxml file and create a new stage for the popup dialog.
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					MainApplication.class.getResource("view/dialog/nlp/AddStopWordsToListDialog.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.initOwner(this.getMainApplication().getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Adding stop words");
			// Set the dialogStage to the controller.
			AddStopWordsToListDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setInputList(listInput);
			controller.setMainApplication(mainApplication);
			dialogStage.showAndWait();
			// Show the dialog and wait until the user closes it, then add filter
			return controller.getInputList();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
