/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.filter;

import es.uam.eps.tweetextractor.model.filter.impl.FilterContains;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class FilterContainsDialogControl extends TweetExtractorFXDialogController{
	@FXML
	private ListView<String> selectedWordsView;
	@FXML
	private TextField wordToAdd;
    private FilterContains filter;
    private ObservableList<String> keywordsList=FXCollections.observableArrayList();
	/**
	 * 
	 */
	public FilterContainsDialogControl() {
		initialize();
	}

	/**
	 * @return the filter
	 */
	public FilterContains getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterContains filter) {
		this.filter = filter;
	}
	/**
	 * @param dialogStage the dialogStage to set
	 */
	@Override
	public void setDialogStage(Stage dialogStage) {
		super.setDialogStage(dialogStage);
		selectedWordsView.setItems(keywordsList);
	}

	/**
	 * @return the wordToAdd
	 */
	public TextField getWordToAdd() {
		return wordToAdd;
	}

	/**
	 * @param wordToAdd the wordToAdd to set
	 */
	public void setWordToAdd(TextField wordToAdd) {
		this.wordToAdd = wordToAdd;
	}

	/**
	 * @return the selectedWordsView
	 */
	public ListView<String> getSelectedWordsView() {
		return selectedWordsView;
	}

	/**
	 * @param selectedWordsView the selectedWordsView to set
	 */
	public void setSelectedWordsView(ListView<String> selectedWordsView) {
		this.selectedWordsView = selectedWordsView;
	}

	private void initialize() {
		filter= new FilterContains();
		filter.getKeywordsList().clear();
	}
	@FXML
	public void handleAddWord() {
		if (wordToAdd.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Información");
	    	alert.setHeaderText("Ningúna palabra que añadir");
	    	alert.setContentText("Por favor, escriba una o varias palabras para añadirlas al filtro.");
	    	alert.showAndWait();
		}else {
			String[] wordsToAdd =wordToAdd.getText().replaceAll("^[,\\s]+", "").split("[,\\s]+");
			for(String word : wordsToAdd) {
				if(!filter.getKeywordsList().contains(word))filter.addKeywordWord(word);
			}
			wordToAdd.clear();
			refreshKeywordList();
		}
	}
	private void refreshKeywordList() {
		if(filter!=null&&this.filter.getKeywordsList()!=null) {
			keywordsList.setAll(filter.getKeywordsList());
		}
		
	}

	@FXML
	public void handleCancel() {
		this.filter=null;
		dialogStage.close();
	}
	@FXML
	public void handleDone() {
		dialogStage.close();
	}

	public ObservableList<String> getKeywordsList() {
		return keywordsList;
	}

	public void setKeywordsList(ObservableList<String> keywordsList) {
		this.keywordsList = keywordsList;
	}

}
