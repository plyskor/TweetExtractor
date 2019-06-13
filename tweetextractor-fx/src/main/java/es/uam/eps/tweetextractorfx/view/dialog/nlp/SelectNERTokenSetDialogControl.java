/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.nlp;

import java.time.LocalDate;
import java.time.ZoneId;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERTokenSetServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.response.SelectNERTokenSetDialogResponse;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
public class SelectNERTokenSetDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private TableView<TweetExtractorNERTokenSet> tokenSetListTable;
	@FXML
	private TableColumn<TweetExtractorNERTokenSet, String> tokenSetNameColumn;
	@FXML
	private TableColumn<TweetExtractorNERTokenSet, Integer> tokenSetWordsNumberColumn;
	@FXML
	private TableColumn<TweetExtractorNERTokenSet, LocalDate> tokenSetLastUpdatedColumn;
	private ObservableList<TweetExtractorNERTokenSet> tableList = FXCollections.observableArrayList();
	private TweetExtractorNERTokenSet selectedTokenSet = null;
	private AvailableTwitterLanguage language = null;
	private TweetExtractorNERTokenSetServiceInterface tsServ;

	/**
	 * 
	 */
	public SelectNERTokenSetDialogControl() {
		super();
		this.setResponse(new SelectNERTokenSetDialogResponse());
		this.getResponse().setIntValue(Constants.ERROR);
	}

	@FXML
	public void initialize() {
		tokenSetNameColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getIdentifier().getName()));
		tokenSetWordsNumberColumn.setCellValueFactory(
				cellData -> new SimpleIntegerProperty(cellData.getValue().getnTokens()).asObject());
		tokenSetLastUpdatedColumn.setCellValueFactory(cellData -> {
			if (cellData.getValue().getLastUpdated() != null) {
				return new SimpleObjectProperty<LocalDate>(
						cellData.getValue().getLastUpdated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			}
			return new SimpleObjectProperty<LocalDate>();
		});
		tokenSetListTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedTokenSet(newValue));
		setSelectedTokenSet(null);
	}

	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		tokenSetListTable.setItems(tableList);
		tsServ = this.mainApplication.getSpringContext().getBean(TweetExtractorNERTokenSetServiceInterface.class);
		loadAvailableTokenSets();
	}

	private void loadAvailableTokenSets() {
		tableList.addAll(tsServ.findByUserAndLanguage(this.mainApplication.getCurrentUser(), language));
		boolean allSetsEmpty=true;
		for(TweetExtractorNERTokenSet tokenSet:tableList) {
			if(tokenSet.getnTokens()>0) {
				allSetsEmpty=false;
			}
		}
		if(tableList.isEmpty()||allSetsEmpty) {
			ErrorDialog.showErrorNoTokenSetsAvailable();
			onBack();
		}
		
	}
	@FXML
	public void onBack() {
		this.dialogStage.close();
	}
	@FXML
	public void onDone() {
		if(selectedTokenSet==null) {
			ErrorDialog.showErrorNoSelectedTokenSet();
			return;
		}
		this.getResponse().setIntValue(Constants.SUCCESS);
		((SelectNERTokenSetDialogResponse) this.getResponse()).setTokenSet(selectedTokenSet);
		this.dialogStage.close();
	}
	/**
	 * @return the tokenSetListTable
	 */
	public TableView<TweetExtractorNERTokenSet> getTokenSetListTable() {
		return tokenSetListTable;
	}

	/**
	 * @param tokenSetListTable the tokenSetListTable to set
	 */
	public void setTokenSetListTable(TableView<TweetExtractorNERTokenSet> tokenSetListTable) {
		this.tokenSetListTable = tokenSetListTable;
	}

	/**
	 * @return the tokenSetNameColumn
	 */
	public TableColumn<TweetExtractorNERTokenSet, String> getTokenSetNameColumn() {
		return tokenSetNameColumn;
	}

	/**
	 * @param tokenSetNameColumn the tokenSetNameColumn to set
	 */
	public void setTokenSetNameColumn(TableColumn<TweetExtractorNERTokenSet, String> tokenSetNameColumn) {
		this.tokenSetNameColumn = tokenSetNameColumn;
	}

	/**
	 * @return the tokenSetWordsNumberColumn
	 */
	public TableColumn<TweetExtractorNERTokenSet, Integer> getTokenSetWordsNumberColumn() {
		return tokenSetWordsNumberColumn;
	}

	/**
	 * @param tokenSetWordsNumberColumn the tokenSetWordsNumberColumn to set
	 */
	public void setTokenSetWordsNumberColumn(
			TableColumn<TweetExtractorNERTokenSet, Integer> tokenSetWordsNumberColumn) {
		this.tokenSetWordsNumberColumn = tokenSetWordsNumberColumn;
	}

	/**
	 * @return the tokenSetLastUpdatedColumn
	 */
	public TableColumn<TweetExtractorNERTokenSet, LocalDate> getTokenSetLastUpdatedColumn() {
		return tokenSetLastUpdatedColumn;
	}

	/**
	 * @param tokenSetLastUpdatedColumn the tokenSetLastUpdatedColumn to set
	 */
	public void setTokenSetLastUpdatedColumn(
			TableColumn<TweetExtractorNERTokenSet, LocalDate> tokenSetLastUpdatedColumn) {
		this.tokenSetLastUpdatedColumn = tokenSetLastUpdatedColumn;
	}

	/**
	 * @return the tableList
	 */
	public ObservableList<TweetExtractorNERTokenSet> getTableList() {
		return tableList;
	}

	/**
	 * @param tableList the tableList to set
	 */
	public void setTableList(ObservableList<TweetExtractorNERTokenSet> tableList) {
		this.tableList = tableList;
	}

	/**
	 * @return the selectedTokenSet
	 */
	public TweetExtractorNERTokenSet getSelectedTokenSet() {
		return selectedTokenSet;
	}

	/**
	 * @param selectedTokenSet the selectedTokenSet to set
	 */
	public void setSelectedTokenSet(TweetExtractorNERTokenSet selectedTokenSet) {
		this.selectedTokenSet = selectedTokenSet;
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
	}

}
