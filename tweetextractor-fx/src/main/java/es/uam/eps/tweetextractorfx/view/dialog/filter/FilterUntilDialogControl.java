package es.uam.eps.tweetextractorfx.view.dialog.filter;

import java.time.LocalDate;

import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.model.filter.impl.FilterUntil;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class FilterUntilDialogControl {
    private FilterUntil filter;
    private Stage dialogStage;
    @FXML
    private DatePicker datePicker = new DatePicker(LocalDate.now());
	public FilterUntilDialogControl() {
		initialize();
	}
	private void initialize() {
		filter= new FilterUntil();
	}
	/**
	 * @return the filter
	 */
	public FilterUntil getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(FilterUntil filter) {
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
			ErrorDialog.showErrorSelectDateTo();
		}else {
			filter.setDate(datePicker.getValue());
			dialogStage.close();
		}
	}

}
