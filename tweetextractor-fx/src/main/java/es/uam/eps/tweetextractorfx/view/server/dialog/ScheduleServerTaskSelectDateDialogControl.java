/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

/**
 * @author jose
 *
 */
public class ScheduleServerTaskSelectDateDialogControl {
	private MainApplication mainApplication;
	private Stage dialogStage;
	private Date toReturn;
	@FXML
	private StackPane datePickerPane;
	private DateTimePicker datePicker = new DateTimePicker();
	/**
	 * 
	 */
	public ScheduleServerTaskSelectDateDialogControl() {
		super();
	}
	/**
	 * @return the mainApplication
	 */
	public MainApplication getMainApplication() {
		return mainApplication;
	}
	/**
	 * @param mainApplication the mainApplication to set
	 */
	public void setMainApplication(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
		datePickerPane.getChildren().add(datePicker);
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
	 * @return the toReturn
	 */
	public Date getToReturn() {
		return toReturn;
	}
	/**
	 * @param toReturn the toReturn to set
	 */
	public void setToReturn(Date toReturn) {
		this.toReturn = toReturn;
	}
	/**
	 * @return the datePickerPane
	 */
	public StackPane getDatePickerPane() {
		return datePickerPane;
	}
	/**
	 * @param datePickerPane the datePickerPane to set
	 */
	public void setDatePickerPane(StackPane datePickerPane) {
		this.datePickerPane = datePickerPane;
	}
	/**
	 * @return the datePicker
	 */
	public DateTimePicker getDatePicker() {
		return datePicker;
	}
	/**
	 * @param datePicker the datePicker to set
	 */
	public void setDatePicker(DateTimePicker datePicker) {
		this.datePicker = datePicker;
	}
	@FXML
	public void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	public void onDone() {
		if(datePicker.getEditor().getText().isEmpty()) {
			ErrorDialog.showErrorNoSelectedDateTime();
			return;
		}
		LocalDateTime result = datePicker.getDateTimeValue();
		Date date = convertToDateViaInstant(result);
		if(date.before(new Date())) {
			ErrorDialog.showErrorTimeAlreadyPassed();
			return;
		}
		setToReturn(date);
		this.dialogStage.close();
	}
	public Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant());
	}
}
