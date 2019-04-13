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
import es.uam.eps.tweetextractor.model.analytics.graphics.XYChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;
import es.uam.eps.tweetextractorfx.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * @author jose
 *
 */
public class XYChartGraphicPreferencesControl extends SpecificGraphicChartPreferencesController {
	private TweetExtractorChartGraphicPreferencesServiceInterface prefsService;
	
	private XYChartGraphicPreferences preferences;
	
	private Logger logger = LoggerFactory.getLogger(XYChartGraphicPreferencesControl.class);
	
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
	
	/**
	 * 
	 */
	public XYChartGraphicPreferencesControl() {
		super();
	}
	
	/**
	 * @param chartTypeInput the chartTypeInput
	 * @param reportInput the reportInput
	 * @param preferencesInput the preferencesInput
	 */
	public XYChartGraphicPreferencesControl(AnalyticsReportImageTypes chartTypeInput,
			AnalyticsRepresentableReport reportInput, TweetExtractorChartGraphicPreferences preferencesInput) {
		super(chartTypeInput, reportInput, preferencesInput);
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		preferences = (XYChartGraphicPreferences) this.getPreferencesInput();
		loadConfiguration();
		prefsService = this.mainApplication.getSpringContext().getBean(TweetExtractorChartGraphicPreferencesServiceInterface.class);
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

	
	

}
