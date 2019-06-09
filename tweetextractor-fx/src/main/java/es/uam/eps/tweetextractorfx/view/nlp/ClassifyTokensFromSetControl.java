/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.nlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERTokenServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorTopicServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERToken;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorTopic;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author jose
 *
 */
public class ClassifyTokensFromSetControl extends TweetExtractorFXController{
	@FXML 
	private Label currentTokenText;
	@FXML
	private ChoiceBox<String> categoriesChoiceBox;
	@FXML
	private TableView<TweetExtractorTopic> availableTopicsTable;
	@FXML
	private TableColumn<TweetExtractorTopic, String> availableTopicsColumn;
	@FXML
	private TableView<TweetExtractorTopic> addedTopicsTable;
	@FXML
	private TableColumn<TweetExtractorTopic, String> addedTopicsColumn;
	private ObservableList<TweetExtractorTopic> addedTopicsList = FXCollections.observableArrayList();
	private ObservableList<TweetExtractorTopic> availableTopicsList = FXCollections.observableArrayList();
	private List<TweetExtractorNamedEntity> availableCategories = new ArrayList<>();
	private TweetExtractorNERConfiguration preferences=null;
	private TweetExtractorNERTokenSetID tokenSetID=null;
	private TweetExtractorTopic selectedAvailableTopic=null;
	private TweetExtractorTopic selectedAddedTopic=null;
	private TweetExtractorNamedEntity selectedCategory =null;
	private TweetExtractorNERToken currentToken=null;
	private TweetExtractorTopicServiceInterface topicService;
	private TweetExtractorNERTokenServiceInterface tokenService;
	private List<TweetExtractorNERToken> tokenList = new ArrayList<>();
	
	/**
	 * 
	 */
	public ClassifyTokensFromSetControl() {
		super();
	}


	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		addedTopicsTable.setItems(addedTopicsList);
		availableTopicsTable.setItems(availableTopicsList);
		topicService=this.mainApplication.getSpringContext().getBean(TweetExtractorTopicServiceInterface.class);
		tokenService=this.mainApplication.getSpringContext().getBean(TweetExtractorNERTokenServiceInterface.class);
		initController();
		classifyNextToken();
	}

	private void initController() {
		tokenList.addAll(tokenService.findNotClassifiedBySet(tokenSetID));
		Collections.sort(tokenList);
		for(TweetExtractorNamedEntity category: preferences.getNamedEntities()) {
			categoriesChoiceBox.getItems().add(category.getName());
		}
		/* Choice box language selector listener */
		categoriesChoiceBox.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					setSelectedCategory(preferences.findByName(newValue));
					loadAvailableTopics();
				});
		setSelectedCategory(preferences.findByName("NIL Category"));
		categoriesChoiceBox.setValue(selectedCategory.getName());
	}


	private void loadAvailableTopics() {
		if(selectedCategory!=null) {
			addedTopicsList.clear();
			availableTopicsList.clear();
			availableTopicsList.addAll(selectedCategory.getTopics());
			selectedAvailableTopic=null;
			selectedAddedTopic=null;
		}
	}

	@FXML
	public void initialize() {
		addedTopicsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		addedTopicsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedAddedTopic(newValue));
		availableTopicsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		availableTopicsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedAvailableTopic(newValue));
	}
	
	@FXML
	public void onDone() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/nlp/MyNERPreferences.fxml");
	}
	
	@FXML
	public void onNIL() {
		TweetExtractorTopic nilTopic =preferences.findByName("NIL Category").getTopics().get(0);
		nilTopic.getLinkedTokens().add(currentToken);
		currentToken.setClassified(true);
		topicService.saveOrUpdate(nilTopic);
		tokenService.saveOrUpdate(currentToken);
		classifyNextToken();
	}
	
	@FXML
	public void onClassify() {
		if(addedTopicsList.isEmpty()) {
			return;
		}
		for(TweetExtractorTopic topic: addedTopicsList) {
			topic.getLinkedTokens().add(currentToken);
			topicService.saveOrUpdate(topic);
		}
		currentToken.setClassified(true);
		tokenService.saveOrUpdate(currentToken);
		classifyNextToken();
	}
	private void classifyNextToken() {
		if(currentToken!=null) {
			tokenList.remove(currentToken);
		}
		if(tokenList.isEmpty()) {
			noMoreTokensToClassify();
			return;
		}
		loadAvailableTopics();
		currentToken=tokenList.get(0);
		currentTokenText.setText(buildTopicText(currentToken));
	}
	private String buildTopicText(TweetExtractorNERToken token) {
		if(token!=null) {
			StringBuilder tokenTextBuilder = new StringBuilder();
			tokenTextBuilder.append(token.getRoot()+"\n(");
			List<String> termsList = new ArrayList<>();
			termsList.addAll(token.getTerms());
			for(int i=0;i<token.getTerms().size();i++) {
				if(i==token.getTerms().size()-1) {
					/*Last term*/
					tokenTextBuilder.append(termsList.get(i));
				}else {
					/*Not last term*/
					tokenTextBuilder.append(termsList.get(i)+", ");
				}
			}
			tokenTextBuilder.append(")");
			return tokenTextBuilder.toString();
		}
		return "";
	}

	private void noMoreTokensToClassify() {
		ErrorDialog.showNoMoreTokensToClassify();
		onDone();
	}


	@FXML
	public void onAddTopic() {
		if(selectedAvailableTopic==null) {
			ErrorDialog.showErrorNoSelectedTopic();
			return;
		}
		addedTopicsList.add(selectedAvailableTopic);
		availableTopicsList.remove(selectedAvailableTopic);
		selectedAvailableTopic=null;
	}
	@FXML
	public void onRemoveTopic() {
		if(selectedAddedTopic==null) {
			ErrorDialog.showErrorNoSelectedTopic();
			return;
		}
		availableTopicsList.add(selectedAddedTopic);
		addedTopicsList.remove(selectedAddedTopic);
		selectedAddedTopic=null;
	}
	@FXML
	public void onClearTopics() {
		loadAvailableTopics();
	}
	/**
	 * @return the currentTokenText
	 */
	public Label getCurrentTokenText() {
		return currentTokenText;
	}


	/**
	 * @param currentTokenText the currentTokenText to set
	 */
	public void setCurrentTokenText(Label currentTokenText) {
		this.currentTokenText = currentTokenText;
	}


	/**
	 * @return the categoriesChoiceBox
	 */
	public ChoiceBox<String> getCategoriesChoiceBox() {
		return categoriesChoiceBox;
	}


	/**
	 * @param categoriesChoiceBox the categoriesChoiceBox to set
	 */
	public void setCategoriesChoiceBox(ChoiceBox<String> categoriesChoiceBox) {
		this.categoriesChoiceBox = categoriesChoiceBox;
	}


	/**
	 * @return the availableTopicsTable
	 */
	public TableView<TweetExtractorTopic> getAvailableTopicsTable() {
		return availableTopicsTable;
	}


	/**
	 * @param availableTopicsTable the availableTopicsTable to set
	 */
	public void setAvailableTopicsTable(TableView<TweetExtractorTopic> availableTopicsTable) {
		this.availableTopicsTable = availableTopicsTable;
	}


	/**
	 * @return the availableTopicsColumn
	 */
	public TableColumn<TweetExtractorTopic, String> getAvailableTopicsColumn() {
		return availableTopicsColumn;
	}


	/**
	 * @param availableTopicsColumn the availableTopicsColumn to set
	 */
	public void setAvailableTopicsColumn(TableColumn<TweetExtractorTopic, String> availableTopicsColumn) {
		this.availableTopicsColumn = availableTopicsColumn;
	}


	/**
	 * @return the addedTopicsTable
	 */
	public TableView<TweetExtractorTopic> getAddedTopicsTable() {
		return addedTopicsTable;
	}


	/**
	 * @param addedTopicsTable the addedTopicsTable to set
	 */
	public void setAddedTopicsTable(TableView<TweetExtractorTopic> addedTopicsTable) {
		this.addedTopicsTable = addedTopicsTable;
	}


	/**
	 * @return the addedTopicsColumn
	 */
	public TableColumn<TweetExtractorTopic, String> getAddedTopicsColumn() {
		return addedTopicsColumn;
	}


	/**
	 * @param addedTopicsColumn the addedTopicsColumn to set
	 */
	public void setAddedTopicsColumn(TableColumn<TweetExtractorTopic, String> addedTopicsColumn) {
		this.addedTopicsColumn = addedTopicsColumn;
	}


	/**
	 * @return the addedTopicsList
	 */
	public ObservableList<TweetExtractorTopic> getAddedTopicsList() {
		return addedTopicsList;
	}


	/**
	 * @param addedTopicsList the addedTopicsList to set
	 */
	public void setAddedTopicsList(ObservableList<TweetExtractorTopic> addedTopicsList) {
		this.addedTopicsList = addedTopicsList;
	}


	/**
	 * @return the availableTopicsList
	 */
	public ObservableList<TweetExtractorTopic> getAvailableTopicsList() {
		return availableTopicsList;
	}


	/**
	 * @param availableTopicsList the availableTopicsList to set
	 */
	public void setAvailableTopicsList(ObservableList<TweetExtractorTopic> availableTopicsList) {
		this.availableTopicsList = availableTopicsList;
	}


	/**
	 * @return the availableCategories
	 */
	public List<TweetExtractorNamedEntity> getAvailableCategories() {
		return availableCategories;
	}


	/**
	 * @param availableCategories the availableCategories to set
	 */
	public void setAvailableCategories(List<TweetExtractorNamedEntity> availableCategories) {
		this.availableCategories = availableCategories;
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
	}


	/**
	 * @return the tokenSetID
	 */
	public TweetExtractorNERTokenSetID getTokenSetID() {
		return tokenSetID;
	}


	/**
	 * @param tokenSetID the tokenSetID to set
	 */
	public void setTokenSetID(TweetExtractorNERTokenSetID tokenSetID) {
		this.tokenSetID = tokenSetID;
	}


	/**
	 * @return the selectedAvailableTopic
	 */
	public TweetExtractorTopic getSelectedAvailableTopic() {
		return selectedAvailableTopic;
	}


	/**
	 * @param selectedAvailableTopic the selectedAvailableTopic to set
	 */
	public void setSelectedAvailableTopic(TweetExtractorTopic selectedAvailableTopic) {
		this.selectedAvailableTopic = selectedAvailableTopic;
	}


	/**
	 * @return the selectedAddedTopic
	 */
	public TweetExtractorTopic getSelectedAddedTopic() {
		return selectedAddedTopic;
	}


	/**
	 * @param selectedAddedTopic the selectedAddedTopic to set
	 */
	public void setSelectedAddedTopic(TweetExtractorTopic selectedAddedTopic) {
		this.selectedAddedTopic = selectedAddedTopic;
	}


	/**
	 * @return the currentToken
	 */
	public TweetExtractorNERToken getCurrentToken() {
		return currentToken;
	}


	/**
	 * @param currentToken the currentToken to set
	 */
	public void setCurrentToken(TweetExtractorNERToken currentToken) {
		this.currentToken = currentToken;
	}


	/**
	 * @return the selectedCategory
	 */
	public TweetExtractorNamedEntity getSelectedCategory() {
		return selectedCategory;
	}


	/**
	 * @param selectedCategory the selectedCategory to set
	 */
	public void setSelectedCategory(TweetExtractorNamedEntity selectedCategory) {
		this.selectedCategory = selectedCategory;
	}


	/**
	 * @return the tokenList
	 */
	public List<TweetExtractorNERToken> getTokenList() {
		return tokenList;
	}


	/**
	 * @param tokenList the tokenList to set
	 */
	public void setTokenList(List<TweetExtractorNERToken> tokenList) {
		this.tokenList = tokenList;
	}
	
}
