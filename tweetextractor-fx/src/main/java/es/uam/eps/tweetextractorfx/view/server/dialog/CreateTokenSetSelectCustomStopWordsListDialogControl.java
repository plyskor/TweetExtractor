/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.server.dialog;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.CustomStopWordsListServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.CreateTokenSetSelectCustomStopWordsListDialogResponse;
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
public class CreateTokenSetSelectCustomStopWordsListDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private TableView<CustomStopWordsList> customStopWordsListTable;
	@FXML
	private TableColumn<CustomStopWordsList, String> customStopWordsListNameColumn;
	private ObservableList<CustomStopWordsList> tableList = FXCollections.observableArrayList();
	private CustomStopWordsList selectedCustomStopWordsList = null;
	private CustomStopWordsListServiceInterface swlServ;
	private AvailableTwitterLanguage language;
	private Logger logger = LoggerFactory.getLogger(CreateTokenSetSelectCustomStopWordsListDialogControl.class);
	/**
	 * 
	 */
	public CreateTokenSetSelectCustomStopWordsListDialogControl() {
		super();
	}
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		customStopWordsListTable.setItems(tableList);
		this.setResponse(new CreateTokenSetSelectCustomStopWordsListDialogResponse());
		this.getResponse().setIntValue(Constants.ERROR);
		swlServ = this.getMainApplication().getSpringContext().getBean(CustomStopWordsListServiceInterface.class);
	}
	@FXML
	private void initialize() {
		customStopWordsListNameColumn
		.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		customStopWordsListTable.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> setSelectedCustomStopWordsList(newValue));
		selectedCustomStopWordsList = null;
	}
	@FXML
	private void onCancel() {
		this.dialogStage.close();
	}
	@FXML
	private void onDone() {
		if(selectedCustomStopWordsList==null) {
			ErrorDialog.showErrorNoSelectedStopWordsList();
		}else {
			this.getResponse().setIntValue(Constants.SUCCESS);
			((CreateTokenSetSelectCustomStopWordsListDialogResponse) this.getResponse()).setStopWordsList(selectedCustomStopWordsList);
			this.dialogStage.close();
		}
	}
	private void loadStopWordsLists() {
		if (language != null) {
			tableList.clear();
			List<CustomStopWordsList> list = swlServ.findByUserAndLanguage(this.getMainApplication().getCurrentUser(),
					language);
			if (list != null && !list.isEmpty()) {
				tableList.addAll(list);
			}
		}
	}
	/**
	 * @return the customStopWordsListTable
	 */
	public TableView<CustomStopWordsList> getCustomStopWordsListTable() {
		return customStopWordsListTable;
	}
	/**
	 * @param customStopWordsListTable the customStopWordsListTable to set
	 */
	public void setCustomStopWordsListTable(TableView<CustomStopWordsList> customStopWordsListTable) {
		this.customStopWordsListTable = customStopWordsListTable;
	}
	/**
	 * @return the customStopWordsListNameColumn
	 */
	public TableColumn<CustomStopWordsList, String> getCustomStopWordsListNameColumn() {
		return customStopWordsListNameColumn;
	}
	/**
	 * @param customStopWordsListNameColumn the customStopWordsListNameColumn to set
	 */
	public void setCustomStopWordsListNameColumn(TableColumn<CustomStopWordsList, String> customStopWordsListNameColumn) {
		this.customStopWordsListNameColumn = customStopWordsListNameColumn;
	}
	/**
	 * @return the tableList
	 */
	public ObservableList<CustomStopWordsList> getTableList() {
		return tableList;
	}
	/**
	 * @param tableList the tableList to set
	 */
	public void setTableList(ObservableList<CustomStopWordsList> tableList) {
		this.tableList = tableList;
	}
	/**
	 * @return the selectedCustomStopWordsList
	 */
	public CustomStopWordsList getSelectedCustomStopWordsList() {
		return selectedCustomStopWordsList;
	}
	/**
	 * @param selectedCustomStopWordsList the selectedCustomStopWordsList to set
	 */
	public void setSelectedCustomStopWordsList(CustomStopWordsList selectedCustomStopWordsList) {
		this.selectedCustomStopWordsList = selectedCustomStopWordsList;
	}
	/**
	 * @return the swlServ
	 */
	public CustomStopWordsListServiceInterface getSwlServ() {
		return swlServ;
	}
	/**
	 * @param swlServ the swlServ to set
	 */
	public void setSwlServ(CustomStopWordsListServiceInterface swlServ) {
		this.swlServ = swlServ;
	}
	/**
	 * @return the language
	 */
	public AvailableTwitterLanguage getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(AvailableTwitterLanguage language) {
		this.language = language;
		loadStopWordsLists();
	}
	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}
	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
}
