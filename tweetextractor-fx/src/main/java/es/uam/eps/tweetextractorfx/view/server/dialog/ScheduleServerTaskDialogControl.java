/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * @author jose
 *
 */
public class ScheduleServerTaskDialogControl extends TweetExtractorFXDialogController{
	@FXML
	private ToggleButton givenDateTimeButton;
	@FXML
	private ToggleButton delayButton;
	@FXML
	private ToggleButton periodicallyButton;
	private ToggleGroup toggleGroup = new ToggleGroup();
	private int toReturn;

	/**
	 * 
	 */
	public ScheduleServerTaskDialogControl() {
		super();
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		givenDateTimeButton.setToggleGroup(toggleGroup);
		delayButton.setToggleGroup(toggleGroup);
		periodicallyButton.setToggleGroup(toggleGroup);
	}

	/**
	 * @return the givenDateTimeButton
	 */
	public ToggleButton getGivenDateTimeButton() {
		return givenDateTimeButton;
	}

	/**
	 * @param givenDateTimeButton the givenDateTimeButton to set
	 */
	public void setGivenDateTimeButton(ToggleButton givenDateTimeButton) {
		this.givenDateTimeButton = givenDateTimeButton;
	}

	/**
	 * @return the delayButton
	 */
	public ToggleButton getDelayButton() {
		return delayButton;
	}

	/**
	 * @param delayButton the delayButton to set
	 */
	public void setDelayButton(ToggleButton delayButton) {
		this.delayButton = delayButton;
	}

	/**
	 * @return the periodicallyButton
	 */
	public ToggleButton getPeriodicallyButton() {
		return periodicallyButton;
	}

	/**
	 * @param periodicallyButton the periodicallyButton to set
	 */
	public void setPeriodicallyButton(ToggleButton periodicallyButton) {
		this.periodicallyButton = periodicallyButton;
	}

	/**
	 * @return the toggleGroup
	 */
	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}

	/**
	 * @param toggleGroup the toggleGroup to set
	 */
	public void setToggleGroup(ToggleGroup toggleGroup) {
		this.toggleGroup = toggleGroup;
	}

	/**
	 * @return the toReturn
	 */
	public int getToReturn() {
		return toReturn;
	}

	/**
	 * @param toReturn the toReturn to set
	 */
	public void setToReturn(int toReturn) {
		this.toReturn = toReturn;
	}

	@FXML
	public void onCancel() {
		this.dialogStage.close();
	}

	@FXML
	public void onNext() {
		if (toggleGroup.getSelectedToggle().equals(givenDateTimeButton)) {
			setToReturn(Constants.SCHEDULE_GIVEN_DATE_TIME);
			this.dialogStage.close();
			return;
		} else if (toggleGroup.getSelectedToggle().equals(delayButton)) {
			setToReturn(Constants.SCHEDULE_DELAY);
			this.dialogStage.close();
			return;
		} else if (toggleGroup.getSelectedToggle().equals(periodicallyButton)) {
			setToReturn(Constants.SCHEDULE_PERIODICALLY);
			this.dialogStage.close();
			return;
		}
		ErrorDialog.showErrorSelectTaskType();
	}
}
