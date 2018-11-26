/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.filter;

import es.uam.eps.tweetextractor.model.filter.impl.FilterContainsExact;
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
public class FilterContainsExactDialogControl {
	@FXML
	private ListView<String> selectedWordsView;
	@FXML
	private TextField wordToAdd;
	private ObservableList<String> listWords= FXCollections.observableArrayList();
    private FilterContainsExact filter;
    private Stage dialogStage;
	/**
	 * 
	 */
	public FilterContainsExactDialogControl() {
		initialize();
	}

	/**
	 * @return the filter
	 */
	public FilterContainsExact getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterContainsExact filter) {
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
		selectedWordsView.setItems(listWords);
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
		filter= new FilterContainsExact();
		filter.getKeywordsList().clear();
	}
	@FXML
	public void handleAddWord() {
		if (wordToAdd.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Información");
	    	alert.setHeaderText("Ningúna palabra que añadir");
	    	alert.setContentText("Por favor, escriba una palabra para añadirla al filtro.");
	    	alert.showAndWait();
		}else {
			if(wordToAdd!=null) {
				filter.addKeywordWord(wordToAdd.getText().trim());
				refreshWordList();
				wordToAdd.clear();
				}
		}
	}
	private void refreshWordList() {
		if(filter!=null&&this.filter.getKeywordsList()!=null) {
			listWords.setAll(filter.getKeywordsList());
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

}
