/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.extraction;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.task.status.UpdateStatus;
import es.uam.eps.tweetextractorfx.task.ExportExtractionTask;
import es.uam.eps.tweetextractorfx.task.LoadTweetsTask;
import es.uam.eps.tweetextractorfx.task.TweetExtractorFXTask;
import es.uam.eps.tweetextractorfx.task.UpdateExtractionTask;
import es.uam.eps.tweetextractorfx.twitterapi.TwitterExtractor;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class ExtractionDetailsControl extends TweetExtractorFXController {
	@FXML
	private TableView<Tweet> tweetsTable;
	@FXML
	private TableColumn<Tweet, String> tweetsColumn;
	@FXML
	private Label authorLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label idLabel;
	@FXML
	private Label langLabel;
	@FXML
	private Label extractionIDLabel;
	@FXML
	private Label nTweetsLabel;
	@FXML
	private Label extCreatedOnLabel;
	@FXML
	private Label extLastUpdatedLabel;
	private Alert alertExport;
	private Tweet selectedQueryResult;
	private Extraction extraction;
	private Stage loadingDialog = null;
	private Alert alertUpdate;
	private ObservableList<Tweet> tweetObservableList = FXCollections.observableArrayList();
	private TwitterExtractor twitterextractor;

	/**
	 * 
	 */
	public ExtractionDetailsControl() {
		super();
	}

	@FXML
	private void initialize() {
		tweetsColumn.setCellFactory(tc -> {
			TableCell<Tweet, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			/* Cargamos la fuente emoji */
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream("OpenSansEmoji.ttf");
			Font openSansEmoji = null;
			try {
				openSansEmoji = Font.createFont(Font.TRUETYPE_FONT, stream);
			} catch (FontFormatException | IOException e) {
				Logger logger = LoggerFactory.getLogger(this.getClass());
				logger.warn(e.getMessage());
			}
			/* Ponemos la fuente en cada celda */
			if (openSansEmoji != null)
				cell.setFont(javafx.scene.text.Font.font(java.awt.Font.MONOSPACED, 35));

			text.wrappingWidthProperty().bind(tweetsColumn.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});
		tweetsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getText()));
		tweetsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedQueryResult(newValue));
	}

	/**
	 * @return the tweetsTable
	 */
	public TableView<Tweet> getTweetsTable() {
		return tweetsTable;
	}

	/**
	 * @param tweetsTable the tweetsTable to set
	 */
	public void setTweetsTable(TableView<Tweet> tweetsTable) {
		this.tweetsTable = tweetsTable;
	}

	/**
	 * @return the tweetObservableList
	 */
	public ObservableList<Tweet> getTweetObservableList() {
		return tweetObservableList;
	}

	/**
	 * @param tweetObservableList the tweetObservableList to set
	 */
	public void setTweetObservableList(ObservableList<Tweet> tweetObservableList) {
		this.tweetObservableList = tweetObservableList;
	}

	/**
	 * @return the selectedQueryResult
	 */
	public Tweet getSelectedQueryResult() {
		return selectedQueryResult;
	}

	/**
	 * @param selectedQueryResult the selectedQueryResult to set
	 */
	public void setSelectedQueryResult(Tweet selectedQueryResult) {
		this.selectedQueryResult = selectedQueryResult;
		if (selectedQueryResult != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			authorLabel.setText(selectedQueryResult.getUserScreenName());
			dateLabel.setText(df.format(selectedQueryResult.getCreatedAt()));
			idLabel.setText("" + selectedQueryResult.getId());
			Locale loc = new Locale(selectedQueryResult.getLang());
			langLabel.setText(loc.getDisplayLanguage(loc));
		} else {
			authorLabel.setText("");
			dateLabel.setText("");
			idLabel.setText("");
			langLabel.setText("");
		}
	}

	/**
	 * @return the tweetsColumn
	 */
	public TableColumn<Tweet, String> getTweetsColumn() {
		return tweetsColumn;
	}

	/**
	 * @param tweetsColumn the tweetsColumn to set
	 */
	public void setTweetsColumn(TableColumn<Tweet, String> tweetsColumn) {
		this.tweetsColumn = tweetsColumn;
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		this.getTweetsTable().setItems(this.tweetObservableList);
		authorLabel.setText("");
		dateLabel.setText("");
		idLabel.setText("");
		langLabel.setText("");
	}

	public void executeQuery() {
		twitterextractor = new TwitterExtractor(this.getMainApplication().getCurrentUser().getCredentialList().get(0),
				mainApplication.getSpringContext());
		UpdateExtractionTask updateTask = new UpdateExtractionTask(mainApplication.getSpringContext(),twitterextractor, extraction);
		updateTask.setOnSucceeded(e -> {
			if (loadingDialog != null)
				loadingDialog.close();
		});
		updateTask.setOnFailed(e -> {
			if (loadingDialog != null)
				loadingDialog.close();
		});
		Thread thread = new Thread(updateTask);
		thread.setName(updateTask.getClass().getCanonicalName());
		thread.start();
		loadingDialog = mainApplication.showLoadingDialog("Extracting");
		loadingDialog.showAndWait();
	}

	@FXML
	public void handleCancel() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/HomeScreen.fxml");
	}

	@FXML
	public void handleDelete() {
		if (this.selectedQueryResult == null) {
			ErrorDialog.showErrorNoTweetSelected();
			return;
		}
		this.getExtraction().getTweetList().remove(selectedQueryResult);
		TweetServiceInterface tweetService = mainApplication.getSpringContext().getBean(TweetServiceInterface.class);
		tweetService.deleteById(selectedQueryResult.getIdDB());
		tweetObservableList.remove(selectedQueryResult);
	}

	@FXML
	public void handleUpdateExtraction() {
		ExtractionServiceInterface eServ = mainApplication.getSpringContext().getBean(ExtractionServiceInterface.class);
		eServ.refresh(extraction);
		if (extraction.isExtracting()) {
			ErrorDialog.showErrorExtractionIsCurrentlyUpdating();
		}
		twitterextractor = new TwitterExtractor(this.getMainApplication().getCurrentUser().getCredentialList().get(0),
				mainApplication.getSpringContext());
		TweetExtractorFXTask<UpdateStatus> updateTask = new UpdateExtractionTask(mainApplication.getSpringContext(),twitterextractor, extraction);
		updateTask.setOnSucceeded(e -> {
			UpdateStatus result = updateTask.getValue();
			if (result == null)
				return;
			if (result.getnTweets() > 0) {
				this.tweetObservableList.addAll(result.getTweetList());
				try {
					ExtractionServiceInterface extractionService = mainApplication.getSpringContext()
							.getBean(ExtractionServiceInterface.class);
					extractionService.update(this.getExtraction());
					refreshExtractionLabels();
				} catch (Exception ex) {
					if (loadingDialog != null)
						loadingDialog.close();
					Logger logger = LoggerFactory.getLogger(this.getClass());
					logger.warn(ex.getMessage());
				}
			}
			if (loadingDialog != null)
				loadingDialog.close();
			alertUpdate = twitterextractor.onError();
			if (alertUpdate == null)
				alertUpdate = ErrorDialog.showUpdateQueryResults(result.getnTweets());

		});
		updateTask.setOnFailed(e -> {
			if (loadingDialog != null)
				loadingDialog.close();
		});
		Thread thread = new Thread(updateTask);
		thread.setName(updateTask.getClass().getCanonicalName());
		thread.start();
		loadingDialog = mainApplication.showLoadingDialog("Extracting...");
		loadingDialog.showAndWait();
		if (alertUpdate != null)
			alertUpdate.showAndWait();
	}

	/**
	 * @return the extraction
	 */
	public Extraction getExtraction() {
		return extraction;
	}
	public void refreshExtractionLabels() {
		if (extraction!=null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			extractionIDLabel.setText(""+extraction.getIdDB());
			nTweetsLabel.setText(""+extraction.getTweetList().size());
			extCreatedOnLabel.setText(df.format(extraction.getCreationDate()));
			extLastUpdatedLabel.setText(df.format(extraction.getLastModificationDate()));
		}else {
			extractionIDLabel.setText("");
			nTweetsLabel.setText("");
			extCreatedOnLabel.setText("");
			extLastUpdatedLabel.setText("");
		}
	}
	public void refreshTweetObservableList() {
		if (extraction != null && extraction.getFilterList() != null) {
			LoadTweetsTask loadTask = new LoadTweetsTask(mainApplication.getSpringContext(), extraction);
			loadTask.setOnSucceeded(e -> {
				this.tweetObservableList.clear();
				this.tweetObservableList.setAll(extraction.getTweetList());
				if (loadingDialog != null)
					loadingDialog.close();
			});
			loadTask.setOnFailed(e -> {
				if (loadingDialog != null)
					loadingDialog.close();
			});
			Thread thread = new Thread(loadTask);
			thread.setName(loadTask.getClass().getCanonicalName());
			thread.start();
			loadingDialog = mainApplication.showLoadingDialog("Loading tweets...");
			loadingDialog.showAndWait();
		}
	}

	/**
	 * @param extraction the extraction to set
	 */
	public void setExtraction(Extraction extraction) {
		this.extraction = extraction;
	}

	@FXML
	public void handleExport() {
		alertExport = null;
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		// Show save file dialog
		File file = fileChooser.showSaveDialog(this.mainApplication.getPrimaryStage());
		if (file != null) {
			ExportExtractionTask exportTask = new ExportExtractionTask(mainApplication.getSpringContext(),extraction, file);
			exportTask.setOnSucceeded(e -> {
				Integer status = exportTask.getValue();
				if (loadingDialog != null) {
					loadingDialog.close();
				}
				switch (status) {
				case (Constants.SUCCESS_EXPORT):
					alertExport = ErrorDialog.showSuccessExport();
					break;
				case (Constants.UNKNOWN_EXPORT_ERROR):
					alertExport = ErrorDialog.showErrorExportTweets(exportTask.getErrorMessage());
					break;
				default:
					break;
				}
			});
			exportTask.setOnFailed(e -> {
				if (loadingDialog != null) {
					loadingDialog.close();
				}
			});
			Thread thread = new Thread(exportTask);
			thread.setName(exportTask.getClass().getCanonicalName());
			thread.start();
			loadingDialog = mainApplication.showLoadingDialog("Exporting tweets...");
			loadingDialog.showAndWait();
			if (alertExport != null)
				alertExport.showAndWait();
		}
	}
}
