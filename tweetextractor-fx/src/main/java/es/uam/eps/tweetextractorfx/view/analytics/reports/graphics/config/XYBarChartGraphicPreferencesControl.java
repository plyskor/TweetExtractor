/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorChartGraphicPreferencesServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;
import es.uam.eps.tweetextractorfx.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author jgarciadelsaz
 *
 */
public class XYBarChartGraphicPreferencesControl extends SpecificGraphicChartPreferencesController {
	private TweetExtractorChartGraphicPreferencesServiceInterface prefsService;
	
	private XYBarChartGraphicPreferences preferences;
	
	private Logger logger = LoggerFactory.getLogger(XYBarChartGraphicPreferencesControl.class);
	
	@FXML
	private CheckBox outlineVisibleCheckBox;
	@FXML
	private CheckBox rangeAxisLineVisibleCheckBox;
	@FXML
	private CheckBox rangeAxisTickMarksVisibleCheckBox;
	@FXML
	private ColorPicker rangeAxisTickLabelPaintColourPicker;
	@FXML
	private ColorPicker domainAxisTickLabelPaintColourPicker;
	@FXML
	private TextField xAxisLabelTextField;
	@FXML
	private TextField yAxisLabelTextField;
	@FXML
	private Text xShadowOffsetText;
	@FXML
	private Text yShadowOffsetText;
	@FXML
	private CheckBox shadowVisibleCheckBox;
	@FXML
	private Slider xShadowOffsetSlider;
	@FXML
	private Slider yShadowOffsetSlider;
	
	public XYBarChartGraphicPreferencesControl() {
		super();
	}
	
	/**
	 * @param chartTypeInput the chartTypeInput
	 * @param reportInput the reportInput
	 * @param preferencesInput the preferencesInput
	 */
	public XYBarChartGraphicPreferencesControl(AnalyticsReportImageTypes chartTypeInput,
			AnalyticsRepresentableReport reportInput, TweetExtractorChartGraphicPreferences preferencesInput) {
		super(chartTypeInput, reportInput, preferencesInput);
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		preferences = (XYBarChartGraphicPreferences) this.getPreferencesInput();
		loadConfiguration();
		prefsService = this.mainApplication.getSpringContext().getBean(TweetExtractorChartGraphicPreferencesServiceInterface.class);
		xShadowOffsetSlider.valueProperty()
		.addListener((observable, oldValue, newValue) -> xShadowOffsetText.setText("X shadow offset: "+newValue.intValue()));
		yShadowOffsetSlider.valueProperty()
		.addListener((observable, oldValue, newValue) -> yShadowOffsetText.setText("Y shadow offset: "+newValue.intValue()));
	}

	public void loadConfiguration() {
		if(preferences!=null) {
			outlineVisibleCheckBox.setSelected(preferences.isOutlineVisible());
			rangeAxisLineVisibleCheckBox.setSelected(preferences.isRangeAxisLineVisible());
			rangeAxisTickMarksVisibleCheckBox.setSelected(preferences.isRangeAxistTickMarksVisible());
			rangeAxisTickLabelPaintColourPicker.setValue(Color.web(preferences.getRangeAxisHexTickLabelPaint()));
			domainAxisTickLabelPaintColourPicker.setValue(Color.web(preferences.getDomainAxisHexTickLabelPaint()));
			xAxisLabelTextField.setText(preferences.getxAxisLabel());
			yAxisLabelTextField.setText(preferences.getyAxisLabel());
			xShadowOffsetSlider.setValue((double)preferences.getShadowXOffset());
			yShadowOffsetSlider.setValue((double)preferences.getShadowYOffset());
			shadowVisibleCheckBox.setSelected(preferences.isShadowVisible());
		}
	}
	public void updateConfiguration() {
		if(preferences!=null) {
			preferences.setOutlineVisible(outlineVisibleCheckBox.isSelected());
			preferences.setRangeAxisLineVisible(rangeAxisLineVisibleCheckBox.isSelected());
			preferences.setRangeAxistTickMarksVisible(rangeAxisTickMarksVisibleCheckBox.isSelected());
			preferences.setRangeAxisHexTickLabelPaint(TweetExtractorUtils.colorToHex(rangeAxisTickLabelPaintColourPicker.getValue()));
			preferences.setDomainAxisHexTickLabelPaint(TweetExtractorUtils.colorToHex(domainAxisTickLabelPaintColourPicker.getValue()));
			preferences.setxAxisLabel(xAxisLabelTextField.getText());
			preferences.setyAxisLabel(yAxisLabelTextField.getText());
			preferences.setShadowXOffset((int)xShadowOffsetSlider.getValue());
			preferences.setShadowYOffset((int)yShadowOffsetSlider.getValue());
			preferences.setShadowVisible(shadowVisibleCheckBox.isSelected());
		}
	}
	@FXML
	public void onBack() {
		this.getMainApplication().showChartGraphicsPreferences(this.getChartTypeInput(), this.getReportInput());
	}
	@FXML
	public void onNext() {
		updateConfiguration();
		prefsService.saveOrUpdate(preferences);
		if (this.getChart()==null) {
			AnalyticsReportImage chart = new AnalyticsReportImage();
			chart.setReport(getReportInput());
			chart.setPlotStrokeConfiguration(TweetExtractorUtils.initializePlotStrokeConfiguration(this.getReportInput(), chart));
			this.getReportInput().getGraphics().add(chart);
			this.setChart(chart);
		}
		switch(this.getReportInput().reportType) {
		case TTNHR:
		case TVR:
			this.preferences.setDateAxis(true);
			prefsService.saveOrUpdate(preferences);
			this.mainApplication.showSpecificGraphicChartConfiguration("view/analytics/reports/graphics/config/DatasetConfiguration.fxml", DatasetConfigurationControl.class, this.getChartTypeInput(), this.getReportInput(), preferences,this.getChart());
			break;
		case TR:
			break;
		default:
			break;
		}
	}
	/**
	 * @return the outlineVisibleCheckBox
	 */
	public CheckBox getOutlineVisibleCheckBox() {
		return outlineVisibleCheckBox;
	}
	/**
	 * @param outlineVisibleCheckBox the outlineVisibleCheckBox to set
	 */
	public void setOutlineVisibleCheckBox(CheckBox outlineVisibleCheckBox) {
		this.outlineVisibleCheckBox = outlineVisibleCheckBox;
	}
	/**
	 * @return the rangeAxisLineVisibleCheckBox
	 */
	public CheckBox getRangeAxisLineVisibleCheckBox() {
		return rangeAxisLineVisibleCheckBox;
	}
	/**
	 * @param rangeAxisLineVisibleCheckBox the rangeAxisLineVisibleCheckBox to set
	 */
	public void setRangeAxisLineVisibleCheckBox(CheckBox rangeAxisLineVisibleCheckBox) {
		this.rangeAxisLineVisibleCheckBox = rangeAxisLineVisibleCheckBox;
	}
	/**
	 * @return the rangeAxisTickMarksVisibleCheckBox
	 */
	public CheckBox getRangeAxisTickMarksVisibleCheckBox() {
		return rangeAxisTickMarksVisibleCheckBox;
	}
	/**
	 * @param rangeAxisTickMarksVisibleCheckBox the rangeAxisTickMarksVisibleCheckBox to set
	 */
	public void setRangeAxisTickMarksVisibleCheckBox(CheckBox rangeAxisTickMarksVisibleCheckBox) {
		this.rangeAxisTickMarksVisibleCheckBox = rangeAxisTickMarksVisibleCheckBox;
	}
	
	/**
	 * @return the rangeAxisTickLabelPaintColourPicker
	 */
	public ColorPicker getRangeAxisTickLabelPaintColourPicker() {
		return rangeAxisTickLabelPaintColourPicker;
	}
	/**
	 * @param rangeAxisTickLabelPaintColourPicker the rangeAxisTickLabelPaintColourPicker to set
	 */
	public void setRangeAxisTickLabelPaintColourPicker(ColorPicker rangeAxisTickLabelPaintColourPicker) {
		this.rangeAxisTickLabelPaintColourPicker = rangeAxisTickLabelPaintColourPicker;
	}
	/**
	 * @return the domainAxisTickLabelPaintColourPicker
	 */
	public ColorPicker getDomainAxisTickLabelPaintColourPicker() {
		return domainAxisTickLabelPaintColourPicker;
	}
	/**
	 * @param domainAxisTickLabelPaintColourPicker the domainAxisTickLabelPaintColourPicker to set
	 */
	public void setDomainAxisTickLabelPaintColourPicker(ColorPicker domainAxisTickLabelPaintColourPicker) {
		this.domainAxisTickLabelPaintColourPicker = domainAxisTickLabelPaintColourPicker;
	}
	/**
	 * @return the xAxisLabelTextField
	 */
	public TextField getxAxisLabelTextField() {
		return xAxisLabelTextField;
	}
	/**
	 * @param xAxisLabelTextField the xAxisLabelTextField to set
	 */
	public void setxAxisLabelTextField(TextField xAxisLabelTextField) {
		this.xAxisLabelTextField = xAxisLabelTextField;
	}
	/**
	 * @return the yAxisLabelTextField
	 */
	public TextField getyAxisLabelTextField() {
		return yAxisLabelTextField;
	}
	/**
	 * @param yAxisLabelTextField the yAxisLabelTextField to set
	 */
	public void setyAxisLabelTextField(TextField yAxisLabelTextField) {
		this.yAxisLabelTextField = yAxisLabelTextField;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
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
	 * @return the preferences
	 */
	public XYBarChartGraphicPreferences getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(XYBarChartGraphicPreferences preferences) {
		this.preferences = preferences;
	}


	/**
	 * @return the xShadowOffsetText
	 */
	public Text getxShadowOffsetText() {
		return xShadowOffsetText;
	}

	/**
	 * @param xShadowOffsetText the xShadowOffsetText to set
	 */
	public void setxShadowOffsetText(Text xShadowOffsetText) {
		this.xShadowOffsetText = xShadowOffsetText;
	}

	/**
	 * @return the yShadowOffsetText
	 */
	public Text getyShadowOffsetText() {
		return yShadowOffsetText;
	}

	/**
	 * @param yShadowOffsetText the yShadowOffsetText to set
	 */
	public void setyShadowOffsetText(Text yShadowOffsetText) {
		this.yShadowOffsetText = yShadowOffsetText;
	}

	/**
	 * @return the shadowVisibleCheckBox
	 */
	public CheckBox getShadowVisibleCheckBox() {
		return shadowVisibleCheckBox;
	}

	/**
	 * @param shadowVisibleCheckBox the shadowVisibleCheckBox to set
	 */
	public void setShadowVisibleCheckBox(CheckBox shadowVisibleCheckBox) {
		this.shadowVisibleCheckBox = shadowVisibleCheckBox;
	}

	/**
	 * @return the xShadowOffsetSlider
	 */
	public Slider getxShadowOffsetSlider() {
		return xShadowOffsetSlider;
	}

	/**
	 * @param xShadowOffsetSlider the xShadowOffsetSlider to set
	 */
	public void setxShadowOffsetSlider(Slider xShadowOffsetSlider) {
		this.xShadowOffsetSlider = xShadowOffsetSlider;
	}

	/**
	 * @return the yShadowOffsetSlider
	 */
	public Slider getyShadowOffsetSlider() {
		return yShadowOffsetSlider;
	}

	/**
	 * @param yShadowOffsetSlider the yShadowOffsetSlider to set
	 */
	public void setyShadowOffsetSlider(Slider yShadowOffsetSlider) {
		this.yShadowOffsetSlider = yShadowOffsetSlider;
	}

}
