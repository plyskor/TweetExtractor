/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.nlp;

import java.util.List;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
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
public class ShowSupportedLanguagesReferenceDialogControl extends TweetExtractorFXDialogController {
	@FXML
	private TableView<AvailableTwitterLanguage> languagesTable;
	@FXML
	private TableColumn<AvailableTwitterLanguage, String> shortCodeColumn;
	@FXML
	private TableColumn<AvailableTwitterLanguage, String> languageNameColumn;
	private ObservableList<AvailableTwitterLanguage> languageList = FXCollections.observableArrayList();

	public ShowSupportedLanguagesReferenceDialogControl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		languagesTable.setItems(languageList);
		initializeLanguageList();
	}
	@FXML 
	public void initialize() {
		shortCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShortName()));
		languageNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLongName()));
	}
	@FXML 
	public void onDone() {
		this.dialogStage.close();
	}
	public void initializeLanguageList() {
		ReferenceAvailableLanguagesServiceInterface languagesService;
		languagesService = this.getMainApplication().getSpringContext().getBean(ReferenceAvailableLanguagesServiceInterface.class);
		List<AvailableTwitterLanguage> list = languagesService.findAll();
		languageList.addAll(list);
	}
	
	
	/**
	 * @return the languagesTable
	 */
	public TableView<AvailableTwitterLanguage> getLanguagesTable() {
		return languagesTable;
	}

	/**
	 * @param languagesTable the languagesTable to set
	 */
	public void setLanguagesTable(TableView<AvailableTwitterLanguage> languagesTable) {
		this.languagesTable = languagesTable;
	}

	/**
	 * @return the languageNameColumn
	 */
	public TableColumn<AvailableTwitterLanguage, String> getLanguageNameColumn() {
		return languageNameColumn;
	}
	/**
	 * @param languageNameColumn the languageNameColumn to set
	 */
	public void setLanguageNameColumn(TableColumn<AvailableTwitterLanguage, String> languageNameColumn) {
		this.languageNameColumn = languageNameColumn;
	}

	/**
	 * @return the shortCodeColumn
	 */
	public TableColumn<AvailableTwitterLanguage, String> getShortCodeColumn() {
		return shortCodeColumn;
	}

	/**
	 * @param shortCodeColumn the shortCodeColumn to set
	 */
	public void setShortCodeColumn(TableColumn<AvailableTwitterLanguage, String> shortCodeColumn) {
		this.shortCodeColumn = shortCodeColumn;
	}

	/**
	 * @return the languageList
	 */
	public ObservableList<AvailableTwitterLanguage> getLanguageList() {
		return languageList;
	}

	/**
	 * @param languageList the languageList to set
	 */
	public void setLanguageList(ObservableList<AvailableTwitterLanguage> languageList) {
		this.languageList = languageList;
	}
	
}
