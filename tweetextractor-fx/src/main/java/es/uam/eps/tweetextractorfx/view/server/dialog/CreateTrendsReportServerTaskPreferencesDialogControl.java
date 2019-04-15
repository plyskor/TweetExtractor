/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import java.util.ArrayList;
import java.util.List;

import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTrendsReportServerTaskPreferencesDialogResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * @author jose
 *
 */
public class CreateTrendsReportServerTaskPreferencesDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private Slider nSlider;
	@FXML
	private Text nText;
	
	private List<String> filter = new ArrayList<>();
	@FXML
	private ListView<String> filterListView;
	@FXML
	private TextField filterToAdd;
	
	private ObservableList<String> filterList = FXCollections.observableArrayList();
	
	private String selectedFilter=null;
	@FXML
	private ToggleButton hashtagChoice;
	@FXML
	private ToggleButton userChoice;
	@FXML
	private ToggleButton userMentionChoice;
	@FXML
	private ToggleButton wordsChoice;
	
	private ToggleGroup toggleGroup = new ToggleGroup();

	public CreateTrendsReportServerTaskPreferencesDialogControl() {
		super();
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		nSlider.valueProperty().addListener((observable, oldValue, newValue) -> nText.setText("Limit to the top: "+newValue.intValue()));
		filterListView.setItems(filterList);
		this.setResponse(new CreateTrendsReportServerTaskPreferencesDialogResponse());
		hashtagChoice.setToggleGroup(toggleGroup);
		userChoice.setToggleGroup(toggleGroup);
		userMentionChoice.setToggleGroup(toggleGroup);
		wordsChoice.setToggleGroup(toggleGroup);
		hashtagChoice.setSelected(true);
	}

	@FXML
	public void initialize() {
		filterListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setSelectedFilter(newValue));
	}
	@FXML
	public void onCancel() {
		this.getResponse().setIntValue(-1);
		this.getDialogStage().close();
	}
	@FXML 
	public void onDone() {
		this.getFilter().addAll(filterList);
		this.getResponse().setIntValue((int)nSlider.getValue());
		AnalyticsReportTypes toReturn = null;
		if(hashtagChoice.equals(toggleGroup.getSelectedToggle())) {
			toReturn = AnalyticsReportTypes.TRHR;
		} else if(userChoice.equals(toggleGroup.getSelectedToggle())) {
			toReturn = AnalyticsReportTypes.TRUR;
		} else if(userMentionChoice.equals(toggleGroup.getSelectedToggle())) {
			toReturn = AnalyticsReportTypes.TRUMR;
		}else if(wordsChoice.equals(toggleGroup.getSelectedToggle())) {
			toReturn = AnalyticsReportTypes.TRWR;
		}
		((CreateTrendsReportServerTaskPreferencesDialogResponse) this.getResponse()).setType(toReturn);
		((CreateTrendsReportServerTaskPreferencesDialogResponse) this.getResponse()).setFilterList(this.getFilter());
		this.dialogStage.close();
	}
	@FXML
	public void onAddToFilter(){
		if(filterToAdd.getText()!=null&&!filterToAdd.getText().isEmpty()) {
			filterList.add(filterToAdd.getText());
			filterToAdd.clear();
		}
	}
	public void onRemoveFromFilter(){
		if (selectedFilter!=null) {
			filterList.remove(selectedFilter);
		}
	}


	/**
	 * @return the nSlider
	 */
	public Slider getnSlider() {
		return nSlider;
	}

	/**
	 * @param nSlider the nSlider to set
	 */
	public void setnSlider(Slider nSlider) {
		this.nSlider = nSlider;
	}

	/**
	 * @return the nText
	 */
	public Text getnText() {
		return nText;
	}

	/**
	 * @param nText the nText to set
	 */
	public void setnText(Text nText) {
		this.nText = nText;
	}

	/**
	 * @return the filter
	 */
	public List<String> getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(List<String> filter) {
		this.filter = filter;
	}

	/**
	 * @return the filterListView
	 */
	public ListView<String> getFilterListView() {
		return filterListView;
	}

	/**
	 * @param filterListView the filterListView to set
	 */
	public void setFilterListView(ListView<String> filterListView) {
		this.filterListView = filterListView;
	}

	/**
	 * @return the filterToAdd
	 */
	public TextField getFilterToAdd() {
		return filterToAdd;
	}

	/**
	 * @param filterToAdd the filterToAdd to set
	 */
	public void setFilterToAdd(TextField filterToAdd) {
		this.filterToAdd = filterToAdd;
	}

	/**
	 * @return the filterList
	 */
	public ObservableList<String> getFilterList() {
		return filterList;
	}

	/**
	 * @param filterList the filterList to set
	 */
	public void setFilterList(ObservableList<String> filterList) {
		this.filterList = filterList;
	}

	/**
	 * @return the selectedFilter
	 */
	public String getSelectedFilter() {
		return selectedFilter;
	}

	/**
	 * @param selectedFilter the selectedFilter to set
	 */
	public void setSelectedFilter(String selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	/**
	 * @return the hashtagChoice
	 */
	public ToggleButton getHashtagChoice() {
		return hashtagChoice;
	}

	/**
	 * @param hashtagChoice the hashtagChoice to set
	 */
	public void setHashtagChoice(ToggleButton hashtagChoice) {
		this.hashtagChoice = hashtagChoice;
	}

	/**
	 * @return the userChoice
	 */
	public ToggleButton getUserChoice() {
		return userChoice;
	}

	/**
	 * @param userChoice the userChoice to set
	 */
	public void setUserChoice(ToggleButton userChoice) {
		this.userChoice = userChoice;
	}

	/**
	 * @return the userMentionChoice
	 */
	public ToggleButton getUserMentionChoice() {
		return userMentionChoice;
	}

	/**
	 * @param userMentionChoice the userMentionChoice to set
	 */
	public void setUserMentionChoice(ToggleButton userMentionChoice) {
		this.userMentionChoice = userMentionChoice;
	}

	/**
	 * @return the wordsChoice
	 */
	public ToggleButton getWordsChoice() {
		return wordsChoice;
	}

	/**
	 * @param wordsChoice the wordsChoice to set
	 */
	public void setWordsChoice(ToggleButton wordsChoice) {
		this.wordsChoice = wordsChoice;
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
