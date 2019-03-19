/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * @author jose
 *
 */
public class CreateTimelineReportServerTaskSelectTypeDialogControl {
	private MainApplication mainApplication;
	private Stage dialogStage;
	@FXML
	private ToggleButton timelineVolumeReportButton;
	@FXML
	private ToggleButton othersButton;
	private ToggleGroup toggleGroup = new ToggleGroup();
	private String toReturn;
	
	public CreateTimelineReportServerTaskSelectTypeDialogControl() {
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
		timelineVolumeReportButton.setToggleGroup(toggleGroup);
		othersButton.setToggleGroup(toggleGroup);
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
	@FXML
    public void onCancel() {
    	this.dialogStage.close();
    }
	
    /**
	 * @return the timelineVolumeReportButton
	 */
	public ToggleButton getTimelineVolumeReportButton() {
		return timelineVolumeReportButton;
	}

	/**
	 * @param timelineVolumeReportButton the timelineVolumeReportButton to set
	 */
	public void setTimelineVolumeReportButton(ToggleButton timelineVolumeReportButton) {
		this.timelineVolumeReportButton = timelineVolumeReportButton;
	}

	/**
	 * @return the othersButton
	 */
	public ToggleButton getOthersButton() {
		return othersButton;
	}

	/**
	 * @param othersButton the othersButton to set
	 */
	public void setOthersButton(ToggleButton othersButton) {
		this.othersButton = othersButton;
	}

	/**
	 * @return the toReturn
	 */
	public String getToReturn() {
		return toReturn;
	}

	/**
	 * @param toReturn the toReturn to set
	 */
	public void setToReturn(String toReturn) {
		this.toReturn = toReturn;
	}

	@FXML 
    public void onNext() {
    	if(toggleGroup.getSelectedToggle().equals(timelineVolumeReportButton)) {
    		setToReturn(Constants.TWEET_VOLUME_TIMELINE_REPORT);
    		this.dialogStage.close();
    		return;
    	}else if(toggleGroup.getSelectedToggle().equals(othersButton)) {
    		setToReturn(Constants.OTHER_TIMELINE_REPORT);
    		this.dialogStage.close();
    		return;
    	}
    	ErrorDialog.showErrorSelectTaskType();
    }
}
