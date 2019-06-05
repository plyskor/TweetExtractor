/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.nlp;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNamedEntityServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorTopicServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorTopic;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.HomeScreenControl;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import es.uam.eps.tweetextractorfx.view.dialog.nlp.SelectNamedEntityNameDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.nlp.SelectTopicNameDialogControl;
import es.uam.eps.tweetextractorfx.view.dialog.response.TweetExtractorFXDialogResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author jose
 *
 */
public class EditNERPreferencesControl extends TweetExtractorFXController {
	@FXML
	private TableView<TweetExtractorNamedEntity> namedEntitiesTable;
	@FXML
	private TableView<TweetExtractorTopic> topicsTable;
	@FXML
	private TableColumn<TweetExtractorNamedEntity, String> namedEntitiesColumn;
	@FXML
	private TableColumn<TweetExtractorTopic, String> topicsColumn;
	@FXML
	private Text title;
	private TweetExtractorNERConfiguration preferences;
	private ObservableList<TweetExtractorNamedEntity> namedEntitiesList = FXCollections.observableArrayList();
	private ObservableList<TweetExtractorTopic> topicsList = FXCollections.observableArrayList();
	private TweetExtractorTopicServiceInterface topicService;
	private TweetExtractorNamedEntityServiceInterface namedEntityService;
	private TweetExtractorNamedEntity selectedNamedEntity;
	private TweetExtractorTopic selectedTopic;

	/**
	 * 
	 */
	public EditNERPreferencesControl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#
	 * setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		namedEntitiesTable.setItems(namedEntitiesList);
		topicsTable.setItems(topicsList);
		topicService = this.getMainApplication().getSpringContext().getBean(TweetExtractorTopicServiceInterface.class);
		namedEntityService = this.getMainApplication().getSpringContext()
				.getBean(TweetExtractorNamedEntityServiceInterface.class);
	}

	@FXML
	private void initialize() {
		namedEntitiesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		namedEntitiesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setSelectedNamedEntity(newValue);
			loadTopics();
		});
		selectedNamedEntity = null;
		topicsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		topicsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedTopic(newValue));
		selectedTopic = null;
	}

	@FXML
	private void onAddNamedEntity() {
		TweetExtractorFXDialogResponse reply = showSelectNameDialog("dialog/nlp/SelectNamedEntityNameDialog.fxml",
				SelectNamedEntityNameDialogControl.class, preferences, selectedNamedEntity);
		if (reply != null&&reply.getIntValue()==Constants.SUCCESS) {
			TweetExtractorNamedEntity newEntity = new TweetExtractorNamedEntity(reply.getStringValue());
			newEntity.setConfiguration(preferences);
			preferences.getNamedEntities().add(newEntity);
			namedEntityService.saveOrUpdate(newEntity);
		}
		loadNamedEntities();
	}

	@FXML
	private void onRenameNamedEntity() {
		if (selectedNamedEntity == null) {
			ErrorDialog.showErrorNoSelectedNamedEntity();
			return;
		}
		if(selectedNamedEntity.getName().equals("NIL Category")) {
			return;
		}
		TweetExtractorFXDialogResponse reply = showSelectNameDialog("dialog/nlp/SelectNamedEntityNameDialog.fxml",
				SelectNamedEntityNameDialogControl.class, preferences, selectedNamedEntity);
		if (reply != null&&reply.getIntValue()==Constants.SUCCESS) {
			selectedNamedEntity.setName(reply.getStringValue());
			namedEntityService.saveOrUpdate(selectedNamedEntity);
		}
		loadNamedEntities();
	}

	@FXML
	private void onDeleteNamedEntity() {
		if (selectedNamedEntity == null) {
			ErrorDialog.showErrorNoSelectedNamedEntity();
			return;
		}
		if(selectedNamedEntity.getName().equals("NIL Category")) {
			return;
		}
		preferences.getNamedEntities().remove(selectedNamedEntity);
		namedEntityService.delete(selectedNamedEntity);
		loadNamedEntities();
		topicsList.clear();
	}

	@FXML
	private void onAddTopic() {
		if (selectedNamedEntity == null) {
			ErrorDialog.showErrorNoSelectedNamedEntity();
			return;
		}
		if(selectedNamedEntity.getName().equals("NIL Category")) {
			return;
		}
		TweetExtractorFXDialogResponse reply = showSelectNameDialog("dialog/nlp/SelectTopicNameDialog.fxml",
				SelectTopicNameDialogControl.class, preferences, selectedNamedEntity);
		if (reply != null&&reply.getIntValue()==Constants.SUCCESS) {
			TweetExtractorTopic newTopic = new TweetExtractorTopic(reply.getStringValue(), selectedNamedEntity);
			selectedNamedEntity.getTopics().add(newTopic);
			topicService.saveOrUpdate(newTopic);
		}
		loadTopics();
	}

	@FXML
	private void onRenameTopic() {
		if (selectedTopic == null) {
			ErrorDialog.showErrorNoSelectedTopic();
			return;
		}
		if(selectedNamedEntity.getName().equals("NIL Category")) {
			return;
		}
		TweetExtractorFXDialogResponse reply = showSelectNameDialog("dialog/nlp/SelectTopicNameDialog.fxml",
				SelectTopicNameDialogControl.class, preferences, selectedNamedEntity);
		if (reply != null&&reply.getIntValue()==Constants.SUCCESS) {
			selectedTopic.setName(reply.getStringValue());
			topicService.saveOrUpdate(selectedTopic);
		}
		loadTopics();
	}

	@FXML
	private void onDeleteTopic() {
		if (selectedTopic == null) {
			ErrorDialog.showErrorNoSelectedTopic();
			return;
		}
		if(selectedNamedEntity.getName().equals("NIL Category")) {
			return;
		}
		selectedNamedEntity.getTopics().remove(selectedTopic);
		topicService.delete(selectedTopic);
		loadTopics();
	}

	@FXML
	private void onDone() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/MyNERPreferences.fxml");
	}

	public void loadNamedEntities() {
		if(preferences!=null) {
			namedEntitiesList.clear();
			namedEntitiesList.addAll(preferences.getNamedEntities());
		}
	}

	public void loadTopics() {
		if(selectedNamedEntity!=null) {
			topicsList.clear();
			topicsList.addAll(selectedNamedEntity.getTopics());
		}
	}

	/**
	 * @return the namedEntitiesTable
	 */
	public TableView<TweetExtractorNamedEntity> getNamedEntitiesTable() {
		return namedEntitiesTable;
	}

	/**
	 * @param namedEntitiesTable the namedEntitiesTable to set
	 */
	public void setNamedEntitiesTable(TableView<TweetExtractorNamedEntity> namedEntitiesTable) {
		this.namedEntitiesTable = namedEntitiesTable;
	}

	/**
	 * @return the topicsTable
	 */
	public TableView<TweetExtractorTopic> getTopicsTable() {
		return topicsTable;
	}

	/**
	 * @param topicsTable the topicsTable to set
	 */
	public void setTopicsTable(TableView<TweetExtractorTopic> topicsTable) {
		this.topicsTable = topicsTable;
	}

	/**
	 * @return the namedEntitiesColumn
	 */
	public TableColumn<TweetExtractorNamedEntity, String> getNamedEntitiesColumn() {
		return namedEntitiesColumn;
	}

	/**
	 * @param namedEntitiesColumn the namedEntitiesColumn to set
	 */
	public void setNamedEntitiesColumn(TableColumn<TweetExtractorNamedEntity, String> namedEntitiesColumn) {
		this.namedEntitiesColumn = namedEntitiesColumn;
	}

	/**
	 * @return the topicsColumn
	 */
	public TableColumn<TweetExtractorTopic, String> getTopicsColumn() {
		return topicsColumn;
	}

	/**
	 * @param topicsColumn the topicsColumn to set
	 */
	public void setTopicsColumn(TableColumn<TweetExtractorTopic, String> topicsColumn) {
		this.topicsColumn = topicsColumn;
	}

	/**
	 * @return the title
	 */
	public Text getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(Text title) {
		this.title = title;
	}

	/**
	 * @return the preferences
	 */
	public TweetExtractorNERConfiguration getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(TweetExtractorNERConfiguration preferences) {
		this.preferences = preferences;
		this.title.setText("Edit " + preferences.getIdentifier().getName() + " ("
				+ preferences.getIdentifier().getLanguage().getShortName() + ")");
		loadNamedEntities();
	}

	/**
	 * @return the namedEntitiesList
	 */
	public ObservableList<TweetExtractorNamedEntity> getNamedEntitiesList() {
		return namedEntitiesList;
	}

	/**
	 * @param namedEntitiesList the namedEntitiesList to set
	 */
	public void setNamedEntitiesList(ObservableList<TweetExtractorNamedEntity> namedEntitiesList) {
		this.namedEntitiesList = namedEntitiesList;
	}

	/**
	 * @return the topicsList
	 */
	public ObservableList<TweetExtractorTopic> getTopicsList() {
		return topicsList;
	}

	/**
	 * @param topicsList the topicsList to set
	 */
	public void setTopicsList(ObservableList<TweetExtractorTopic> topicsList) {
		this.topicsList = topicsList;
	}

	/**
	 * @return the selectedNamedEntity
	 */
	public TweetExtractorNamedEntity getSelectedNamedEntity() {
		return selectedNamedEntity;
	}

	/**
	 * @param selectedNamedEntity the selectedNamedEntity to set
	 */
	public void setSelectedNamedEntity(TweetExtractorNamedEntity selectedNamedEntity) {
		this.selectedNamedEntity = selectedNamedEntity;
	}

	/**
	 * @return the selectedTopic
	 */
	public TweetExtractorTopic getSelectedTopic() {
		return selectedTopic;
	}

	/**
	 * @param selectedTopic the selectedTopic to set
	 */
	public void setSelectedTopic(TweetExtractorTopic selectedTopic) {
		this.selectedTopic = selectedTopic;
	}

	private TweetExtractorFXDialogResponse showSelectNameDialog(String fxmlPath, Class<?> controllerClazz,
			TweetExtractorNERConfiguration preferences, TweetExtractorNamedEntity namedEntity) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeScreenControl.class.getResource(fxmlPath));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			TweetExtractorFXDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApplication(mainApplication);
			Method meth = controllerClazz.getMethod("setPreferences", TweetExtractorNERConfiguration.class);
			meth.invoke(controller, preferences);
			meth = controllerClazz.getMethod("setNamedEntity", TweetExtractorNamedEntity.class);
			meth.invoke(controller, namedEntity);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			return controller.getResponse();
		} catch (Exception e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
}
