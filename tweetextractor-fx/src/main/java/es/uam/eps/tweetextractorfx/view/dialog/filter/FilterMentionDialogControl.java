/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.filter;

import es.uam.eps.tweetextractor.model.filter.impl.FilterMention;
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
public class FilterMentionDialogControl {
	@FXML
	private ListView<String> selectedWordsView;
	@FXML
	private TextField wordToAdd;
	private ObservableList<String> mentionList=FXCollections.observableArrayList();
    private FilterMention filter;
    private Stage dialogStage;
	/**
	 * 
	 */
	public FilterMentionDialogControl() {
		initialize();
	}

	/**
	 * @return the filter
	 */
	public FilterMention getFilter() {
		return filter;
	}

	/**
	 * @return the mentionList
	 */
	public ObservableList<String> getMentionList() {
		return mentionList;
	}

	/**
	 * @param mentionList the mentionList to set
	 */
	public void setMentionList(ObservableList<String> mentionList) {
		this.mentionList = mentionList;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterMention filter) {
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
		selectedWordsView.setItems(mentionList);
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
		filter= new FilterMention();
		filter.getMentionList().clear();
	}
	@FXML
	public void handleAddWord() {
		if (wordToAdd.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Information");
	    	alert.setHeaderText("No mention to add");
	    	alert.setContentText("Please, write one or more twitter usernames to add to the filter.");
	    	alert.showAndWait();
		}else {
			String[] wordsToAdd =wordToAdd.getText().replaceAll("^[,\\s]+", "").split("[,\\s]+");
			for(String word : wordsToAdd) {
				word=word.replace("@", "");
				if(!filter.getMentionList().contains(word))filter.addMention(word);
			}
			wordToAdd.clear();
			refreshMentionList();
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
	private void refreshMentionList() {
		if(filter!=null&&this.filter.getMentionList()!=null) {
			mentionList.setAll(filter.getMentionList());
		}
		
	}
}
