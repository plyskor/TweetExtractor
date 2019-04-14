/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics.config;

import java.awt.GraphicsEnvironment;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorChartGraphicPreferencesServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.CategoryBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.PieChartConfiguration;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;

/**
 * @author jose
 *
 */
public class ChartGraphicPreferencesControl extends TweetExtractorFXController {
	private static final String[] availablefonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getAvailableFontFamilyNames();

	private String nextFXMLscreen="";
	
	private Class<?> nextControllerClazz;
	
	private AnalyticsReportImageTypes chartTypeInput;

	private AnalyticsRepresentableReport reportInput;
	
	private Class<?> configClazz;

	private TweetExtractorChartGraphicPreferencesServiceInterface prefsService;
	@FXML
	private TableView<TweetExtractorChartGraphicPreferences> savedPrefsTable;
	@FXML
	private TableColumn<TweetExtractorChartGraphicPreferences, String> prefNameColumn;

	private ObservableList<TweetExtractorChartGraphicPreferences> savedPreferencesList = FXCollections
			.observableArrayList();

	private TweetExtractorChartGraphicPreferences selectedPref;
	@FXML
	private ChoiceBox<Integer> chartTitleFontSizeChoiceBox;
	@FXML
	private ChoiceBox<String> chartTitleFontTypeChoiceBox;
	@FXML
	private ChoiceBox<String> fontNameChoiceBox;
	@FXML
	private ChoiceBox<Integer> axisTitleFontSizeChoiceBox;
	@FXML
	private ChoiceBox<String> axisTitleFontTypeChoiceBox;
	@FXML
	private ChoiceBox<Integer> regularFontSizeChoiceBox;
	@FXML
	private ChoiceBox<String> regularFontTypeChoiceBox;
	@FXML
	private ColorPicker hexTitleColourPicker;
	@FXML
	private ColorPicker hexRangeGridLineColourPicker;
	@FXML
	private ColorPicker hexPlotBackgroundPaintColourPicker;
	@FXML
	private ColorPicker hexChartBackgroundPaintColourPicker;
	@FXML
	private ColorPicker hexGridBandPaintColourPicker;
	@FXML
	private ColorPicker hexAxisLabelColourPicker;
	@FXML
	private CheckBox legendCheckBox;
	@FXML
	private CheckBox toolTipsCheckBox;
	@FXML
	private CheckBox urlsCheckBox;

	private Logger logger = LoggerFactory.getLogger(ChartGraphicPreferencesControl.class);


	public ChartGraphicPreferencesControl() {
		super();
	}

	@FXML
	private void initialize() {
		prefNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		prefNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		savedPrefsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setSelectedPref(newValue);
		});
		prefNameColumn.setOnEditCommit((CellEditEvent<TweetExtractorChartGraphicPreferences, String> t) -> {
			TweetExtractorChartGraphicPreferences editedPref = t.getTableView().getItems()
					.get(t.getTablePosition().getRow());
			editedPref.setName(t.getNewValue());
			prefsService.saveOrUpdate(editedPref);
		});
	}

	private void loadConfiguration(TweetExtractorChartGraphicPreferences selectedPref) {
		if (selectedPref != null) {
			/* Load font preferences */
			String currentFont = isFontAvailable(selectedPref.getFontName()) ? selectedPref.getFontName() : "Palatino";
			fontNameChoiceBox.setValue(currentFont);
			chartTitleFontSizeChoiceBox.setValue(selectedPref.getTitleFontSize());
			chartTitleFontTypeChoiceBox
					.setValue(TweetExtractorUtils.getKeyFromMap(Constants.FONT_TYPES_MAP, selectedPref.getTitleFontType()));
			axisTitleFontSizeChoiceBox.setValue(selectedPref.getAxisTitleFontSize());
			axisTitleFontTypeChoiceBox
					.setValue(TweetExtractorUtils.getKeyFromMap(Constants.FONT_TYPES_MAP, selectedPref.getAxisTitleFontType()));
			regularFontSizeChoiceBox.setValue(selectedPref.getRegularFontSize());
			regularFontTypeChoiceBox
					.setValue(TweetExtractorUtils.getKeyFromMap(Constants.FONT_TYPES_MAP, selectedPref.getRegularFontType()));
			/* Load Color Preferences */
			hexTitleColourPicker.setValue(Color.web(selectedPref.getHexTitleColour()));
			hexRangeGridLineColourPicker.setValue(Color.web(selectedPref.getHexRangeGridLineColour()));
			hexPlotBackgroundPaintColourPicker.setValue(Color.web(selectedPref.getHexPlotBackgroundPaintColour()));
			hexChartBackgroundPaintColourPicker.setValue(Color.web(selectedPref.getHexChartBackgroundPaintColour()));
			hexGridBandPaintColourPicker.setValue(Color.web(selectedPref.getHexGridBandPaintColour()));
			hexAxisLabelColourPicker.setValue(Color.web(selectedPref.getHexAxisLabelColour()));
			/* Load general preferences */
			legendCheckBox.setSelected(selectedPref.isLegend());
			toolTipsCheckBox.setSelected(selectedPref.isTooltips());
			urlsCheckBox.setSelected(selectedPref.isUrls());
		}
	}
	private void updateConfiguration() {
		if (selectedPref != null) {
			/* Load font preferences */
			selectedPref.setFontName(fontNameChoiceBox.getValue());
			selectedPref.setTitleFontSize(chartTitleFontSizeChoiceBox.getValue());
			selectedPref.setTitleFontType(Constants.FONT_TYPES_MAP.get(chartTitleFontTypeChoiceBox.getValue()));
			selectedPref.setAxisTitleFontSize(axisTitleFontSizeChoiceBox.getValue());
			selectedPref.setAxisTitleFontType(Constants.FONT_TYPES_MAP.get(axisTitleFontTypeChoiceBox.getValue()));
			selectedPref.setRegularFontSize(regularFontSizeChoiceBox.getValue());
			selectedPref.setRegularFontType(Constants.FONT_TYPES_MAP.get(regularFontTypeChoiceBox.getValue()));
			/* Load Color Preferences */
			selectedPref.setHexTitleColour(TweetExtractorUtils.colorToHex(hexTitleColourPicker.getValue()));
			selectedPref.setHexRangeGridLineColour(TweetExtractorUtils.colorToHex(hexRangeGridLineColourPicker.getValue()));
			selectedPref.setHexChartBackgroundPaintColour(TweetExtractorUtils.colorToHex(hexChartBackgroundPaintColourPicker.getValue()));
			selectedPref.setHexGridBandPaintColour(TweetExtractorUtils.colorToHex(hexGridBandPaintColourPicker.getValue()));
			selectedPref.setHexAxisLabelColour(TweetExtractorUtils.colorToHex(hexAxisLabelColourPicker.getValue()));
			selectedPref.setHexPlotBackgroundPaintColour(TweetExtractorUtils.colorToHex(hexPlotBackgroundPaintColourPicker.getValue()));
			/* Load general preferences */
			selectedPref.setLegend(legendCheckBox.isSelected());
			selectedPref.setTooltips(toolTipsCheckBox.isSelected());
			selectedPref.setUrls(urlsCheckBox.isSelected());
		}
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		savedPrefsTable.setItems(savedPreferencesList);
		prefsService = this.mainApplication.getSpringContext()
				.getBean(TweetExtractorChartGraphicPreferencesServiceInterface.class);
		initializeChoiceBoxes();
		initializeConfigClazz();
		refreshSavedPrefsList();
		savedPrefsTable.getSelectionModel().selectFirst();
	}

	private void initializeConfigClazz() {
		switch (chartTypeInput) {
			case TSC:
				configClazz=XYChartGraphicPreferences.class;
				nextFXMLscreen="view/analytics/reports/graphics/config/XYChartGraphicPreferences.fxml";
				nextControllerClazz=XYChartGraphicPreferencesControl.class;
				break;
			case P3DCH:
			case PCH:
				configClazz=PieChartConfiguration.class;
				nextFXMLscreen="view/analytics/reports/graphics/config/DatasetConfiguration.fxml";
				nextControllerClazz=DatasetConfigurationControl.class;
				break;
			case WCC:
				break;
			case BARC:
				configClazz=CategoryBarChartGraphicPreferences.class;
				nextFXMLscreen="view/analytics/reports/graphics/config/CategoryBarChartGraphicPreferences.fxml";
				nextControllerClazz=CategoryBarChartGraphicPreferencesControl.class;
				break;
			case BXYC:
				configClazz=XYBarChartGraphicPreferences.class;
				nextFXMLscreen="view/analytics/reports/graphics/config/XYBarChartGraphicPreferences.fxml";
				nextControllerClazz=XYBarChartGraphicPreferencesControl.class;
				break;
			default:
				break;
		}
	}

	private void initializeChoiceBoxes() {
		for (String font : availablefonts) {
			fontNameChoiceBox.getItems().add(font);
		}
		for (int i = 7; i <= 72; i++) {
			chartTitleFontSizeChoiceBox.getItems().add(i);
			axisTitleFontSizeChoiceBox.getItems().add(i);
			regularFontSizeChoiceBox.getItems().add(i);
		}
		chartTitleFontTypeChoiceBox.getItems().addAll(Constants.FONT_TYPES_MAP.keySet());
		axisTitleFontTypeChoiceBox.getItems().addAll(Constants.FONT_TYPES_MAP.keySet());
		regularFontTypeChoiceBox.getItems().addAll(Constants.FONT_TYPES_MAP.keySet());
	}

	/**
	 * @return the savedPrefsTable
	 */
	public TableView<TweetExtractorChartGraphicPreferences> getSavedPrefsTable() {
		return savedPrefsTable;
	}

	/**
	 * @param savedPrefsTable the savedPrefsTable to set
	 */
	public void setSavedPrefsTable(TableView<TweetExtractorChartGraphicPreferences> savedPrefsTable) {
		this.savedPrefsTable = savedPrefsTable;
	}

	/**
	 * @return the prefNameColumn
	 */
	public TableColumn<TweetExtractorChartGraphicPreferences, String> getPrefNameColumn() {
		return prefNameColumn;
	}

	/**
	 * @param prefNameColumn the prefNameColumn to set
	 */
	public void setPrefNameColumn(TableColumn<TweetExtractorChartGraphicPreferences, String> prefNameColumn) {
		this.prefNameColumn = prefNameColumn;
	}

	/**
	 * @return the savedPreferencesList
	 */
	public ObservableList<TweetExtractorChartGraphicPreferences> getSavedPreferencesList() {
		return savedPreferencesList;
	}

	/**
	 * @param savedPreferencesList the savedPreferencesList to set
	 */
	public void setSavedPreferencesList(ObservableList<TweetExtractorChartGraphicPreferences> savedPreferencesList) {
		this.savedPreferencesList = savedPreferencesList;
	}

	/**
	 * @return the selectedPref
	 */
	public TweetExtractorChartGraphicPreferences getSelectedPref() {
		return selectedPref;
	}

	/**
	 * @param selectedPref the selectedPref to set
	 */
	public void setSelectedPref(TweetExtractorChartGraphicPreferences selectedPref) {
		updateConfiguration();
		this.selectedPref = selectedPref;
		loadConfiguration(selectedPref);
	}

	@FXML
	public void onBack() {
		//
	}

	@FXML
	public void onNext() {
		if(selectedPref!=null) {
			updateConfiguration();
			prefsService.saveOrUpdate(selectedPref);
			switch (getChartTypeInput()) {
			case P3DCH:
			case PCH:
				AnalyticsReportImage chart = new AnalyticsReportImage();
				chart.setReport(getReportInput());
				chart.setPlotStrokeConfiguration(TweetExtractorUtils.initializePlotStrokeConfiguration(this.getReportInput(), chart));
				this.getReportInput().getGraphics().add(chart);
				this.getMainApplication().showSpecificGraphicChartConfiguration(nextFXMLscreen, nextControllerClazz, chartTypeInput, reportInput, selectedPref,chart);
				break;
			default:
				this.getMainApplication().showSpecificGraphicChartConfiguration(nextFXMLscreen, nextControllerClazz, chartTypeInput, reportInput, selectedPref,null);
				break;
			}
		}
	}
	@FXML
	public void onSave() {
		if (selectedPref == null) {
			ErrorDialog.showErrorNoSelectedChartConfig();
		} else {
			updateConfiguration();
			prefsService.saveOrUpdate(selectedPref);
		}
	}
	@FXML
	public void onAddSavedPref() {
		Class<?>[] params = new Class<?>[1];
		params[0]=String.class;
        Object newPrefs=null;
		try {
			newPrefs = configClazz.getConstructor(params).newInstance("My config");
			savedPreferencesList.add((TweetExtractorChartGraphicPreferences)newPrefs);
			((TweetExtractorChartGraphicPreferences)newPrefs).setUser(this.getMainApplication().getCurrentUser());
			((TweetExtractorChartGraphicPreferences)newPrefs).setChartType(chartTypeInput);
			prefsService.persist((TweetExtractorChartGraphicPreferences)newPrefs);
			setSelectedPref((TweetExtractorChartGraphicPreferences)newPrefs);
		}catch(Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	public void onDeleteSavedPref() {
		if (selectedPref == null) {
			ErrorDialog.showErrorNoSelectedChartConfig();
		} else {
			prefsService.deleteById(selectedPref.getId());
			savedPreferencesList.remove(selectedPref);
			refreshSavedPrefsList();
			savedPrefsTable.getSelectionModel().selectFirst();
		}
	}

	public void refreshSavedPrefsList() {
		List<TweetExtractorChartGraphicPreferences> result = prefsService
				.findByUserAndChartType(this.getMainApplication().getCurrentUser(), chartTypeInput);
		if (result == null || result.isEmpty()) {
			Class<?>[] params = new Class<?>[1];
			params[0]=String.class;
	        Object newPrefs=null;
			try {
				newPrefs = configClazz.getConstructor(params).newInstance("myConfig");
				savedPreferencesList.add((TweetExtractorChartGraphicPreferences)newPrefs);
				((TweetExtractorChartGraphicPreferences)newPrefs).setUser(this.getMainApplication().getCurrentUser());
				((TweetExtractorChartGraphicPreferences)newPrefs).setChartType(chartTypeInput);
				prefsService.persist((TweetExtractorChartGraphicPreferences)newPrefs);
				loadConfiguration((TweetExtractorChartGraphicPreferences)newPrefs);
			} catch (Exception e) {
				logger.warn(e.getMessage());
				e.printStackTrace();
			}
		} else {
			savedPreferencesList.clear();
			savedPreferencesList.addAll(result);
		}
	}

	/**
	 * @return the chartTypeInput
	 */
	public AnalyticsReportImageTypes getChartTypeInput() {
		return chartTypeInput;
	}

	/**
	 * @param chartTypeInput the chartTypeInput to set
	 */
	public void setChartTypeInput(AnalyticsReportImageTypes chartTypeInput) {
		this.chartTypeInput = chartTypeInput;
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
	 * @return the chartTitleFontSizeChoiceBox
	 */
	public ChoiceBox<Integer> getChartTitleFontSizeChoiceBox() {
		return chartTitleFontSizeChoiceBox;
	}

	/**
	 * @param chartTitleFontSizeChoiceBox the chartTitleFontSizeChoiceBox to set
	 */
	public void setChartTitleFontSizeChoiceBox(ChoiceBox<Integer> chartTitleFontSizeChoiceBox) {
		this.chartTitleFontSizeChoiceBox = chartTitleFontSizeChoiceBox;
	}

	/**
	 * @return the chartTitleFontTypeChoiceBox
	 */
	public ChoiceBox<String> getChartTitleFontTypeChoiceBox() {
		return chartTitleFontTypeChoiceBox;
	}

	/**
	 * @param chartTitleFontTypeChoiceBox the chartTitleFontTypeChoiceBox to set
	 */
	public void setChartTitleFontTypeChoiceBox(ChoiceBox<String> chartTitleFontTypeChoiceBox) {
		this.chartTitleFontTypeChoiceBox = chartTitleFontTypeChoiceBox;
	}

	/**
	 * @return the fontNameChoiceBox
	 */
	public ChoiceBox<String> getFontNameChoiceBox() {
		return fontNameChoiceBox;
	}

	/**
	 * @param fontNameChoiceBox the fontNameChoiceBox to set
	 */
	public void setFontNameChoiceBox(ChoiceBox<String> fontNameChoiceBox) {
		this.fontNameChoiceBox = fontNameChoiceBox;
	}

	/**
	 * @return the axisTitleFontSizeChoiceBox
	 */
	public ChoiceBox<Integer> getAxisTitleFontSizeChoiceBox() {
		return axisTitleFontSizeChoiceBox;
	}

	/**
	 * @param axisTitleFontSizeChoiceBox the axisTitleFontSizeChoiceBox to set
	 */
	public void setAxisTitleFontSizeChoiceBox(ChoiceBox<Integer> axisTitleFontSizeChoiceBox) {
		this.axisTitleFontSizeChoiceBox = axisTitleFontSizeChoiceBox;
	}

	/**
	 * @return the axisTitleFontTypeChoiceBox
	 */
	public ChoiceBox<String> getAxisTitleFontTypeChoiceBox() {
		return axisTitleFontTypeChoiceBox;
	}

	/**
	 * @param axisTitleFontTypeChoiceBox the axisTitleFontTypeChoiceBox to set
	 */
	public void setAxisTitleFontTypeChoiceBox(ChoiceBox<String> axisTitleFontTypeChoiceBox) {
		this.axisTitleFontTypeChoiceBox = axisTitleFontTypeChoiceBox;
	}

	/**
	 * @return the regularFontSizeChoiceBox
	 */
	public ChoiceBox<Integer> getRegularFontSizeChoiceBox() {
		return regularFontSizeChoiceBox;
	}

	/**
	 * @param regularFontSizeChoiceBox the regularFontSizeChoiceBox to set
	 */
	public void setRegularFontSizeChoiceBox(ChoiceBox<Integer> regularFontSizeChoiceBox) {
		this.regularFontSizeChoiceBox = regularFontSizeChoiceBox;
	}

	/**
	 * @return the regularFontTypeChoiceBox
	 */
	public ChoiceBox<String> getRegularFontTypeChoiceBox() {
		return regularFontTypeChoiceBox;
	}

	/**
	 * @param regularFontTypeChoiceBox the regularFontTypeChoiceBox to set
	 */
	public void setRegularFontTypeChoiceBox(ChoiceBox<String> regularFontTypeChoiceBox) {
		this.regularFontTypeChoiceBox = regularFontTypeChoiceBox;
	}

	/**
	 * @return the hexTitleColourPicker
	 */
	public ColorPicker getHexTitleColourPicker() {
		return hexTitleColourPicker;
	}

	/**
	 * @param hexTitleColourPicker the hexTitleColourPicker to set
	 */
	public void setHexTitleColourPicker(ColorPicker hexTitleColourPicker) {
		this.hexTitleColourPicker = hexTitleColourPicker;
	}

	/**
	 * @return the hexRangeGridLineColourPicker
	 */
	public ColorPicker getHexRangeGridLineColourPicker() {
		return hexRangeGridLineColourPicker;
	}

	/**
	 * @param hexRangeGridLineColourPicker the hexRangeGridLineColourPicker to set
	 */
	public void setHexRangeGridLineColourPicker(ColorPicker hexRangeGridLineColourPicker) {
		this.hexRangeGridLineColourPicker = hexRangeGridLineColourPicker;
	}

	/**
	 * @return the hexPlotBackgroundPaintColourPicker
	 */
	public ColorPicker getHexPlotBackgroundPaintColourPicker() {
		return hexPlotBackgroundPaintColourPicker;
	}

	/**
	 * @param hexPlotBackgroundPaintColourPicker the
	 *                                           hexPlotBackgroundPaintColourPicker
	 *                                           to set
	 */
	public void setHexPlotBackgroundPaintColourPicker(ColorPicker hexPlotBackgroundPaintColourPicker) {
		this.hexPlotBackgroundPaintColourPicker = hexPlotBackgroundPaintColourPicker;
	}

	/**
	 * @return the hexChartBackgroundPaintColourPicker
	 */
	public ColorPicker getHexChartBackgroundPaintColourPicker() {
		return hexChartBackgroundPaintColourPicker;
	}

	/**
	 * @param hexChartBackgroundPaintColourPicker the
	 *                                            hexChartBackgroundPaintColourPicker
	 *                                            to set
	 */
	public void setHexChartBackgroundPaintColourPicker(ColorPicker hexChartBackgroundPaintColourPicker) {
		this.hexChartBackgroundPaintColourPicker = hexChartBackgroundPaintColourPicker;
	}

	/**
	 * @return the hexGridBandPaintColourPicker
	 */
	public ColorPicker getHexGridBandPaintColourPicker() {
		return hexGridBandPaintColourPicker;
	}

	/**
	 * @param hexGridBandPaintColourPicker the hexGridBandPaintColourPicker to set
	 */
	public void setHexGridBandPaintColourPicker(ColorPicker hexGridBandPaintColourPicker) {
		this.hexGridBandPaintColourPicker = hexGridBandPaintColourPicker;
	}

	/**
	 * @return the hexAxisLabelColourPicker
	 */
	public ColorPicker getHexAxisLabelColourPicker() {
		return hexAxisLabelColourPicker;
	}

	/**
	 * @param hexAxisLabelColourPicker the hexAxisLabelColourPicker to set
	 */
	public void setHexAxisLabelColourPicker(ColorPicker hexAxisLabelColourPicker) {
		this.hexAxisLabelColourPicker = hexAxisLabelColourPicker;
	}

	/**
	 * @return the legendCheckBox
	 */
	public CheckBox getLegendCheckBox() {
		return legendCheckBox;
	}

	/**
	 * @param legendCheckBox the legendCheckBox to set
	 */
	public void setLegendCheckBox(CheckBox legendCheckBox) {
		this.legendCheckBox = legendCheckBox;
	}

	/**
	 * @return the toolTipsCheckBox
	 */
	public CheckBox getToolTipsCheckBox() {
		return toolTipsCheckBox;
	}

	/**
	 * @param toolTipsCheckBox the toolTipsCheckBox to set
	 */
	public void setToolTipsCheckBox(CheckBox toolTipsCheckBox) {
		this.toolTipsCheckBox = toolTipsCheckBox;
	}

	/**
	 * @return the urlsCheckBox
	 */
	public CheckBox getUrlsCheckBox() {
		return urlsCheckBox;
	}

	/**
	 * @param urlsCheckBox the urlsCheckBox to set
	 */
	public void setUrlsCheckBox(CheckBox urlsCheckBox) {
		this.urlsCheckBox = urlsCheckBox;
	}

	/**
	 * @return the reportInput
	 */
	public AnalyticsRepresentableReport getReportInput() {
		return reportInput;
	}

	/**
	 * @param reportInput the reportInput to set
	 */
	public void setReportInput(AnalyticsRepresentableReport reportInput) {
		this.reportInput = reportInput;
	}
	/*Utils*/
	public boolean isFontAvailable(String font) {
		for (String availableFont : availablefonts) {
			if (font.equals(availableFont)) {
				return true;
			}
		}
		return false;
	}

	

    
}
