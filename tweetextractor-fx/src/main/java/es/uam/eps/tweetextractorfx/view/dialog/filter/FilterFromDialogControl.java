package es.uam.eps.tweetextractorfx.view.dialog.filter;

import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractor.model.filter.impl.FilterFrom;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FilterFromDialogControl extends TweetExtractorFXDialogController{
	private FilterFrom filter;
	@FXML
	private TextField nickToAdd;

	public FilterFromDialogControl() {
		initialize();
	}
	private void initialize() {
		filter=new FilterFrom();
	}
	/**
	 * @return the filter
	 */
	public FilterFrom getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterFrom filter) {
		this.filter = filter;
	}
	/**
	 * @return the nickToAdd
	 */
	public TextField getNickToAdd() {
		return nickToAdd;
	}
	/**
	 * @param nickToAdd the nickToAdd to set
	 */
	public void setNickToAdd(TextField nickToAdd) {
		this.nickToAdd = nickToAdd;
	}
	public void handleAddNick() {
		if (nickToAdd.getText().trim().isEmpty()) {
			ErrorDialog.showErrorEmptyNickname();
		}else {
			filter.setNickName(nickToAdd.getText().trim().replace("@", ""));
			this.getDialogStage().close();
		}
		
	}
	public void handelCancel() {
		this.filter=null;
		this.getDialogStage().close();
	}
	
}
