/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics.config;


import java.awt.GraphicsEnvironment;
import java.util.List;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorChartGraphicPreferencesServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;
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

/**
 * @author jose
 *
 */
public class ChartGraphicPreferencesControl extends TweetExtractorFXController {
	private AnalyticsReportImageTypes chartTypeInput;
	
	private AnalyticsRepresentableReport reportInput;
	
	private TweetExtractorChartGraphicPreferencesServiceInterface prefsService;
	@FXML
	private TableView<TweetExtractorChartGraphicPreferences> savedPrefsTable;
	@FXML
	private TableColumn<TweetExtractorChartGraphicPreferences, String> prefNameColumn;
	
	private ObservableList<TweetExtractorChartGraphicPreferences> savedPreferencesList= FXCollections.observableArrayList();
	
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
	
	
	
	public ChartGraphicPreferencesControl() {
		super();
	}
	@FXML
	private void initialize() {
		prefNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		prefNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		savedPrefsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			setSelectedPref(newValue);
			loadConfiguration(selectedPref);
		});
		prefNameColumn.setOnEditCommit((CellEditEvent<TweetExtractorChartGraphicPreferences, String> t)->{
			TweetExtractorChartGraphicPreferences editedPref= t.getTableView().getItems().get(t.getTablePosition().getRow());	
			editedPref.setName(t.getNewValue());
			//TODO: Save to BDD
		});
	}
	private void loadConfiguration(TweetExtractorChartGraphicPreferences selectedPref) {
		if(selectedPref!=null) {
			
		}
	}
	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override	
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		savedPrefsTable.setItems(savedPreferencesList);
		prefsService=this.mainApplication.getSpringContext().getBean(TweetExtractorChartGraphicPreferencesServiceInterface.class);
		initializeChoiceBoxes();
	}
	private void initializeChoiceBoxes() {
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(String font:fonts) {
			fontNameChoiceBox.getItems().add(font);
		}
		for(int i=7;i<=72;i++) {
			chartTitleFontSizeChoiceBox.getItems().add(i);
			axisTitleFontSizeChoiceBox.getItems().add(i);
			regularFontSizeChoiceBox.getItems().add(i);
		}
		chartTitleFontTypeChoiceBox.getItems().add("Plain");
		chartTitleFontTypeChoiceBox.getItems().add("Bold");
		chartTitleFontTypeChoiceBox.getItems().add("Italic");
		axisTitleFontTypeChoiceBox.getItems().add("Plain");
		axisTitleFontTypeChoiceBox.getItems().add("Bold");
		axisTitleFontTypeChoiceBox.getItems().add("Italic");
		regularFontTypeChoiceBox.getItems().add("Plain");
		regularFontTypeChoiceBox.getItems().add("Bold");
		regularFontTypeChoiceBox.getItems().add("Italic");
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
		this.selectedPref = selectedPref;
	}
	@FXML
	public void onBack() {
		
	}
	@FXML
	public void onNext() {
		
	}
	@FXML
	public void onAddSavedPref() {
		
	}
	@FXML
	public void onDeleteSavedPref() {
		if(selectedPref==null) {
			ErrorDialog.showErrorNoSelectedChartConfig();
		}else {
			savedPreferencesList.remove(selectedPref);
			prefsService.deleteById(selectedPref.getId());
		}
	}
	public void refreshSavedPrefsList() {
		List<TweetExtractorChartGraphicPreferences> result = prefsService.findByUserAndChartType(this.getMainApplication().getCurrentUser(), chartTypeInput);
		savedPreferencesList.clear();
		savedPreferencesList.addAll(result);
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
	 * @param hexPlotBackgroundPaintColourPicker the hexPlotBackgroundPaintColourPicker to set
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
	 * @param hexChartBackgroundPaintColourPicker the hexChartBackgroundPaintColourPicker to set
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
	
}
