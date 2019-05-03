/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.nlp;

import org.apache.commons.lang3.StringUtils;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.StopWordServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.analytics.nlp.StopWord;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public class AddStopWordsToListDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private ListView<String> listView;
	@FXML 
	private TextField textField;
	private ObservableList<String> wordsList=FXCollections.observableArrayList();
	private StopWordServiceInterface swServ;
	private CustomStopWordsList inputList;
	public AddStopWordsToListDialogControl() {
		super();
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		swServ= this.getMainApplication().getSpringContext().getBean(StopWordServiceInterface.class);
		listView.setItems(wordsList);
	}
	@FXML
	public void onAddWordsToList() {
		if (StringUtils.isBlank(textField.getText().trim())) {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Information");
	    	alert.setHeaderText("No stop words given");
	    	alert.setContentText("Please, give at least one stop word to add to the list");
	    	alert.showAndWait();
		}else {
			String[] wordsToAdd =textField.getText().replaceAll("^[,\\s]+", "").split("[,\\s]+");
			for(String word : wordsToAdd) {
				if(!isAlreadyPresent(word)) {
					wordsList.add(word);
				}
			}
			textField.clear();
		}
	}
	private boolean isAlreadyPresent(String word) {
		for (StopWord existingWord: inputList.getList()) {
			if (existingWord.getStopWord().equals(word)) {
				return true;
			}
		}
		return false;
	}
	@FXML
	public void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	public void onDone() {
		for(String addedWord:wordsList ) {
			StopWord newWord = new StopWord();
			newWord.setStopWord(addedWord);
			newWord.setList(inputList);
			inputList.getList().add(newWord);
			swServ.persist(newWord);
		}
		this.dialogStage.close();
	}
	/**
	 * @return the listView
	 */
	public ListView<String> getListView() {
		return listView;
	}

	/**
	 * @param listView the listView to set
	 */
	public void setListView(ListView<String> listView) {
		this.listView = listView;
	}

	/**
	 * @return the textField
	 */
	public TextField getTextField() {
		return textField;
	}

	/**
	 * @param textField the textField to set
	 */
	public void setTextField(TextField textField) {
		this.textField = textField;
	}

	/**
	 * @return the wordsList
	 */
	public ObservableList<String> getWordsList() {
		return wordsList;
	}

	/**
	 * @param wordsList the wordsList to set
	 */
	public void setWordsList(ObservableList<String> wordsList) {
		this.wordsList = wordsList;
	}

	/**
	 * @return the swServ
	 */
	public StopWordServiceInterface getSwServ() {
		return swServ;
	}

	/**
	 * @param swServ the swServ to set
	 */
	public void setSwServ(StopWordServiceInterface swServ) {
		this.swServ = swServ;
	}

	/**
	 * @return the inputList
	 */
	public CustomStopWordsList getInputList() {
		return inputList;
	}

	/**
	 * @param inputList the inputList to set
	 */
	public void setInputList(CustomStopWordsList inputList) {
		this.inputList = inputList;
	}
	
	
}
