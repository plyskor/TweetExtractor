/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics.config;

import java.text.DecimalFormat;

import es.uam.eps.tweetextractor.analytics.graphics.constructor.TweetExtractorChartConstructor;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.analytics.reports.graphics.MyGraphicsControl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author jose
 *
 */
public class DatasetConfigurationControl extends SpecificGraphicChartPreferencesController {
	private String nextFXMLscreen="";
	
	private Class<?> nextControllerClazz;
	@FXML
	private TextField categoryLabelTextField;
	@FXML
	private ColorPicker plotColorPicker;
	@FXML
	private ChoiceBox<String> plotTypeChoiceBox;
	@FXML
	private TableView<PlotStrokeConfiguration> categoryTable;
	@FXML
	private TableColumn<PlotStrokeConfiguration, String> categoryNameColumn;
	@FXML
	private Slider strokeWidthSlider;
	@FXML
	private Text widthText;

	private PlotStrokeConfiguration selectedCategory = null;

	private ObservableList<PlotStrokeConfiguration> categoryConfigList = FXCollections.observableArrayList();
	
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");
	/**
	 * 
	 */

	public DatasetConfigurationControl() {
		super();
	}

	@FXML
	private void initialize() {
		categoryNameColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoryName()));
		categoryTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedCategory(newValue));
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
		categoryTable.setItems(categoryConfigList);
		strokeWidthSlider.valueProperty()
				.addListener((observable, oldValue, newValue) -> widthText.setText("" + decimalFormat.format(newValue.floatValue())));
		widthText.setText("1.00");
		plotTypeChoiceBox.getItems().setAll(Constants.STROKE_TYPE_MAP.values());
	}

	@FXML
	public void onBack() {
		switch(this.getChartTypeInput()) {
		case TSC:
			nextFXMLscreen="view/analytics/reports/graphics/config/XYChartGraphicPreferences.fxml";
			nextControllerClazz=XYChartGraphicPreferencesControl.class;
			break;
		case BXYC:
			break;
		default:
			break;
		}
		this.getMainApplication().showSpecificGraphicChartConfiguration(nextFXMLscreen,nextControllerClazz, this.getChartTypeInput(), this.getReportInput(), this.getPreferencesInput(), this.getChart());
	}

	@FXML
	public void onDone() {
		TweetExtractorChartConstructor constructor;
		switch(this.getChartTypeInput()) {
		case TSC:
			nextFXMLscreen="view/analytics/reports/graphics/config/MyGraphics.fxml";
			nextControllerClazz=MyGraphicsControl.class;
			constructor= new TweetExtractorChartConstructor(this.getReportInput().constructIntervalXYDataset(selectedCategory.getCategoryLabel()), this.getPreferencesInput());
			break;
		case BXYC:
			break;
		default:
			break;
		}
		this.getMainApplication().showScreenInCenterOfRootLayout(nextFXMLscreen);
	
	}

	/**
	 * @return the categoryLabelTextField
	 */
	public TextField getCategoryLabelTextField() {
		return categoryLabelTextField;
	}

	/**
	 * @param categoryLabelTextField the categoryLabelTextField to set
	 */
	public void setCategoryLabelTextField(TextField categoryLabelTextField) {
		this.categoryLabelTextField = categoryLabelTextField;
	}

	/**
	 * @return the plotColorPicker
	 */
	public ColorPicker getPlotColorPicker() {
		return plotColorPicker;
	}

	/**
	 * @param plotColorPicker the plotColorPicker to set
	 */
	public void setPlotColorPicker(ColorPicker plotColorPicker) {
		this.plotColorPicker = plotColorPicker;
	}

	/**
	 * @return the plotTypeChoiceBox
	 */
	public ChoiceBox<String> getPlotTypeChoiceBox() {
		return plotTypeChoiceBox;
	}

	/**
	 * @param plotTypeChoiceBox the plotTypeChoiceBox to set
	 */
	public void setPlotTypeChoiceBox(ChoiceBox<String> plotTypeChoiceBox) {
		this.plotTypeChoiceBox = plotTypeChoiceBox;
	}

	/**
	 * @return the categoryTable
	 */
	public TableView<PlotStrokeConfiguration> getCategoryTable() {
		return categoryTable;
	}

	/**
	 * @param categoryTable the categoryTable to set
	 */
	public void setCategoryTable(TableView<PlotStrokeConfiguration> categoryTable) {
		this.categoryTable = categoryTable;
	}

	/**
	 * @return the categoryNameColumn
	 */
	public TableColumn<PlotStrokeConfiguration, String> getCategoryNameColumn() {
		return categoryNameColumn;
	}

	/**
	 * @param categoryNameColumn the categoryNameColumn to set
	 */
	public void setCategoryNameColumn(TableColumn<PlotStrokeConfiguration, String> categoryNameColumn) {
		this.categoryNameColumn = categoryNameColumn;
	}

	/**
	 * @return the categoryConfigList
	 */
	public ObservableList<PlotStrokeConfiguration> getCategoryConfigList() {
		return categoryConfigList;
	}

	/**
	 * @param categoryConfigList the categoryConfigList to set
	 */
	public void setCategoryConfigList(ObservableList<PlotStrokeConfiguration> categoryConfigList) {
		this.categoryConfigList = categoryConfigList;
	}

	/**
	 * @return the strokeWidthSlider
	 */
	public Slider getStrokeWidthSlider() {
		return strokeWidthSlider;
	}

	/**
	 * @param strokeWidthSlider the strokeWidthSlider to set
	 */
	public void setStrokeWidthSlider(Slider strokeWidthSlider) {
		this.strokeWidthSlider = strokeWidthSlider;
	}

	/**
	 * @return the selectedCategory
	 */
	public PlotStrokeConfiguration getSelectedCategory() {
		return selectedCategory;
	}

	/**
	 * @return the widthText
	 */
	public Text getWidthText() {
		return widthText;
	}

	/**
	 * @param widthText the widthText to set
	 */
	public void setWidthText(Text widthText) {
		this.widthText = widthText;
	}

	/**
	 * @param selectedCategory the selectedCategory to set
	 */
	public void setSelectedCategory(PlotStrokeConfiguration selectedCategory) {
		updateCategory();
		this.selectedCategory = selectedCategory;
		loadCategory();
	}

	private void loadCategory() {
		if (selectedCategory != null) {
			plotTypeChoiceBox.getSelectionModel()
					.select(Constants.STROKE_TYPE_MAP.get(selectedCategory.getStrokeType()));
			categoryLabelTextField.setText(selectedCategory.getCategoryLabel());
			plotColorPicker.setValue(Color.web(selectedCategory.getHexLineColour()));
			widthText.setText("" + selectedCategory.getStrokeWidth());
			strokeWidthSlider.setValue((double) selectedCategory.getStrokeWidth());
		}
	}

	private void updateCategory() {
		if(selectedCategory != null) {
			selectedCategory.setCategoryLabel(categoryLabelTextField.getText());
			selectedCategory.setHexLineColour(TweetExtractorUtils.colorToHex(plotColorPicker.getValue()));
			selectedCategory.setStrokeType(Constants.STROKE_TYPE_MAP.get(plotTypeChoiceBox.getValue()));
			selectedCategory.setStrokeWidth((float)strokeWidthSlider.getValue());
		}
	}

	@Override
	public void setChart(AnalyticsReportImage chart) {
		super.setChart(chart);
		if (chart != null) {
			this.categoryConfigList.setAll(this.getChart().getPlotStrokeConfiguration());
			categoryTable.getSelectionModel().selectFirst();
		}

	}

	/**
	 * @return the nextFXMLscreen
	 */
	public String getNextFXMLscreen() {
		return nextFXMLscreen;
	}

	/**
	 * @param nextFXMLscreen the nextFXMLscreen to set
	 */
	public void setNextFXMLscreen(String nextFXMLscreen) {
		this.nextFXMLscreen = nextFXMLscreen;
	}

	/**
	 * @return the nextControllerClazz
	 */
	public Class<?> getNextControllerClazz() {
		return nextControllerClazz;
	}

	/**
	 * @param nextControllerClazz the nextControllerClazz to set
	 */
	public void setNextControllerClazz(Class<?> nextControllerClazz) {
		this.nextControllerClazz = nextControllerClazz;
	}
	
}
