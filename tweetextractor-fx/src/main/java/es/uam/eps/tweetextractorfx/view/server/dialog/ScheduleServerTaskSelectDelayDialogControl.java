/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import java.util.Calendar;
import java.util.Date;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * @author jose
 *
 */
public class ScheduleServerTaskSelectDelayDialogControl extends TweetExtractorFXDialogController{
	private Date toReturn=null;
	@FXML
	private Slider secondsSlider;
	@FXML
	private Slider minutesSlider;
	@FXML
	private Slider hoursSlider;
	@FXML
	private Slider daysSlider;
	@FXML
	private Slider monthsSlider;
	@FXML
	private Slider yearsSlider;
	@FXML
	private Text secondsText;
	@FXML
	private Text minutesText;
	@FXML
	private Text hoursText;
	@FXML
	private Text daysText;
	@FXML
	private Text monthsText;
	@FXML
	private Text yearsText;
	/**
	 * 
	 */
	public ScheduleServerTaskSelectDelayDialogControl() {
		super();
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		secondsSlider.valueProperty().addListener((observable, oldValue, newValue) -> secondsText.setText(""+newValue.intValue()));
		minutesSlider.valueProperty().addListener((observable, oldValue, newValue) -> minutesText.setText(""+newValue.intValue()));
		hoursSlider.valueProperty().addListener((observable, oldValue, newValue) -> hoursText.setText(""+newValue.intValue()));
		daysSlider.valueProperty().addListener((observable, oldValue, newValue) -> daysText.setText(""+newValue.intValue()));
		monthsSlider.valueProperty().addListener((observable, oldValue, newValue) -> monthsText.setText(""+newValue.intValue()));
		yearsSlider.valueProperty().addListener((observable, oldValue, newValue) -> yearsText.setText(""+newValue.intValue()));
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
	@FXML
	public void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	public void onNext() {
		Date desiredDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(desiredDate);
		calendar.add(Calendar.SECOND, (int)secondsSlider.getValue());
		calendar.add(Calendar.MINUTE, (int)minutesSlider.getValue());
		calendar.add(Calendar.HOUR, (int)hoursSlider.getValue());
		calendar.add(Calendar.DATE, (int)daysSlider.getValue());
		calendar.add(Calendar.MONTH, (int)monthsSlider.getValue());
		calendar.add(Calendar.YEAR, (int)yearsSlider.getValue());
		desiredDate= calendar.getTime();
		if (desiredDate.before(new Date())) {
			ErrorDialog.showErrorScheduleServerTask("Select a date from the future.");
			return;
		}
		this.setToReturn(desiredDate);
		this.dialogStage.close();
	}
}
