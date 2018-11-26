/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.filter;

import es.uam.eps.tweetextractor.model.filter.impl.FilterHashtag;
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
public class FilterHashtagDialogControl {
	@FXML
	private ListView<String> selectedWordsView;
	@FXML
	private TextField wordToAdd;
    private FilterHashtag filter;
    private Stage dialogStage;
    private ObservableList<String> hashtagList= FXCollections.observableArrayList();
	/**
	 * 
	 */
	public FilterHashtagDialogControl() {
		initialize();
	}

	/**
	 * @return the filter
	 */
	public FilterHashtag getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterHashtag filter) {
		this.filter = filter;
	}

	/**
	 * @return the dialogStage
	 */
	public Stage getDialogStage() {
		return dialogStage;
	}

	/**
	 * @param dialogStage the dialogStage to set
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		selectedWordsView.setItems(hashtagList);
		
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
		filter= new FilterHashtag();
		filter.getHashtagList().clear();
	}
	@FXML
	public void handleAddWord() {
		if (wordToAdd.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Información");
	    	alert.setHeaderText("Ningún hashtag que añadir");
	    	alert.setContentText("Por favor, escriba uno o varios hashtags para añadirlos al filtro.");
	    	alert.showAndWait();
		}else {
			String[] wordsToAdd =wordToAdd.getText().replaceAll("^[,\\s]+", "").split("[,\\s]+");
			for(String word : wordsToAdd) {
				word=word.replace("#", "");
				if(!filter.getHashtagList().contains(word))filter.addHashtag(word);
			}
			wordToAdd.clear();
			refreshHashtagList();
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
	private void refreshHashtagList() {
		if(filter!=null&&this.filter.getHashtagList()!=null) {
			hashtagList.setAll(filter.getHashtagList());
		}
		
	}
}
