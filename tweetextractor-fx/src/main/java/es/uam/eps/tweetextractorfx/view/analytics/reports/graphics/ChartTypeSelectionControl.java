/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics;

import java.util.List;

import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsCategoryReport;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author jose
 *
 */
public class ChartTypeSelectionControl extends TweetExtractorFXController {
	@FXML
	private ToggleButton timeSeriesChartToggleButton;
	@FXML
	private ToggleButton xyBarChartToggleButton;
	@FXML
	private ToggleButton categoryBarChartToggleButton;
	@FXML
	private ToggleButton pieChartToggleButton;
	@FXML
	private ToggleButton pie3DChartToggleButton;
	@FXML
	private ToggleButton wordCloudToggleButton;
	@FXML
	private ImageView timeSeriesChartImage;
	@FXML
	private ImageView xyBarChartToggleImage;
	@FXML
	private ImageView categoryBarChartToggleImage;
	@FXML
	private ImageView pieChartToggleImage;
	@FXML
	private ImageView pie3DChartToggleImage;
	@FXML
	private ImageView wordCloudToggleImage;
	
	private ToggleGroup toggleGroup = new ToggleGroup();
		
	private AnalyticsReportServiceInterface arServ;
	
	/**
	 * 
	 */
	public ChartTypeSelectionControl() {
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
		timeSeriesChartToggleButton.setToggleGroup(toggleGroup);
		xyBarChartToggleButton.setToggleGroup(toggleGroup);
		categoryBarChartToggleButton.setToggleGroup(toggleGroup);
		pieChartToggleButton.setToggleGroup(toggleGroup);
		pie3DChartToggleButton.setToggleGroup(toggleGroup);
		wordCloudToggleButton.setToggleGroup(toggleGroup);
		arServ=this.mainApplication.getSpringContext().getBean(AnalyticsReportServiceInterface.class);
	}

	@FXML
	private void initialize() {
		timeSeriesChartImage.setImage(new Image("timeSeriesChart.png"));
		xyBarChartToggleImage.setImage(new Image("XYBarChart.jpg"));
		categoryBarChartToggleImage.setImage(new Image("categoryBarChart.jpg"));
		pieChartToggleImage.setImage(new Image("pieChart.png"));
		pie3DChartToggleImage.setImage(new Image("3DPieChart.jpg"));
		wordCloudToggleImage.setImage(new Image("wordCloud.png"));
	}

	@FXML
	private void onBack() {
		this.getMainApplication().showScreenInCenterOfRootLayout("view/analytics/reports/graphics/MyGraphics.fxml");
	}

	@FXML
	private void onNext() {
		ToggleButton selectedToggle = (ToggleButton) toggleGroup.getSelectedToggle();
		if(selectedToggle==null) {
			ErrorDialog.showErrorNoSelectedChartType();
			return;
		}
		List<AnalyticsCategoryReport> result;
		if(selectedToggle.equals(timeSeriesChartToggleButton)) {
			result = arServ.findByUserAndReportType(this.mainApplication.getCurrentUser(), Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.TSC));
			if(result==null||result.isEmpty()) {
				ErrorDialog.showErrorNoReportsForThisChart(Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.TSC));
			}else{
				this.mainApplication.showCompatibleReportSelection(AnalyticsReportImageTypes.TSC);
			}
		} else if(selectedToggle.equals(categoryBarChartToggleButton)) {
			result = arServ.findByUserAndReportType(this.mainApplication.getCurrentUser(), Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.BARC));
			if(result==null||result.isEmpty()) {
				ErrorDialog.showErrorNoReportsForThisChart(Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.BARC));
			}else {
				this.mainApplication.showCompatibleReportSelection(AnalyticsReportImageTypes.BARC);
			}	
		}else if(selectedToggle.equals(pieChartToggleButton)) {
			result = arServ.findByUserAndReportType(this.mainApplication.getCurrentUser(), Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.PCH));
			if(result==null||result.isEmpty()) {
				ErrorDialog.showErrorNoReportsForThisChart(Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.PCH));
			}else {
				this.mainApplication.showCompatibleReportSelection(AnalyticsReportImageTypes.PCH);
			}	
		}else if(selectedToggle.equals(xyBarChartToggleButton)) {
			result = arServ.findByUserAndReportType(this.mainApplication.getCurrentUser(), Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.BXYC));
			if(result==null||result.isEmpty()) {
				ErrorDialog.showErrorNoReportsForThisChart(Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.BXYC));
			}else {
				this.mainApplication.showCompatibleReportSelection(AnalyticsReportImageTypes.BXYC);
			}
		}else if(selectedToggle.equals(pie3DChartToggleButton)) {
			result = arServ.findByUserAndReportType(this.mainApplication.getCurrentUser(), Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.P3DCH));
			if(result==null||result.isEmpty()) {
				ErrorDialog.showErrorNoReportsForThisChart(Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.P3DCH));
			}else {
				this.mainApplication.showCompatibleReportSelection(AnalyticsReportImageTypes.P3DCH);
			}
		}else if(selectedToggle.equals(wordCloudToggleButton)) {
			result = arServ.findByUserAndReportType(this.mainApplication.getCurrentUser(), Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.WCC));
			if(result==null||result.isEmpty()) {
				ErrorDialog.showErrorNoReportsForThisChart(Constants.REPORT_CHART_TYPES_COMPATIBILITY.get(AnalyticsReportImageTypes.WCC));
			}else {
				this.mainApplication.showCompatibleReportSelection(AnalyticsReportImageTypes.WCC);
			}
		}

	}

	/**
	 * @return the timeSeriesChartToggleButton
	 */
	public ToggleButton getTimeSeriesChartToggleButton() {
		return timeSeriesChartToggleButton;
	}

	/**
	 * @param timeSeriesChartToggleButton the timeSeriesChartToggleButton to set
	 */
	public void setTimeSeriesChartToggleButton(ToggleButton timeSeriesChartToggleButton) {
		this.timeSeriesChartToggleButton = timeSeriesChartToggleButton;
	}

	/**
	 * @return the xyBarChartToggleButton
	 */
	public ToggleButton getXyBarChartToggleButton() {
		return xyBarChartToggleButton;
	}

	/**
	 * @param xyBarChartToggleButton the xyBarChartToggleButton to set
	 */
	public void setXyBarChartToggleButton(ToggleButton xyBarChartToggleButton) {
		this.xyBarChartToggleButton = xyBarChartToggleButton;
	}

	/**
	 * @return the categoryBarChartToggleButton
	 */
	public ToggleButton getCategoryBarChartToggleButton() {
		return categoryBarChartToggleButton;
	}

	/**
	 * @param categoryBarChartToggleButton the categoryBarChartToggleButton to set
	 */
	public void setCategoryBarChartToggleButton(ToggleButton categoryBarChartToggleButton) {
		this.categoryBarChartToggleButton = categoryBarChartToggleButton;
	}

	/**
	 * @return the pieChartToggleButton
	 */
	public ToggleButton getPieChartToggleButton() {
		return pieChartToggleButton;
	}

	/**
	 * @param pieChartToggleButton the pieChartToggleButton to set
	 */
	public void setPieChartToggleButton(ToggleButton pieChartToggleButton) {
		this.pieChartToggleButton = pieChartToggleButton;
	}

	/**
	 * @return the pie3DChartToggleButton
	 */
	public ToggleButton getPie3DChartToggleButton() {
		return pie3DChartToggleButton;
	}

	/**
	 * @param pie3dChartToggleButton the pie3DChartToggleButton to set
	 */
	public void setPie3DChartToggleButton(ToggleButton pie3dChartToggleButton) {
		pie3DChartToggleButton = pie3dChartToggleButton;
	}

	/**
	 * @return the wordCloudToggleButton
	 */
	public ToggleButton getWordCloudToggleButton() {
		return wordCloudToggleButton;
	}

	/**
	 * @param wordCloudToggleButton the wordCloudToggleButton to set
	 */
	public void setWordCloudToggleButton(ToggleButton wordCloudToggleButton) {
		this.wordCloudToggleButton = wordCloudToggleButton;
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
	 * @return the timeSeriesChartImage
	 */
	public ImageView getTimeSeriesChartImage() {
		return timeSeriesChartImage;
	}

	/**
	 * @param timeSeriesChartImage the timeSeriesChartImage to set
	 */
	public void setTimeSeriesChartImage(ImageView timeSeriesChartImage) {
		this.timeSeriesChartImage = timeSeriesChartImage;
	}

	/**
	 * @return the xyBarChartToggleImage
	 */
	public ImageView getXyBarChartToggleImage() {
		return xyBarChartToggleImage;
	}

	/**
	 * @param xyBarChartToggleImage the xyBarChartToggleImage to set
	 */
	public void setXyBarChartToggleImage(ImageView xyBarChartToggleImage) {
		this.xyBarChartToggleImage = xyBarChartToggleImage;
	}

	/**
	 * @return the categoryBarChartToggleImage
	 */
	public ImageView getCategoryBarChartToggleImage() {
		return categoryBarChartToggleImage;
	}

	/**
	 * @param categoryBarChartToggleImage the categoryBarChartToggleImage to set
	 */
	public void setCategoryBarChartToggleImage(ImageView categoryBarChartToggleImage) {
		this.categoryBarChartToggleImage = categoryBarChartToggleImage;
	}

	/**
	 * @return the pieChartToggleImage
	 */
	public ImageView getPieChartToggleImage() {
		return pieChartToggleImage;
	}

	/**
	 * @param pieChartToggleImage the pieChartToggleImage to set
	 */
	public void setPieChartToggleImage(ImageView pieChartToggleImage) {
		this.pieChartToggleImage = pieChartToggleImage;
	}

	/**
	 * @return the pie3DChartToggleImage
	 */
	public ImageView getPie3DChartToggleImage() {
		return pie3DChartToggleImage;
	}

	/**
	 * @param pie3dChartToggleImage the pie3DChartToggleImage to set
	 */
	public void setPie3DChartToggleImage(ImageView pie3dChartToggleImage) {
		pie3DChartToggleImage = pie3dChartToggleImage;
	}

	/**
	 * @return the wordCloudToggleImage
	 */
	public ImageView getWordCloudToggleImage() {
		return wordCloudToggleImage;
	}

	/**
	 * @param wordCloudToggleImage the wordCloudToggleImage to set
	 */
	public void setWordCloudToggleImage(ImageView wordCloudToggleImage) {
		this.wordCloudToggleImage = wordCloudToggleImage;
	}




}
