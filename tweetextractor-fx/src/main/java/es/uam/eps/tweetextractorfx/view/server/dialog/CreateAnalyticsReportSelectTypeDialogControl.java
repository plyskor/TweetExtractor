/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * @author jgarciadelsaz
 *
 */
public class CreateAnalyticsReportSelectTypeDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private ToggleButton timelineReportChoice;
	@FXML
	private ToggleButton trendsReportChoice;
	private ToggleGroup toggleGroup = new ToggleGroup();
	/**
	 * 
	 */
	public CreateAnalyticsReportSelectTypeDialogControl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		timelineReportChoice.setToggleGroup(toggleGroup);
		trendsReportChoice.setToggleGroup(toggleGroup);
		trendsReportChoice.setSelected(true);
	}
	@FXML
	public void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	public void onNext() {
		if(toggleGroup.getSelectedToggle().equals(timelineReportChoice)) {
			getResponse().setStringValue("TR");
		}else if(toggleGroup.getSelectedToggle().equals(trendsReportChoice)) {
			getResponse().setStringValue("TRR");
		}
		this.dialogStage.close();
	}
	/**
	 * @return the timelineReportChoice
	 */
	public ToggleButton getTimelineReportChoice() {
		return timelineReportChoice;
	}
	/**
	 * @param timelineReportChoice the timelineReportChoice to set
	 */
	public void setTimelineReportChoice(ToggleButton timelineReportChoice) {
		this.timelineReportChoice = timelineReportChoice;
	}
	/**
	 * @return the trendsReportChoice
	 */
	public ToggleButton getTrendsReportChoice() {
		return trendsReportChoice;
	}
	/**
	 * @param trendsReportChoice the trendsReportChoice to set
	 */
	public void setTrendsReportChoice(ToggleButton trendsReportChoice) {
		this.trendsReportChoice = trendsReportChoice;
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

}
