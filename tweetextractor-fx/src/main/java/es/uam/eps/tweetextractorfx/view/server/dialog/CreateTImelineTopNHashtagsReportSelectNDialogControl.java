/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import java.util.ArrayList;
import java.util.List;

import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTimelineTopNHashtagsReportDialogResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author jose
 *
 */
public class CreateTImelineTopNHashtagsReportSelectNDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private Slider nHashtagsSlider;
	@FXML
	private Text nHashtagsText;
	
	private int toReturn;
	
	private List<String> filter = new ArrayList<>();
	@FXML
	private ListView<String> hashtagFilterListView;
	@FXML
	private TextField hashtagToAdd;
	
	private ObservableList<String> hashtagFilterList = FXCollections.observableArrayList();
	
	private String selectedHashtag=null;
	/**
	 * 
	 */
	public CreateTImelineTopNHashtagsReportSelectNDialogControl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		nHashtagsSlider.valueProperty().addListener((observable, oldValue, newValue) -> nHashtagsText.setText(""+newValue.intValue()));
		hashtagFilterListView.setItems(hashtagFilterList);
		this.setResponse(new CreateTimelineTopNHashtagsReportDialogResponse());
	}
	@FXML
	public void initialize() {
		hashtagFilterListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setSelectedHashtag(newValue));
	}
	@FXML
	public void onCancel() {
		setToReturn(-1);
		this.dialogStage.close();
	}
	@FXML
	public void onDone() {
		this.getFilter().addAll(hashtagFilterList);
		this.getResponse().setIntValue((int)nHashtagsSlider.getValue());
		((CreateTimelineTopNHashtagsReportDialogResponse) this.getResponse()).setFilterList(this.getFilter());
		this.dialogStage.close();
	}
	@FXML
	public void onAddHashtagToFilter() {
		if(hashtagToAdd.getText()!=null&&!hashtagToAdd.getText().isEmpty()) {
			hashtagFilterList.add(hashtagToAdd.getText());
			hashtagToAdd.clear();
		}
	}
	@FXML
	public void onDeleteHashtagFromFilter() {
		if (selectedHashtag!=null) {
			hashtagFilterList.remove(selectedHashtag);
		}
	}
	/**
	 * @return the nHashtagsSlider
	 */
	public Slider getnHashtagsSlider() {
		return nHashtagsSlider;
	}
	/**
	 * @param nHashtagsSlider the nHashtagsSlider to set
	 */
	public void setnHashtagsSlider(Slider nHashtagsSlider) {
		this.nHashtagsSlider = nHashtagsSlider;
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
	/**
	 * @return the nHashtagsText
	 */
	public Text getnHashtagsText() {
		return nHashtagsText;
	}
	/**
	 * @param nHashtagsText the nHashtagsText to set
	 */
	public void setnHashtagsText(Text nHashtagsText) {
		this.nHashtagsText = nHashtagsText;
	}

	/**
	 * @return the hashtagFilterListView
	 */
	public ListView<String> getHashtagFilterListView() {
		return hashtagFilterListView;
	}

	/**
	 * @param hashtagFilterListView the hashtagFilterListView to set
	 */
	public void setHashtagFilterListView(ListView<String> hashtagFilterListView) {
		this.hashtagFilterListView = hashtagFilterListView;
	}

	/**
	 * @return the hashtagToAdd
	 */
	public TextField getHashtagToAdd() {
		return hashtagToAdd;
	}

	/**
	 * @param hashtagToAdd the hashtagToAdd to set
	 */
	public void setHashtagToAdd(TextField hashtagToAdd) {
		this.hashtagToAdd = hashtagToAdd;
	}

	/**
	 * @return the selectedHashtag
	 */
	public String getSelectedHashtag() {
		return selectedHashtag;
	}

	/**
	 * @param selectedHashtag the selectedHashtag to set
	 */
	public void setSelectedHashtag(String selectedHashtag) {
		this.selectedHashtag = selectedHashtag;
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

}
