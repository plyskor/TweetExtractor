package es.uam.eps.tweetextractorfx.view.extraction;

import java.io.IOException;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.filter.Filter;
import es.uam.eps.tweetextractor.model.filter.impl.*;
import es.uam.eps.tweetextractorfx.task.CreateExtractionTask;
import es.uam.eps.tweetextractorfx.util.FilterManager;
import es.uam.eps.tweetextractorfx.view.RootLayoutControl;
import es.uam.eps.tweetextractorfx.view.dialog.filter.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QueryConstructorControl {
	/* Reference to the MainApplication */
	private MainApplication mainApplication;
	@FXML
	private TableView<Filter> availableFiltersTable;
	@FXML
	private TableColumn<Filter, String> availableFiltersColumn;
	@FXML
	private TableView<Filter> addedFilterTable;
	@FXML
	private TableColumn<Filter, String> addedFiltersColumn;
	@FXML
    private ImageView logoView;

	/* Added filters to the Query */
	private ObservableList<Filter> addedFiltersList = FXCollections.observableArrayList();

	private Filter selectedAvailableFilter;
	
	private Extraction extraction;

	private Stage loadingDialog = null;
	/**
	 * @return the availableFiltersTable
	 */
	public TableView<Filter> getAvailableFiltersTable() {
		return availableFiltersTable;
	}

	/**
	 * @return the addedFilterTable
	 */
	public TableView<Filter> getAddedFilterTable() {
		return addedFilterTable;
	}

	/**
	 * @param addedFilterTable the addedFilterTable to set
	 */
	public void setAddedFilterTable(TableView<Filter> addedFilterTable) {
		this.addedFilterTable = addedFilterTable;
	}

	/**
	 * @return the addedFiltersColumn
	 */
	public TableColumn<Filter, String> getAddedFiltersColumn() {
		return addedFiltersColumn;
	}

	/**
	 * @return the extraction
	 */
	public Extraction getExtraction() {
		return extraction;
	}

	/**
	 * @return the logoView
	 */
	public ImageView getLogoView() {
		return logoView;
	}

	/**
	 * @param logoView the logoView to set
	 */
	public void setLogoView(ImageView logoView) {
		this.logoView = logoView;
	}

	/**
	 * @param extraction the extraction to set
	 */
	public void setExtraction(Extraction extraction) {
		this.extraction = extraction;
	}

	/**
	 * @param addedFiltersColumn the addedFiltersColumn to set
	 */
	public void setAddedFiltersColumn(TableColumn<Filter, String> addedFiltersColumn) {
		this.addedFiltersColumn = addedFiltersColumn;
	}

	/**
	 * @return the addedFiltersList
	 */
	public ObservableList<Filter> getAddedFiltersList() {
		return addedFiltersList;
	}

	/**
	 * @param addedFiltersList the addedFiltersList to set
	 */
	public void setAddedFiltersList(ObservableList<Filter> addedFiltersList) {
		this.addedFiltersList = addedFiltersList;
	}

	/**
	 * @param availableFiltersTable the availableFiltersTable to set
	 */
	public void setAvailableFiltersTable(TableView<Filter> availableFiltersTable) {
		this.availableFiltersTable = availableFiltersTable;
	}

	/**
	 * @return the availableFiltersColumn
	 */
	public TableColumn<Filter, String> getAvailableFiltersColumn() {
		return availableFiltersColumn;
	}

	/**
	 * @param availableFiltersColumn the availableFiltersColumn to set
	 */
	public void setAvailableFiltersColumn(TableColumn<Filter, String> availableFiltersColumn) {
		this.availableFiltersColumn = availableFiltersColumn;
	}

	/**
	 * @return the selectedAvailableFilter
	 */
	public Filter getSelectedAvailableFilter() {
		return selectedAvailableFilter;
	}

	/**
	 * @param selectedAvailableFilter the selectedAvailableFilter to set
	 */
	public void setSelectedAvailableFilter(Filter selectedAvailableFilter) {
		this.selectedAvailableFilter = selectedAvailableFilter;
	}

	/**
	 * @return the mainApplication
	 */
	public MainApplication getMainApplication() {
		return mainApplication;
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	public void setMainApplication(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
		availableFiltersTable.setItems(mainApplication.getAvailableFilters());
		addedFilterTable.setItems(addedFiltersList);
		// Necesario para operaciones lógicas
		addedFilterTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		availableFiltersColumn.setCellValueFactory(cellData -> cellData.getValue().getLABEL());
		addedFiltersColumn.setCellValueFactory(cellData -> cellData.getValue().getSummaryProperty());

		// Listen for selection changes and show the person details when changed.
		availableFiltersTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedAvailableFilter(newValue));

		selectedAvailableFilter = null;
	}

	@FXML
	public void handleAddFilter() {
		if (selectedAvailableFilter == null) {
			/* No se ha seleccionado ningún filtro para añadir a la lista */
			ErrorDialog.showErrorSelectFilterAdd();
		} else {
			/* Multiplexado de filtros para añadir */
			switch (selectedAvailableFilter.getClass().getCanonicalName()) {
			case (Constants.CLASS_FILTER_CONTAINS):
				showFilterContainsDialog();
				break;
			case (Constants.CLASS_FILTER_CONTAINS_EXACT):
				showFilterContainsExactDialog();
				break;
			case (Constants.CLASS_FILTER_SINCE):
				showFilterSinceDialog();
				break;
			case (Constants.CLASS_FILTER_UNTIL):
				showFilterUntilDialog();
				break;
			case (Constants.CLASS_FILTER_MENTION):
				showFilterMentionDialog();
				break;
			case (Constants.CLASS_FILTER_FROM):
				showFilterFromDialog();
				break;
			case (Constants.CLASS_FILTER_TO):
				showFilterToDialog();
				break;
			case (Constants.CLASS_FILTER_HASHTAG):
				showFilterHashtagDialog();
				break;
			case (Constants.CLASS_FILTER_URL):
				showFilterUrlDialog();
				break;
			case (Constants.CLASS_FILTER_LIST):
				showFilterListDialog();
				break;
			default:
				// ERROR: Unknown Filter
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Filtro desconocido");
				alert.setContentText("Se ha producido un error al añadir el filtro. Por favor, vuelva a intentarlo.");
				alert.showAndWait();
				break;
			}
		}
	}
	@FXML
	public void handleDeleteFilter() {
		if (addedFilterTable.getSelectionModel().getSelectedItems().size() == 0) {
			ErrorDialog.showErrorSelectFilterRemove();
		} else {
			addedFiltersList.removeAll(addedFilterTable.getSelectionModel().getSelectedItems());
		}
	}
	@FXML
	public void handleSaveQuery() {
		if(this.addedFiltersList.isEmpty()){
			ErrorDialog.showErrorEmptyExtraction();
		}else {
		extraction = new Extraction();
		extraction.addFilters(addedFiltersList);
		this.getMainApplication().getCurrentUser().addExtractionToList(extraction);
		CreateExtractionTask createTask = new CreateExtractionTask(extraction);
		createTask.setOnSucceeded(e->{
    		if(loadingDialog!=null)loadingDialog.close();
		});
		createTask.setOnFailed(e->{
    		if(loadingDialog!=null)loadingDialog.close();
		});
		Thread thread = new Thread(createTask);
        thread.setName(createTask.getClass().getCanonicalName());
        thread.start();
        loadingDialog=mainApplication.showLoadingDialog("Creating extraction...");    
        loadingDialog.showAndWait();
		this.getMainApplication().showExtractionDetails(extraction,true);
		}
	}

	@FXML
	public void handleOr() {
		if (addedFilterTable.getSelectionModel().getSelectedItems().size() < 2) {
			ErrorDialog.showErrorNotEnoughFilters();
		} else {
			FilterOr newFilter = new FilterOr();
			newFilter.addAll(addedFilterTable.getSelectionModel().getSelectedItems());
			addedFiltersList.removeAll(addedFilterTable.getSelectionModel().getSelectedItems());
			addedFiltersList.add(newFilter);
		}
	}

	@FXML
	public void handleNot() {
		if (addedFilterTable.getSelectionModel().getSelectedItems().size() == 0) {
			ErrorDialog.showErrorNotEnoughFiltersNot();
		} else {
			for (Filter filter : addedFilterTable.getSelectionModel().getSelectedItems()) {
				if(filter.getClass().getCanonicalName().equals(Constants.CLASS_FILTER_NOT)) {
					addedFiltersList.add(((FilterNot) filter).getFilter());
				}else {
					FilterNot newFilter = new FilterNot();
					newFilter.setFilter(filter);
					addedFiltersList.add(newFilter);
				}
			}
			addedFiltersList.removeAll(addedFilterTable.getSelectionModel().getSelectedItems());
		}
	}

	@FXML
	public void handleUndoLogic() {
		if (addedFilterTable.getSelectionModel().getSelectedItems().size() < 1) {
			ErrorDialog.showErrorUndoLogic();
		} else if(FilterManager.isFilterListLogic(addedFilterTable.getSelectionModel().getSelectedItems())){
			for (Filter filter : addedFilterTable.getSelectionModel().getSelectedItems()) {
				switch (filter.getClass().getCanonicalName()) {
				case (Constants.CLASS_FILTER_NOT):
					addedFiltersList.add(((FilterNot) filter).getFilter());
					break;
				case (Constants.CLASS_FILTER_OR):
					FilterOr filterOr = ((FilterOr) filter);
					for (Filter filterToUndo : filterOr.getFilterList()) {
						addedFiltersList.add(filterToUndo);
					}
					break;
				default:
					ErrorDialog.showErrorUndoLogic();
					break;
				}
			}
			addedFiltersList.removeAll(addedFilterTable.getSelectionModel().getSelectedItems());
		}else {
			ErrorDialog.showErrorUndoLogic();
		}
	}

	/*
	 * 
	 * FUNCIONES PARA MOSTRAR EL DIÁLOGO MODAL DE CADA FILTRO Y AÑADIRLO A LA LISTA
	 * DE FILTROS SELECCIONADOS
	 * 
	 */
	public void showFilterContainsDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterContainsDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the dialogStage to the controller.
			FilterContainsDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getKeywordsList()!=null&&!controller.getFilter().getKeywordsList().isEmpty()) {
				addedFiltersList.add(new FilterContains(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showFilterContainsExactDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					RootLayoutControl.class.getResource("dialog/filter/FilterContainsExactDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			FilterContainsExactDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getKeywordsList()!=null&&!controller.getFilter().getKeywordsList().isEmpty()) {
				addedFiltersList.add(new FilterContainsExact(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showFilterSinceDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterSinceDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			FilterSinceDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getDate()!=null) {
				addedFiltersList.add(new FilterSince(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showFilterUntilDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterUntilDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			FilterUntilDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getDate()!=null) {
				addedFiltersList.add(new FilterUntil(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showFilterMentionDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterMentionDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the dialogStage to the controller.
			FilterMentionDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getMentionList()!=null&&controller.getFilter().getMentionList().size()>0) {
				addedFiltersList.add(new FilterMention(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showFilterFromDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterFromDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			FilterFromDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getNickName()!=null&&!controller.getFilter().getNickName().isEmpty()) {
				addedFiltersList.add(new FilterFrom(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showFilterToDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterToDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			FilterToDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getNickName()!=null&&!controller.getFilter().getNickName().isEmpty()) {
				addedFiltersList.add(new FilterTo(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showFilterHashtagDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterHashtagDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			FilterHashtagDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getHashtagList()!=null&&controller.getFilter().getHashtagList().size()>0) {
				addedFiltersList.add(new FilterHashtag(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void showFilterUrlDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterUrlDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			FilterUrlDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&&controller.getFilter().getUrl()!=null&&!controller.getFilter().getUrl().isEmpty()) {
				addedFiltersList.add(new FilterUrl(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	public void showFilterListDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RootLayoutControl.class.getResource("dialog/filter/FilterListDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApplication.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the dialogStage to the controller.
			FilterListDialogControl controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it, then add filter
			dialogStage.showAndWait();
			if (controller.getFilter() != null&& controller.getFilter().getAccount() !=null && !controller.getFilter().getAccount().isEmpty()&&controller.getFilter().getListName()!=null&&!controller.getFilter().getListName().isEmpty()) {
				addedFiltersList.add(new FilterList(controller.getFilter()));
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
}
