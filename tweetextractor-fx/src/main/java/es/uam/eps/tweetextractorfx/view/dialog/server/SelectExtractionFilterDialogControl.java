/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.server;

import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.util.FilterManager;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.SelectExtractionFilterDialogResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author jose
 *
 */
public class SelectExtractionFilterDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private TableView<Extraction> availableExtractionsTable;
	@FXML
	private TableColumn<Extraction, String> availableExtractionsColumn;
	@FXML
	private TableView<Extraction> addedExtractionsTable;
	@FXML
	private TableColumn<Extraction, String> addedExtractionsColumn;
	private ExtractionServiceInterface eServ;
	private ObservableList<Extraction> availableExtractionsList = FXCollections.observableArrayList();
	private ObservableList<Extraction> addedExtractionsList = FXCollections.observableArrayList();
	private Extraction selectedAvailableExtraction=null;
	private Extraction selectedAddedExtraction=null;
	/**
	 * 
	 */
	public SelectExtractionFilterDialogControl() {
		super();
	}
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		this.setResponse(new SelectExtractionFilterDialogResponse());
		availableExtractionsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(FilterManager.getQueryFromFilters(cellData.getValue().getFilterList())));
		addedExtractionsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(FilterManager.getQueryFromFilters(cellData.getValue().getFilterList())));
		availableExtractionsTable.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> setSelectedAvailableExtraction(newValue));
		addedExtractionsTable.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> setSelectedAddedExtraction(newValue));
		eServ=this.getMainApplication().getSpringContext().getBean(ExtractionServiceInterface.class);
		initializeAvailableExtractions();
	}
	@FXML
	private void initialize() {
		availableExtractionsTable.setItems(availableExtractionsList);
		addedExtractionsTable.setItems(addedExtractionsList);
	}
	
	@FXML
	private void onCancel() {
		this.getResponse().setIntValue(Constants.ERROR);
		this.getDialogStage().close();
	}
	@FXML
	private void onDone() {
		if(addedExtractionsList.isEmpty()) {
			ErrorDialog.showErrorNoSelectedExtractionForFilter();
			return;
		}
		((SelectExtractionFilterDialogResponse) this.getResponse()).getFilter().addAll(addedExtractionsList);
		this.dialogStage.close();
	}
	@FXML
	private void onAddToFilter() {
		if(selectedAvailableExtraction==null) {
			ErrorDialog.showErrorNoSelectedExtraction();
			return;
		}
		addedExtractionsList.add(selectedAvailableExtraction);
	}
	@FXML
	private void onAddAllToFilter() {
		addedExtractionsList.clear();
		addedExtractionsList.addAll(availableExtractionsList);
	}
	@FXML
	private void onRemoveAllFromFilter() {
		addedExtractionsList.clear();
	}
	@FXML
	private void onRemoveFromFilter() {
		if(selectedAddedExtraction==null) {
			ErrorDialog.showErrorNoSelectedExtraction();
			return;
		}
		addedExtractionsList.remove(selectedAddedExtraction);
	}
	private void initializeAvailableExtractions() {
		availableExtractionsList.clear();
		availableExtractionsList.addAll(eServ.findByUser(this.getMainApplication().getCurrentUser()));
	}
	/**
	 * @return the availableExtractionsTable
	 */
	public TableView<Extraction> getAvailableExtractionsTable() {
		return availableExtractionsTable;
	}
	/**
	 * @param availableExtractionsTable the availableExtractionsTable to set
	 */
	public void setAvailableExtractionsTable(TableView<Extraction> availableExtractionsTable) {
		this.availableExtractionsTable = availableExtractionsTable;
	}
	/**
	 * @return the availableExtractionsColumn
	 */
	public TableColumn<Extraction, String> getAvailableExtractionsColumn() {
		return availableExtractionsColumn;
	}
	/**
	 * @param availableExtractionsColumn the availableExtractionsColumn to set
	 */
	public void setAvailableExtractionsColumn(TableColumn<Extraction, String> availableExtractionsColumn) {
		this.availableExtractionsColumn = availableExtractionsColumn;
	}
	/**
	 * @return the addedExtractionsTable
	 */
	public TableView<Extraction> getAddedExtractionsTable() {
		return addedExtractionsTable;
	}
	/**
	 * @param addedExtractionsTable the addedExtractionsTable to set
	 */
	public void setAddedExtractionsTable(TableView<Extraction> addedExtractionsTable) {
		this.addedExtractionsTable = addedExtractionsTable;
	}
	/**
	 * @return the addedExtractionsColumn
	 */
	public TableColumn<Extraction, String> getAddedExtractionsColumn() {
		return addedExtractionsColumn;
	}
	/**
	 * @param addedExtractionsColumn the addedExtractionsColumn to set
	 */
	public void setAddedExtractionsColumn(TableColumn<Extraction, String> addedExtractionsColumn) {
		this.addedExtractionsColumn = addedExtractionsColumn;
	}
	/**
	 * @return the availableExtractionsList
	 */
	public ObservableList<Extraction> getAvailableExtractionsList() {
		return availableExtractionsList;
	}
	/**
	 * @param availableExtractionsList the availableExtractionsList to set
	 */
	public void setAvailableExtractionsList(ObservableList<Extraction> availableExtractionsList) {
		this.availableExtractionsList = availableExtractionsList;
	}
	/**
	 * @return the addedExtractionsList
	 */
	public ObservableList<Extraction> getAddedExtractionsList() {
		return addedExtractionsList;
	}
	/**
	 * @param addedExtractionsList the addedExtractionsList to set
	 */
	public void setAddedExtractionsList(ObservableList<Extraction> addedExtractionsList) {
		this.addedExtractionsList = addedExtractionsList;
	}
	/**
	 * @return the selectedAvailableExtraction
	 */
	public Extraction getSelectedAvailableExtraction() {
		return selectedAvailableExtraction;
	}
	/**
	 * @param selectedAvailableExtraction the selectedAvailableExtraction to set
	 */
	public void setSelectedAvailableExtraction(Extraction selectedAvailableExtraction) {
		this.selectedAvailableExtraction = selectedAvailableExtraction;
	}
	/**
	 * @return the selectedAddedExtraction
	 */
	public Extraction getSelectedAddedExtraction() {
		return selectedAddedExtraction;
	}
	/**
	 * @param selectedAddedExtraction the selectedAddedExtraction to set
	 */
	public void setSelectedAddedExtraction(Extraction selectedAddedExtraction) {
		this.selectedAddedExtraction = selectedAddedExtraction;
	}
	
}
