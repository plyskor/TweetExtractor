/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.nlp;


import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;

import es.uam.eps.tweetextractor.dao.service.inter.CustomStopWordsListServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.reference.nlp.StopWord;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public class CreateCustomStopWordsListDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private TextField listNameTextField;
	@FXML
	private CheckBox initializeListCheckBox;
	private AvailableTwitterLanguage language;
	private CustomStopWordsListServiceInterface swlServ;
	private CustomStopWordsList newList = new CustomStopWordsList();
	/**
	 * 
	 */
	public CreateCustomStopWordsListDialogControl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		swlServ=this.getMainApplication().getSpringContext().getBean(CustomStopWordsListServiceInterface.class);
	}
	@FXML
	private void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	private void onDone() {
		if(listNameTextField==null||listNameTextField.getText().isBlank()) {
			ErrorDialog.showErrorEmptyStopWordsListName();
		}else {
			newList.setLanguage(language);
			newList.setUser(this.getMainApplication().getCurrentUser());
			newList.setName(listNameTextField.getText());
			if(swlServ.existsAny(newList.getIdentifier())) {
				ErrorDialog.showErrorCustomStopWordsListNameExists();
				return;
			}
			if(initializeListCheckBox.isSelected()) {
				addDefaultStopSet();
			}
			swlServ.persist(newList);
			this.dialogStage.close();
		}
	}
	private void addDefaultStopSet() {
		CharArraySet arraySet = null;
		switch(language.getLongName()) {
		case("English"):
			arraySet = EnglishAnalyzer.getDefaultStopSet();
			break;
		case("Spanish"):
			arraySet = SpanishAnalyzer.getDefaultStopSet();
			break;
		case("German"):
			arraySet = GermanAnalyzer.getDefaultStopSet();
			break;
		case("French"):
			arraySet = FrenchAnalyzer.getDefaultStopSet();
			break;
		case("Italian"):
			arraySet = ItalianAnalyzer.getDefaultStopSet();
			break;
		case("Arabic"):
			arraySet = ArabicAnalyzer.getDefaultStopSet();
			break;
		default:
			break;
		}
		for(Object defaultWord : arraySet.toArray() ) {
			char[] charWord = (char[]) defaultWord;
			String word = new String(charWord);
			StopWord newWord = new StopWord();
			newWord.setStopWord(word);
			newWord.setList(newList);
			newList.getList().add(newWord);
		}	
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
	 * @return the initializeListCheckBox
	 */
	public CheckBox getInitializeListCheckBox() {
		return initializeListCheckBox;
	}
	/**
	 * @param initializeListCheckBox the initializeListCheckBox to set
	 */
	public void setInitializeListCheckBox(CheckBox initializeListCheckBox) {
		this.initializeListCheckBox = initializeListCheckBox;
	}
	/**
	 * @return the language
	 */
	public AvailableTwitterLanguage getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(AvailableTwitterLanguage language) {
		this.language = language;
		if (Constants.LANGUAGE_AVAILABLE_DEFAULT_STOP_SET.contains(language.getLongName())) {
			initializeListCheckBox.setDisable(false);
			initializeListCheckBox.setSelected(true);
		}
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
	 * @return the newList
	 */
	public CustomStopWordsList getNewList() {
		return newList;
	}

	/**
	 * @param newList the newList to set
	 */
	public void setNewList(CustomStopWordsList newList) {
		this.newList = newList;
	}

}
