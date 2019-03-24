package es.uam.eps.tweetextractorfx.view.dialog.filter;

import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractor.model.filter.impl.FilterUrl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FilterUrlDialogControl extends TweetExtractorFXDialogController{
	private FilterUrl filter;
	@FXML
	private TextField keyWordField;

	public FilterUrlDialogControl() {
		initialize();
	}
	private void initialize() {
		filter=new FilterUrl();
	}
	/**
	 * @return the filter
	 */
	public FilterUrl getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterUrl filter) {
		this.filter = filter;
	}
	/**
	 * @return the keyWord
	 */
	public TextField getKeyWord() {
		return keyWordField;
	}
	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(TextField keyWord) {
		this.keyWordField = keyWord;
	}
	public void handleAddKeyWord() {
		if (keyWordField.getText().trim().isEmpty()) {
			ErrorDialog.showErrorEmptyUrl();
		}else {
			filter.setUrl(keyWordField.getText().trim());
			this.getDialogStage().close();
		}
		
	}
	
	public void handelCancel() {
		this.filter=null;
		this.getDialogStage().close();
	}
	
	
}
