/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateExtractionServerTaskSelectExtractionDialogResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public class CreateExtractionServerTaskSelectExtractionDialogControl extends TweetExtractorFXDialogController{
    @FXML 
    private TableView<Extraction> extractionTableView;
    @FXML
	private TableColumn<Extraction, String> extractionIDColumn;
	@FXML
	private TableColumn<Extraction, String> extractionCreatedOnColumn;
	@FXML
	private TableColumn<Extraction, String> extractionFiltersColumn;
	@FXML
	private ObservableList<Extraction> extractionList =FXCollections.observableArrayList();
	private Extraction selectedExtraction;
	/**
	 * 
	 */
	public CreateExtractionServerTaskSelectExtractionDialogControl() {
		super();
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		this.extractionTableView.setItems(extractionList);
		this.extractionList.addAll(this.mainApplication.getCurrentUser().getExtractionList());
		
	}

	/**
	 * @return the extractionTableView
	 */
	public TableView<Extraction> getExtractionTableView() {
		return extractionTableView;
	}
	/**
	 * @param extractionTableView the extractionTableView to set
	 */
	public void setExtractionTableView(TableView<Extraction> extractionTableView) {
		this.extractionTableView = extractionTableView;
	}
	/**
	 * @return the extractionIDColumn
	 */
	public TableColumn<Extraction, String> getExtractionIDColumn() {
		return extractionIDColumn;
	}
	/**
	 * @param extractionIDColumn the extractionIDColumn to set
	 */
	public void setExtractionIDColumn(TableColumn<Extraction, String> extractionIDColumn) {
		this.extractionIDColumn = extractionIDColumn;
	}
	/**
	 * @return the extractionCreatedOnColumn
	 */
	public TableColumn<Extraction, String> getExtractionCreatedOnColumn() {
		return extractionCreatedOnColumn;
	}
	/**
	 * @param extractionCreatedOnColumn the extractionCreatedOnColumn to set
	 */
	public void setExtractionCreatedOnColumn(TableColumn<Extraction, String> extractionCreatedOnColumn) {
		this.extractionCreatedOnColumn = extractionCreatedOnColumn;
	}
	/**
	 * @return the extractionFiltersColumn
	 */
	public TableColumn<Extraction, String> getExtractionFiltersColumn() {
		return extractionFiltersColumn;
	}
	/**
	 * @param extractionFiltersColumn the extractionFiltersColumn to set
	 */
	public void setExtractionFiltersColumn(TableColumn<Extraction, String> extractionFiltersColumn) {
		this.extractionFiltersColumn = extractionFiltersColumn;
	}
	/**
	 * @return the extractionList
	 */
	public ObservableList<Extraction> getExtractionList() {
		return extractionList;
	}
	/**
	 * @param extractionList the extractionList to set
	 */
	public void setExtractionList(ObservableList<Extraction> extractionList) {
		this.extractionList = extractionList;
	}
	/**
	 * @return the selectedExtraction
	 */
	public Extraction getSelectedExtraction() {
		return selectedExtraction;
	}
	/**
	 * @param selectedExtraction the selectedExtraction to set
	 */
	public void setSelectedExtraction(Extraction selectedExtraction) {
		this.selectedExtraction = selectedExtraction;
	}
	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the extraction table with the  columns.
		extractionIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getIdDB()));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		extractionCreatedOnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(df.format(cellData.getValue().getCreationDate())));
		extractionFiltersColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFiltersColumn()));
		// Listen for selection changes and show the extraction details when changed.
		extractionTableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedExtraction(newValue));
		selectedExtraction = null;
	}

	@FXML
    public void onBack() {
		this.setResponse((null));
    	this.dialogStage.close();
    }
    @FXML 
    public void onNext() {
    	if(selectedExtraction==null) {
    		ErrorDialog.showErrorNoSelectedExtraction();
    	}else {
    		this.setResponse(new CreateExtractionServerTaskSelectExtractionDialogResponse(selectedExtraction));
    		this.dialogStage.close();
    	}
    }
}
