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
public class CreateTimelineReportServerTaskSelectTypeDialogControl extends TweetExtractorFXDialogController{
	@FXML
	private ToggleButton timelineVolumeReportButton;
	@FXML
	private ToggleButton timelineTopNHashtagsReportButton;
	@FXML
	private ToggleButton othersButton;
	private ToggleGroup toggleGroup = new ToggleGroup();
	private String toReturn;
	
	public CreateTimelineReportServerTaskSelectTypeDialogControl() {
		super();
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		timelineVolumeReportButton.setToggleGroup(toggleGroup);
		timelineTopNHashtagsReportButton.setToggleGroup(toggleGroup);
		othersButton.setToggleGroup(toggleGroup);
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
    		getResponse().setStringValue(Constants.TWEET_VOLUME_TIMELINE_REPORT);
    		this.dialogStage.close();
    		return;
    	}else if(toggleGroup.getSelectedToggle().equals(timelineTopNHashtagsReportButton)){
    		getResponse().setStringValue(Constants.TOP_N_HASHTAGS_VOLUME_TIMELINE_REPORT);
    		this.dialogStage.close();
    		return;
    	}else if(toggleGroup.getSelectedToggle().equals(othersButton)) {
    		getResponse().setStringValue(Constants.OTHER_TIMELINE_REPORT);
    		this.dialogStage.close();
    		return;
    	}
    	ErrorDialog.showErrorSelectTaskType();
    }

	/**
	 * @return the timelineTopNHashtagsReportButton
	 */
	public ToggleButton getTimelineTopNHashtagsReportButton() {
		return timelineTopNHashtagsReportButton;
	}

	/**
	 * @param timelineTopNHashtagsReportButton the timelineTopNHashtagsReportButton to set
	 */
	public void setTimelineTopNHashtagsReportButton(ToggleButton timelineTopNHashtagsReportButton) {
		this.timelineTopNHashtagsReportButton = timelineTopNHashtagsReportButton;
	}
	
}
