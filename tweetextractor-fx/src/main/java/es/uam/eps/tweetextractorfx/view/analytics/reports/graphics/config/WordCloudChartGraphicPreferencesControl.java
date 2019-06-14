/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics.config;

import java.io.File;
import java.util.List;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportImageServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorChartGraphicPreferencesServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.WorldCloudChartConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsNLPReport;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.task.GenerateWordCloudChartTask;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author jose
 *
 */
public class WordCloudChartGraphicPreferencesControl extends TweetExtractorFXController {
	private TweetExtractorChartGraphicPreferencesServiceInterface prefsService;
    private AnalyticsReportImageServiceInterface chartService;
	@FXML
	private Slider paddingSlider;
	@FXML
	private Slider minimumFontSizeSlider;
	@FXML
	private Slider maximumFontSizeSlider;
	@FXML
	private Slider numberOfWordsSlider;
	@FXML
	private Slider minimumWordLengthSlider;
	@FXML
	private Text paddingText;
	@FXML
	private Text minimumFontSizeText;
	@FXML
	private Text maximumFontSizeText;
	@FXML
	private Text numberOfWordsText;
	@FXML
	private Text minimumWordLengthText;
	@FXML
	private ToggleButton pixelBoundaryChoice;
	@FXML
	private ToggleButton circularChoice;
	@FXML
	private ToggleButton rectangularChoice;
	private ToggleGroup toggleGroup = new ToggleGroup();
	@FXML
	private ListView<String> colorsListView;
	private ObservableList<String> colorsNameList = FXCollections.observableArrayList();
	private String selectedColor = null;
	@FXML
	private ColorPicker colorPicker;
	private WorldCloudChartConfiguration preferences;
	private AnalyticsCategoryReport reportInput;
	private AnalyticsReportImage chart;
	private Stage loadingDialog = null;
	private Alert alertGeneration=null; 

	/**
	 * 
	 */
	public WordCloudChartGraphicPreferencesControl() {
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
		prefsService = this.mainApplication.getSpringContext()
				.getBean(TweetExtractorChartGraphicPreferencesServiceInterface.class);
		chartService = this.mainApplication.getSpringContext().getBean(AnalyticsReportImageServiceInterface.class);
		List<TweetExtractorChartGraphicPreferences> existingPrefs = prefsService
				.findByUserAndChartType(this.getMainApplication().getCurrentUser(), AnalyticsReportImageTypes.WCC);
		if (existingPrefs == null || existingPrefs.isEmpty()) {
			preferences = new WorldCloudChartConfiguration();
			switch(reportInput.getReportType()) {
			case TRWR:
				TrendsReport trendsReport = (TrendsReport) reportInput;
				preferences.setnWords(trendsReport.getN());
				break;
			case TVT:
			case TVNE:
				AnalyticsNLPReport nlpReport = (AnalyticsNLPReport)reportInput;
				preferences.setnWords(nlpReport.getSizePositiveValue());
				preferences.setMinWordLength(2);
				numberOfWordsSlider.setDisable(true);
				minimumWordLengthSlider.setDisable(true);
				break;
			default:
				break;
			}
		} else {
			preferences = new WorldCloudChartConfiguration();
			preferences.setUser(this.mainApplication.getCurrentUser());
			preferences.setChartType(AnalyticsReportImageTypes.WCC);
			switch(reportInput.getReportType()) {
				case TRWR:
					TrendsReport trendsReport = (TrendsReport) reportInput;
					preferences.setnWords(trendsReport.getN());
					break;
				case TVT:
				case TVNE:
					AnalyticsNLPReport nlpReport = (AnalyticsNLPReport)reportInput;
					preferences.setnWords(nlpReport.getSizePositiveValue());
					preferences.setMinWordLength(2);
					numberOfWordsSlider.setDisable(true);
					minimumWordLengthSlider.setDisable(true);
					break;
				default:
					break;
			}
			prefsService.persist(preferences);
		}
		colorsListView.setItems(colorsNameList);
		pixelBoundaryChoice.setToggleGroup(toggleGroup);
		circularChoice.setToggleGroup(toggleGroup);
		rectangularChoice.setToggleGroup(toggleGroup);
		loadFromPreferences();
	}

	@FXML
	public void initialize() {
		colorsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			saveColorPickerValue();
			setSelectedColor(newValue);
			loadColorPickerValue();
		});
		paddingSlider.valueProperty().addListener(
				(observable, oldValue, newValue) -> paddingText.setText("Padding :" + newValue.intValue()));
		minimumFontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (maximumFontSizeSlider.getValue() <= newValue.intValue()) {
				maximumFontSizeSlider.setValue(newValue.doubleValue() + 1);
				maximumFontSizeText.setText("Maximum font size: " + newValue.intValue());
			}
			maximumFontSizeSlider.setMin(newValue.doubleValue() + 1);
			minimumFontSizeText.setText("Minimum font size: " + newValue.intValue());
		});
		maximumFontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> maximumFontSizeText
				.setText("Maximum font size: " + newValue.intValue()));
		numberOfWordsSlider.valueProperty().addListener((observable, oldValue, newValue) -> numberOfWordsText
				.setText("Number of words :" + newValue.intValue()));
		minimumWordLengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> minimumWordLengthText
				.setText("Minimum word length :" + newValue.intValue()));

	}

	private void loadFromPreferences() {
		if (preferences != null) {
			paddingSlider.setValue(preferences.getPadding());
			paddingText.setText("Padding :" + preferences.getPadding());
			minimumFontSizeSlider.setValue(preferences.getFontMin());
			maximumFontSizeSlider.setMin((double)preferences.getFontMin() + 1);
			minimumFontSizeText.setText("Minimum font size: " + preferences.getFontMin());
			maximumFontSizeSlider.setValue(preferences.getFontMax());
			maximumFontSizeText.setText("Maximum font size: " + preferences.getFontMax());
			numberOfWordsSlider.setValue(preferences.getnWords());
			numberOfWordsText.setText("Number of words :" + preferences.getnWords());
			minimumWordLengthSlider.setValue(preferences.getMinWordLength());
			minimumWordLengthText.setText("Minimum word length :" + preferences.getMinWordLength());
			switch (preferences.getType()) {
			case Constants.WCC_CIRCULAR:
				circularChoice.setSelected(true);
				break;
			case Constants.WCC_PIXEL_BOUNDARY:
				pixelBoundaryChoice.setSelected(true);
				break;
			case Constants.WCC_RECTANGULAR:
				rectangularChoice.setSelected(true);
				break;
			default:
				break;
			}
			loadColorsList();
		}
	}

	private void updatePreferencesFromView() {
		if (preferences != null) {
			preferences.setPadding((int) paddingSlider.getValue());
			preferences.setFontMin((int) minimumFontSizeSlider.getValue());
			preferences.setFontMax((int) maximumFontSizeSlider.getValue());
			preferences.setnWords((int) numberOfWordsSlider.getValue());
			preferences.setMinWordLength((int) minimumWordLengthSlider.getValue());
			saveColorPickerValue();
			if (toggleGroup.getSelectedToggle().equals(circularChoice)) {
				preferences.setType(Constants.WCC_CIRCULAR);
			} else if (toggleGroup.getSelectedToggle().equals(pixelBoundaryChoice)) {
				preferences.setType(Constants.WCC_PIXEL_BOUNDARY);
			} else if (toggleGroup.getSelectedToggle().equals(rectangularChoice)) {
				preferences.setType(Constants.WCC_RECTANGULAR);
			}
			prefsService.saveOrUpdate(preferences);
		}
	}

	private void loadColorPickerValue() {
		if (selectedColor != null) {
			colorPicker.setValue(Color.web(preferences.getColorList().get(getSelectedColorIndex())));
		}
	}

	private void saveColorPickerValue() {
		if (selectedColor != null) {
			int index = getSelectedColorIndex();
			preferences.getColorList().remove(index);
			preferences.getColorList().add(index, TweetExtractorUtils.colorToHex(colorPicker.getValue()));
		}
	}

	@FXML
	public void onBack() {
		this.mainApplication.showCompatibleReportSelection(AnalyticsReportImageTypes.WCC);
	}

	@FXML
	public void onDone() {
		updatePreferencesFromView();
		File pixelBoundaryFile=null;
		if (preferences.getType() == Constants.WCC_PIXEL_BOUNDARY) {
			pixelBoundaryFile=selectPixelBoundaryFile();
			if (pixelBoundaryFile == null) {
				ErrorDialog.showErrorNoSelectedPixelBoundary();
				return;
			}
		}
		if (this.getChart()==null) {
			initilizeNewChart();
		}
		GenerateWordCloudChartTask generateTask = new GenerateWordCloudChartTask(this.getMainApplication().getSpringContext(),getReportInput(),getChart(),AnalyticsReportImageTypes.WCC,preferences,pixelBoundaryFile);
		generateTask.setOnSucceeded(e -> {
			if (loadingDialog != null) {
				loadingDialog.close();
			}	
			chartService.saveOrUpdate(getChart());
			alertGeneration=generateTask.getValue()==Constants.SUCCESS ? ErrorDialog.showSuccessCreateGraphicChart(getChart().getId()):ErrorDialog.showErrorGeneratingWordCloud();
		});
		generateTask.setOnFailed(e -> {
			if (loadingDialog != null) {
				loadingDialog.close();
			}
			alertGeneration=ErrorDialog.showErrorGeneratingWordCloud();
		});
		Thread thread = new Thread(generateTask);
		thread.setName(generateTask.getClass().getCanonicalName());
		thread.start();
		loadingDialog = mainApplication.showLoadingDialog("Generating word cloud...");
		loadingDialog.showAndWait();
        if(alertGeneration!=null)alertGeneration.showAndWait();
		this.mainApplication.showScreenInCenterOfRootLayout("view/analytics/reports/graphics/MyGraphics.fxml");
	}

	@FXML
	public void onAddColor() {
		int size = preferences.getColorList().size();
		if (size == 9) {
			return;
		}
		String newWhite = "#FFFFFF";
		preferences.getColorList().add(newWhite);
		colorsNameList.add("Color " + (size + 1));

	}

	@FXML
	public void onRemoveColor() {
		if (selectedColor != null) {
			int index = getSelectedColorIndex();
			preferences.getColorList().remove(index);
			setSelectedColor(null);
			loadColorsList();
		}
	}

	public void loadColorsList() {
		colorsNameList.clear();
		for (String hexColor : preferences.getColorList()) {
			String view = "Color " + (preferences.getColorList().indexOf(hexColor) + 1);
			colorsNameList.add(view);
		}
	}

	public int getSelectedColorIndex() {
		if (selectedColor != null) {
			return (Integer.parseInt(selectedColor.substring(6, 7))) - 1;
		}
		return -1;
	}
	public File selectPixelBoundaryFile() {
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilterJPEG = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)","*.jpeg");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
		fileChooser.getExtensionFilters().addAll(extFilterJPEG, extFilterPNG, extFilterJPG);
		// Show save file dialog
		return fileChooser.showOpenDialog(this.mainApplication.getPrimaryStage());
	}
	public void initilizeNewChart() {
		AnalyticsReportImage newChart = new AnalyticsReportImage();
		newChart.setReport(getReportInput());
		newChart.setChartType(AnalyticsReportImageTypes.WCC);
		newChart.setPlotStrokeConfiguration(TweetExtractorUtils.initializePlotStrokeConfiguration(this.getReportInput(), getChart()));
		this.getReportInput().getGraphics().add(newChart);
		this.setChart(newChart);
	}
	/**
	 * @return the paddingSlider
	 */
	public Slider getPaddingSlider() {
		return paddingSlider;
	}

	/**
	 * @param paddingSlider the paddingSlider to set
	 */
	public void setPaddingSlider(Slider paddingSlider) {
		this.paddingSlider = paddingSlider;
	}

	/**
	 * @return the minimumFontSizeSlider
	 */
	public Slider getMinimumFontSizeSlider() {
		return minimumFontSizeSlider;
	}

	/**
	 * @param minimumFontSizeSlider the minimumFontSizeSlider to set
	 */
	public void setMinimumFontSizeSlider(Slider minimumFontSizeSlider) {
		this.minimumFontSizeSlider = minimumFontSizeSlider;
	}

	/**
	 * @return the maximumFontSizeSlider
	 */
	public Slider getMaximumFontSizeSlider() {
		return maximumFontSizeSlider;
	}

	/**
	 * @param maximumFontSizeSlider the maximumFontSizeSlider to set
	 */
	public void setMaximumFontSizeSlider(Slider maximumFontSizeSlider) {
		this.maximumFontSizeSlider = maximumFontSizeSlider;
	}

	/**
	 * @return the numberOfWordsSlider
	 */
	public Slider getNumberOfWordsSlider() {
		return numberOfWordsSlider;
	}

	/**
	 * @param numberOfWordsSlider the numberOfWordsSlider to set
	 */
	public void setNumberOfWordsSlider(Slider numberOfWordsSlider) {
		this.numberOfWordsSlider = numberOfWordsSlider;
	}

	/**
	 * @return the minimumWordLengthSlider
	 */
	public Slider getMinimumWordLengthSlider() {
		return minimumWordLengthSlider;
	}

	/**
	 * @param minimumWordLengthSlider the minimumWordLengthSlider to set
	 */
	public void setMinimumWordLengthSlider(Slider minimumWordLengthSlider) {
		this.minimumWordLengthSlider = minimumWordLengthSlider;
	}

	/**
	 * @return the paddingText
	 */
	public Text getPaddingText() {
		return paddingText;
	}

	/**
	 * @param paddingText the paddingText to set
	 */
	public void setPaddingText(Text paddingText) {
		this.paddingText = paddingText;
	}

	/**
	 * @return the minimumFontSizeText
	 */
	public Text getMinimumFontSizeText() {
		return minimumFontSizeText;
	}

	/**
	 * @param minimumFontSizeText the minimumFontSizeText to set
	 */
	public void setMinimumFontSizeText(Text minimumFontSizeText) {
		this.minimumFontSizeText = minimumFontSizeText;
	}

	/**
	 * @return the maximumFontSizeText
	 */
	public Text getMaximumFontSizeText() {
		return maximumFontSizeText;
	}

	/**
	 * @param maximumFontSizeText the maximumFontSizeText to set
	 */
	public void setMaximumFontSizeText(Text maximumFontSizeText) {
		this.maximumFontSizeText = maximumFontSizeText;
	}

	/**
	 * @return the numberOfWordsText
	 */
	public Text getNumberOfWordsText() {
		return numberOfWordsText;
	}

	/**
	 * @param numberOfWordsText the numberOfWordsText to set
	 */
	public void setNumberOfWordsText(Text numberOfWordsText) {
		this.numberOfWordsText = numberOfWordsText;
	}

	/**
	 * @return the minimumWordLengthText
	 */
	public Text getMinimumWordLengthText() {
		return minimumWordLengthText;
	}

	/**
	 * @param minimumWordLengthText the minimumWordLengthText to set
	 */
	public void setMinimumWordLengthText(Text minimumWordLengthText) {
		this.minimumWordLengthText = minimumWordLengthText;
	}

	/**
	 * @return the pixelBoundaryChoice
	 */
	public ToggleButton getPixelBoundaryChoice() {
		return pixelBoundaryChoice;
	}

	/**
	 * @param pixelBoundaryChoice the pixelBoundaryChoice to set
	 */
	public void setPixelBoundaryChoice(ToggleButton pixelBoundaryChoice) {
		this.pixelBoundaryChoice = pixelBoundaryChoice;
	}

	/**
	 * @return the circularChoice
	 */
	public ToggleButton getCircularChoice() {
		return circularChoice;
	}

	/**
	 * @param circularChoice the circularChoice to set
	 */
	public void setCircularChoice(ToggleButton circularChoice) {
		this.circularChoice = circularChoice;
	}

	/**
	 * @return the rectangularChoice
	 */
	public ToggleButton getRectangularChoice() {
		return rectangularChoice;
	}

	/**
	 * @param rectangularChoice the rectangularChoice to set
	 */
	public void setRectangularChoice(ToggleButton rectangularChoice) {
		this.rectangularChoice = rectangularChoice;
	}

	/**
	 * @return the toggleGroup
	 */
	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}

	/**
	 * @param toggleGroup the toggleGroup to set
	 */
	public void setToggleGroup(ToggleGroup toggleGroup) {
		this.toggleGroup = toggleGroup;
	}

	/**
	 * @return the colorsListView
	 */
	public ListView<String> getColorsListView() {
		return colorsListView;
	}

	/**
	 * @param colorsListView the colorsListView to set
	 */
	public void setColorsListView(ListView<String> colorsListView) {
		this.colorsListView = colorsListView;
	}

	/**
	 * @return the colorsNameList
	 */
	public ObservableList<String> getColorsNameList() {
		return colorsNameList;
	}

	/**
	 * @param colorsNameList the colorsNameList to set
	 */
	public void setColorsNameList(ObservableList<String> colorsNameList) {
		this.colorsNameList = colorsNameList;
	}

	/**
	 * @return the colorPicker
	 */
	public ColorPicker getColorPicker() {
		return colorPicker;
	}

	/**
	 * @param colorPicker the colorPicker to set
	 */
	public void setColorPicker(ColorPicker colorPicker) {
		this.colorPicker = colorPicker;
	}

	/**
	 * @return the prefsService
	 */
	public TweetExtractorChartGraphicPreferencesServiceInterface getPrefsService() {
		return prefsService;
	}

	/**
	 * @param prefsService the prefsService to set
	 */
	public void setPrefsService(TweetExtractorChartGraphicPreferencesServiceInterface prefsService) {
		this.prefsService = prefsService;
	}

	/**
	 * @return the selectedColor
	 */
	public String getSelectedColor() {
		return selectedColor;
	}

	/**
	 * @param selectedColor the selectedColor to set
	 */
	public void setSelectedColor(String selectedColor) {
		this.selectedColor = selectedColor;
	}

	/**
	 * @return the preferences
	 */
	public WorldCloudChartConfiguration getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(WorldCloudChartConfiguration preferences) {
		this.preferences = preferences;
	}

	/**
	 * @return the reportInput
	 */
	public AnalyticsCategoryReport getReportInput() {
		return reportInput;
	}

	/**
	 * @param reportInput the reportInput to set
	 */
	public void setReportInput(AnalyticsCategoryReport reportInput) {
		this.reportInput = reportInput;
	}

	/**
	 * @return the chart
	 */
	public AnalyticsReportImage getChart() {
		return chart;
	}

	/**
	 * @param chart the chart to set
	 */
	public void setChart(AnalyticsReportImage chart) {
		this.chart = chart;
	}

}
