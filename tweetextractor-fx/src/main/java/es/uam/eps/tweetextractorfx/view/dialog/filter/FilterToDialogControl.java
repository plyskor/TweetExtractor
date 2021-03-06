package es.uam.eps.tweetextractorfx.view.dialog.filter;

import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractor.model.filter.impl.FilterTo;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FilterToDialogControl extends TweetExtractorFXDialogController{
	private FilterTo filter;
	@FXML
	private TextField nickToAdd;

	public FilterToDialogControl() {
		initialize();
	}
	private void initialize() {
		filter=new FilterTo();
	}
	/**
	 * @return the filter
	 */
	public FilterTo getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterTo filter) {
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
			ErrorDialog.showErrorEmptyNicknameTo();
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
