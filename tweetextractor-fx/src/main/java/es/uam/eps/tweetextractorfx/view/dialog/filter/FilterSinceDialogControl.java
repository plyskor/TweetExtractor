package es.uam.eps.tweetextractorfx.view.dialog.filter;

import java.time.LocalDate;

import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractor.model.filter.impl.FilterSince;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class FilterSinceDialogControl extends TweetExtractorFXDialogController{
    private FilterSince filter;
    @FXML
    private DatePicker datePicker = new DatePicker(LocalDate.now());
	public FilterSinceDialogControl() {
		initialize();
	}
	private void initialize() {
		filter= new FilterSince();
	}
	/**
	 * @return the filter
	 */
	public FilterSince getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterSince filter) {
		this.filter = filter;
	}
	/**
	 * @return the datePicker
	 */
	public DatePicker getDatePicker() {
		return datePicker;
	}
	/**
	 * @param datePicker the datePicker to set
	 */
	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}
	@FXML
	public void handleCancel() {
		this.filter=null;
		dialogStage.close();
	}
	@FXML
	public void handleDone() {
		if(datePicker.getValue()==null) {
			ErrorDialog.showErrorSelectDateSince();
		}else {
			filter.setDate(datePicker.getValue());
			dialogStage.close();
		}
	}
	
}
