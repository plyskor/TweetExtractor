/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

/**
 * @author jose
 *
 */
public class CreateAnalyticsServerTaskSelectTypeDialogControl extends TweetExtractorFXDialogController{
    @FXML
    private ChoiceBox<String> taskTypeChoice;
    private ObservableList<String> taskTypesList = FXCollections.observableArrayList();
    private String toReturn;
	/**
	 * @param mainApplication the mainApplication to set
	 */
   @Override 
	public void setMainApplication(MainApplication mainApplication) {
	   super.setMainApplication(mainApplication);
		taskTypeChoice.setItems(taskTypesList);
		initializeTaskTypeChoice();
	}
	private void initializeTaskTypeChoice() {
		/*Initialize available types of tasks*/
		taskTypesList.add(Constants.TIMELINE_REPORT_SERVER_TASK_TYPE);		
	}
	/**
	 * @return the taskTypeChoice
	 */
	public ChoiceBox<String> getTaskTypeChoice() {
		return taskTypeChoice;
	}
	/**
	 * @param taskTypeChoice the taskTypeChoice to set
	 */
	public void setTaskTypeChoice(ChoiceBox<String> taskTypeChoice) {
		this.taskTypeChoice = taskTypeChoice;
	}
	/**
	 * @return the taskTypesList
	 */
	public ObservableList<String> getTaskTypesList() {
		return taskTypesList;
	}
	/**
	 * @param taskTypesList the taskTypesList to set
	 */
	public void setTaskTypesList(ObservableList<String> taskTypesList) {
		this.taskTypesList = taskTypesList;
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
    public void onCancel() {
    	this.dialogStage.close();
    }
    @FXML 
    public void onNext() {
    	if(taskTypeChoice.getValue()==null||taskTypeChoice.getValue().isEmpty()) {
    		ErrorDialog.showErrorSelectTaskType();
    	}else {
    		setToReturn(taskTypeChoice.getValue());
    		this.dialogStage.close();
    	}
    }
    
}
