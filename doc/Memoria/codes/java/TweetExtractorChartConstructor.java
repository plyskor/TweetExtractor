/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.graphics.constructor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.CategoryBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.WorldCloudChartConfiguration;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsNLPReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingWordsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.AnalyticsTweetVolumeByNLPReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingWordsReportRegister;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;

/**
 * @author jgarciadelsaz
 *
 */
public class TweetExtractorChartConstructor {
	private AnalyticsRepresentableReport report;
	private AnalyticsReportImage reportImage;
	private AnalyticsReportImageTypes chartType;
	private TweetExtractorChartGraphicPreferences preferences;
	// Set of data to build the chart
	private Dataset dataset;
	// Logger
	private Logger logger = LoggerFactory.getLogger(TweetExtractorChartConstructor.class);
	// Theme for charts (JFreeChart)
	private StandardChartTheme theme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();
	// Properties
	// Line properties
	private float lineWidth = 3.2f;
	private float dashWidth = 5.0f;
	// Colours
	private String hexLineColour = "#E0FB00";
	private String hexRangeGridLineColour = "#C0C0C0";
	private String hexTitleColour = "#E0FB00";
	// Font properties
	private String fontName = "Lucida Sans";
	/*
		 * 
		 */

	public TweetExtractorChartConstructor(AnalyticsRepresentableReport report, AnalyticsReportImage chart,
			AnalyticsReportImageTypes chartType, TweetExtractorChartGraphicPreferences config) {
		super();
		this.report = report;
		this.reportImage = chart;
		this.chartType = chartType;
		this.preferences = config;
		if (chartType != AnalyticsReportImageTypes.WCC) {
			constructDataset();
			theme.setTitlePaint(Color.decode(preferences.getHexTitleColour()));
			theme.setExtraLargeFont(new Font(preferences.getFontName(), preferences.getTitleFontType(),
					preferences.getTitleFontSize())); // title
			theme.setLargeFont(new Font(preferences.getFontName(), preferences.getAxisTitleFontType(),
					preferences.getAxisTitleFontSize())); // axis-title
			theme.setRegularFont(new Font(preferences.getFontName(), preferences.getRegularFontType(),
					preferences.getRegularFontSize()));
			theme.setRangeGridlinePaint(Color.decode(preferences.getHexRangeGridLineColour()));
			theme.setPlotBackgroundPaint(Color.decode(preferences.getHexPlotBackgroundPaintColour()));
			theme.setChartBackgroundPaint(Color.decode(preferences.getHexChartBackgroundPaintColour()));
			theme.setGridBandPaint(Color.decode(preferences.getHexGridBandPaintColour()));
			theme.setAxisOffset(new org.jfree.ui.RectangleInsets(0, 0, 0, 0));
			theme.setBarPainter(new StandardBarPainter());
			theme.setAxisLabelPaint(Color.decode(preferences.getHexAxisLabelColour()));
		}
	}

	/**
	 * 
	 */
	public TweetExtractorChartConstructor() {
		super();
	}

	public void constructDataset() {
		switch (chartType) {
		case TSC:
			this.setDataset(report.constructIntervalXYDataset(this.getReportImage().getPlotStrokeConfiguration()));
			break;
		case BXYC:
			this.setDataset(report.constructXYDataset(this.getReportImage().getPlotStrokeConfiguration()));
			break;
		case BARC:
			this.setDataset(report.constructDefaultCategoryDataset(this.getReportImage().getPlotStrokeConfiguration()));
			break;
		case PCH:
		case P3DCH:
			this.setDataset(report.constructPieDataset(this.getReportImage().getPlotStrokeConfiguration()));
			break;
		default:
			break;
		}
	}

	public void constructJFreeChart() {
		JFreeChart chartObject = null;
		if (chartType.equals(AnalyticsReportImageTypes.WCC)) {
			return;
		}
		switch (chartType) {
		case TSC:
			chartObject = constructTimeSeriesChart((XYChartGraphicPreferences) preferences,
					reportImage.getPlotStrokeConfiguration());
			break;
		case BXYC:
			chartObject = constructXYBarChart((XYBarChartGraphicPreferences) preferences,
					reportImage.getPlotStrokeConfiguration());
			break;
		case BARC:
			chartObject = constructBarChart((CategoryBarChartGraphicPreferences) preferences,
					reportImage.getPlotStrokeConfiguration());
			break;
		case PCH:
			chartObject = constructPieChart(preferences, reportImage.getPlotStrokeConfiguration());
			break;
		case P3DCH:
			chartObject = constructPieChart3D(preferences, reportImage.getPlotStrokeConfiguration());
			break;
		default:
			break;
		}
		if (chartObject != null) {
			this.getReportImage().setImage(TweetExtractorUtils.convertChartObjectToByteArray(chartObject));
		}
	}

	public WordCloud constructWordCloudChartFromTrendingWordsReport(File pixelBoundaryFile) throws IOException {
		WorldCloudChartConfiguration config = (WorldCloudChartConfiguration) preferences;
		TrendingWordsReport trendingWordsReport = (TrendingWordsReport) getReport();
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		frequencyAnalyzer.setWordFrequenciesToReturn(config.getnWords());
		frequencyAnalyzer.setMinWordLength(config.getMinWordLength());
		List<WordFrequency> wordFrequencies = new ArrayList<>();
		for (AnalyticsReportCategoryRegister register : trendingWordsReport.getCategories().get(0).getResult()) {
			TrendingWordsReportRegister castedRegister = (TrendingWordsReportRegister) register;
			WordFrequency newWord = new WordFrequency((String) castedRegister.getTerms().toArray()[0],
					castedRegister.getFrequency());
			wordFrequencies.add(newWord);
		}
		wordFrequencies = frequencyAnalyzer.loadWordFrequencies(wordFrequencies);
		Dimension dimension = null; WordCloud wordCloud = null;
		switch (config.getType()) {
		case Constants.WCC_CIRCULAR:
			dimension = new Dimension(1080, 1080);
			wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
			wordCloud.setBackground(new CircleBackground(540));
			break;
		case Constants.WCC_PIXEL_BOUNDARY:
			try {
				dimension = new Dimension(1920, 1080);
				wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
				wordCloud.setBackground(new PixelBoundryBackground(pixelBoundaryFile));
			} catch (IOException e) {
				logger.warn("An exception has been thrown opening pixel boundary file: " + e.getMessage());
			}
			break;
		case Constants.WCC_RECTANGULAR:
			dimension = new Dimension(1920, 1080);
			wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
			wordCloud.setBackground(new RectangleBackground(dimension));
			break;
		default:
			break;
		}
		if (wordCloud != null) {
			wordCloud.setPadding(2);
			wordCloud.setColorPalette(new ColorPalette(config.getAwtColorList()));
			wordCloud.setFontScalar(new LinearFontScalar(config.getFontMin(), config.getFontMax()));
			wordCloud.build(wordFrequencies);
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			wordCloud.writeToStreamAsPNG(byteArrayOutputStream);
			final byte[] bytes = byteArrayOutputStream.toByteArray();
			this.reportImage.setImage(bytes);
		}
		return wordCloud;
	}
	public WordCloud constructWordCloudChartFromNLPReport(File pixelBoundaryFile) throws IOException {
		WorldCloudChartConfiguration config = (WorldCloudChartConfiguration) preferences;
		AnalyticsNLPReport nlpReport = (AnalyticsNLPReport) getReport();
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		frequencyAnalyzer.setWordFrequenciesToReturn(config.getnWords());
		frequencyAnalyzer.setMinWordLength(2);
		List<WordFrequency> wordFrequencies = new ArrayList<>();
		for(AnalyticsReportCategory category : nlpReport.getCategories()) {
			for (AnalyticsReportCategoryRegister register : category.getResult()) {
				AnalyticsTweetVolumeByNLPReportRegister castedRegister = (AnalyticsTweetVolumeByNLPReportRegister) register;
				WordFrequency newWord = new WordFrequency(category.getCategoryName().substring(0, 1).toUpperCase() + category.getCategoryName().substring(1),
						castedRegister.getValue());
				wordFrequencies.add(newWord);
			}
		}
		wordFrequencies = frequencyAnalyzer.loadWordFrequencies(wordFrequencies);
		Dimension dimension = null;
		WordCloud wordCloud = null;
		switch (config.getType()) {
		case Constants.WCC_CIRCULAR:
			dimension = new Dimension(1080, 1080);
			wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
			wordCloud.setBackground(new CircleBackground(540));
			break;
		case Constants.WCC_PIXEL_BOUNDARY:
			try {
				dimension = new Dimension(1920, 1080);
				wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
				wordCloud.setBackground(new PixelBoundryBackground(pixelBoundaryFile));
			} catch (IOException e) {
				logger.warn("An exception has been thrown opening pixel boundary file: " + e.getMessage());
			}
			break;
		case Constants.WCC_RECTANGULAR:
			dimension = new Dimension(1920, 1080);
			wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
			wordCloud.setBackground(new RectangleBackground(dimension));
			break;
		default:
			break;
		}
		if (wordCloud != null) {
			wordCloud.setPadding(2);
			wordCloud.setColorPalette(new ColorPalette(config.getAwtColorList()));
			wordCloud.setFontScalar(new LinearFontScalar(config.getFontMin(), config.getFontMax()));
			wordCloud.build(wordFrequencies);
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			wordCloud.writeToStreamAsPNG(byteArrayOutputStream);
			final byte[] bytes = byteArrayOutputStream.toByteArray();
			this.reportImage.setImage(bytes);
		}
		return wordCloud;
	}
	/**
	 * @return the dataset
	 */
	public Dataset getDataset() {
		return dataset;
	}

	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	/**
	 * @return the theme
	 */
	public StandardChartTheme getTheme() {
		return theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(StandardChartTheme theme) {
		this.theme = theme;
	}

	/**
	 * @return the lineWidth
	 */
	public float getLineWidth() {
		return lineWidth;
	}

	/**
	 * @param lineWidth the lineWidth to set
	 */
	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * @return the dashWidth
	 */
	public float getDashWidth() {
		return dashWidth;
	}

	/**
	 * @param dashWidth the dashWidth to set
	 */
	public void setDashWidth(float dashWidth) {
		this.dashWidth = dashWidth;
	}

	/**
	 * @return the hexLineColour
	 */
	public String getHexLineColour() {
		return hexLineColour;
	}

	/**
	 * @param hexLineColour the hexLineColour to set
	 */
	public void setHexLineColour(String hexLineColour) {
		this.hexLineColour = hexLineColour;
	}

	/**
	 * @return the hexRangeGridLineColour
	 */
	public String getHexRangeGridLineColour() {
		return hexRangeGridLineColour;
	}

	/**
	 * @param hexRangeGridLineColour the hexRangeGridLineColour to set
	 */
	public void setHexRangeGridLineColour(String hexRangeGridLineColour) {
		this.hexRangeGridLineColour = hexRangeGridLineColour;
	}

	/**
	 * @return the hexTitleColour
	 */
	public String getHexTitleColour() {
		return hexTitleColour;
	}

	/**
	 * @param hexTitleColour the hexTitleColour to set
	 */
	public void setHexTitleColour(String hexTitleColour) {
		this.hexTitleColour = hexTitleColour;
	}

	/**
	 * @return the fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * @param fontName the fontName to set
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public JFreeChart constructTimeSeriesChart(XYChartGraphicPreferences config,
			List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		try {
			JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(config.getChartTitle(),
					config.getxAxisLabel(), config.getyAxisLabel(), (XYDataset) this.dataset, config.isLegend(),
					config.isTooltips(), config.isUrls());
			this.theme.apply(timeSeriesChart);
			return setPreferencesXYChart(timeSeriesChart, config, plotStrokeConfiguration);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart constructPieChart(TweetExtractorChartGraphicPreferences config,
			List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		try {
			JFreeChart timeSeriesChart = ChartFactory.createPieChart(config.getChartTitle(), (PieDataset) this.dataset,
					config.isLegend(), config.isTooltips(), config.isUrls());
			this.theme.apply(timeSeriesChart);
			return setPreferencesPieChart(timeSeriesChart, plotStrokeConfiguration);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart constructPieChart3D(TweetExtractorChartGraphicPreferences config,
			List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		try {
			JFreeChart timeSeriesChart = ChartFactory.createPieChart3D(config.getChartTitle(),
					(PieDataset) this.dataset, config.isLegend(), config.isTooltips(), config.isUrls());
			this.theme.apply(timeSeriesChart);
			return setPreferencesPieChart(timeSeriesChart, plotStrokeConfiguration);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart constructXYBarChart(XYBarChartGraphicPreferences config,
			List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		try {
			JFreeChart xyBarChart = ChartFactory.createXYBarChart(config.getChartTitle(), config.getxAxisLabel(),
					config.isDateAxis(), config.getyAxisLabel(), (IntervalXYDataset) this.dataset);
			this.theme.apply(xyBarChart);
			return setPreferencesXYBarChart(xyBarChart, config, plotStrokeConfiguration);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart constructBarChart(CategoryBarChartGraphicPreferences config,
			List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		try {
			JFreeChart barChart = ChartFactory.createBarChart3D(config.getChartTitle(), config.getxAxisLabel(),
					config.getyAxisLabel(), (CategoryDataset) this.dataset);
			this.theme.apply(barChart);
			return setPreferencesCategoryBarChart(barChart, config, plotStrokeConfiguration);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart setPreferencesXYChart(JFreeChart chart, XYChartGraphicPreferences config,
			List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		if (chart == null) {
			return null;
		}
		XYPlot xyPlot = chart.getXYPlot();
		XYItemRenderer cir = xyPlot.getRenderer();
		for (PlotStrokeConfiguration strokeToUse : plotStrokeConfiguration) {
			BasicStroke stroke = toStroke(strokeToUse);
			cir.setSeriesStroke(strokeToUse.getCategoryIndex(), stroke); // series line style
			chart.getXYPlot().getRenderer().setSeriesPaint(strokeToUse.getCategoryIndex(),
					Color.decode(strokeToUse.getHexLineColour()));
		}
		chart.getXYPlot().setOutlineVisible(config.isOutlineVisible());
		chart.getXYPlot().getRangeAxis().setAxisLineVisible(config.isRangeAxisLineVisible());
		chart.getXYPlot().getRangeAxis().setTickMarksVisible(config.isRangeAxistTickMarksVisible());
		chart.getXYPlot().setRangeGridlineStroke(new BasicStroke());
		chart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.decode(config.getRangeAxisHexTickLabelPaint()));
		chart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.decode(config.getDomainAxisHexTickLabelPaint()));
		chart.setTextAntiAlias(true);
		chart.setAntiAlias(true);
		return chart;
	}

	private JFreeChart setPreferencesPieChart(JFreeChart pieChart,List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		PiePlot plot = (PiePlot) pieChart.getPlot();
		for (PlotStrokeConfiguration plotConfig : plotStrokeConfiguration) {
			plot.setSectionPaint(plotConfig.getCategoryName(), Color.decode(plotConfig.getHexLineColour()));
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}"));
			plot.setLabelBackgroundPaint(new Color(220, 220, 220));
		}
		return pieChart;
	}

	public JFreeChart setPreferencesCategoryBarChart(JFreeChart chart, CategoryBarChartGraphicPreferences config,
			List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		if (chart == null) {
			return null;
		}
		chart.getCategoryPlot().setOutlineVisible(config.isOutlineVisible());
		chart.getCategoryPlot().getRangeAxis().setAxisLineVisible(config.isRangeAxisLineVisible());
		chart.getCategoryPlot().getRangeAxis().setTickMarksVisible(config.isRangeAxistTickMarksVisible());
		chart.getCategoryPlot().setRangeGridlineStroke(new BasicStroke());
		chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.decode(config.getRangeAxisHexTickLabelPaint()));
		chart.getCategoryPlot().getDomainAxis()
				.setTickLabelPaint(Color.decode(config.getDomainAxisHexTickLabelPaint()));
		chart.setTextAntiAlias(true);
		chart.setAntiAlias(true);
		for (PlotStrokeConfiguration strokeToUse : plotStrokeConfiguration) {
			chart.getCategoryPlot().getRenderer().setSeriesPaint(strokeToUse.getCategoryIndex(),
					Color.decode(strokeToUse.getHexLineColour()));
		}
		setPreferencesRendererBarChart(chart, config);
		return chart;
	}

	private JFreeChart setPreferencesXYBarChart(JFreeChart xyBarChart, XYBarChartGraphicPreferences config,
			List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		if (xyBarChart == null) {
			return null;
		}
		xyBarChart.getXYPlot().setOutlineVisible(config.isOutlineVisible());
		xyBarChart.getXYPlot().getRangeAxis().setAxisLineVisible(config.isRangeAxisLineVisible());
		xyBarChart.getXYPlot().getRangeAxis().setTickMarksVisible(config.isRangeAxistTickMarksVisible());
		xyBarChart.getXYPlot().setRangeGridlineStroke(new BasicStroke());
		xyBarChart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.decode(config.getRangeAxisHexTickLabelPaint()));
		xyBarChart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.decode(config.getDomainAxisHexTickLabelPaint()));
		xyBarChart.setTextAntiAlias(true);
		xyBarChart.setAntiAlias(true);
		for (PlotStrokeConfiguration strokeToUse : plotStrokeConfiguration) {
			xyBarChart.getXYPlot().getRenderer().setSeriesPaint(strokeToUse.getCategoryIndex(),
					Color.decode(strokeToUse.getHexLineColour()));
		}
		setPreferencesRendererXYBarChart(xyBarChart);
		return xyBarChart;
	}

	private void setPreferencesRendererBarChart(JFreeChart chart, CategoryBarChartGraphicPreferences config) {
		BarRenderer rend = (BarRenderer) chart.getCategoryPlot().getRenderer();
		rend.setShadowVisible(config.isShadowVisible());
		rend.setShadowXOffset(config.getShadowXOffset());
		rend.setShadowYOffset(config.getShadowYOffset());
		rend.setShadowPaint(Color.decode(config.getHexShadowPaint()));
		rend.setMaximumBarWidth(config.getMaximumBarWidth());
	}

	private void setPreferencesRendererXYBarChart(JFreeChart xyBarChart) {
		XYBarRenderer rend = (XYBarRenderer) xyBarChart.getXYPlot().getRenderer();
		rend.setShadowVisible(true);
		rend.setShadowXOffset(2);
		rend.setShadowYOffset(0);
	}

	private BasicStroke toStroke(PlotStrokeConfiguration category) {
		BasicStroke result = null;
		if (category != null) {
			float[] dash = { category.getStrokeWidth() };
			float[] dot = { category.getStrokeWidth() };
			if (category.getStrokeType().equalsIgnoreCase(Constants.STROKE_LINE)) {
				result = new BasicStroke(category.getStrokeWidth());
			} else if (category.getStrokeType().equalsIgnoreCase(Constants.STROKE_DASH)) {
				result = new BasicStroke(category.getStrokeWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
						dash, 0.0f);
			} else if (category.getStrokeType().equalsIgnoreCase(Constants.STROKE_DOT)) {
				result = new BasicStroke(category.getStrokeWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f,
						dot, 0.0f);
			}
		}
		return result;
	}

	/* Function to convert an Image file into a Blob */
	public byte[] convertFileContentToByteArray(File file) throws IOException {
		byte[] fileContent = null;
		// initialize string buffer to hold contents of file
		StringBuilder fileContentStr = new StringBuilder("");
		try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
			// initialize buffered reader
			String line = null;
			// read lines of file
			while ((line = reader.readLine()) != null) {
				// append line to string buffer
				fileContentStr.append(line).append("\n");
			}
			// convert string to byte array
			fileContent = fileContentStr.toString().trim().getBytes();
		} catch (IOException e) {
			throw new IOException("Unable to convert file to byte array. " + e.getMessage());
		}
		return fileContent;
	}

	/**
	 * @return the report
	 */
	public AnalyticsRepresentableReport getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsRepresentableReport report) {
		this.report = report;
	}

	/**
	 * @return the reportImage
	 */
	public AnalyticsReportImage getReportImage() {
		return reportImage;
	}

	/**
	 * @param reportImage the reportImage to set
	 */
	public void setReportImage(AnalyticsReportImage reportImage) {
		this.reportImage = reportImage;
	}

	/**
	 * @return the preferences
	 */
	public TweetExtractorChartGraphicPreferences getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(TweetExtractorChartGraphicPreferences preferences) {
		this.preferences = preferences;
	}

	/**
	 * @return the chartType
	 */
	public AnalyticsReportImageTypes getChartType() {
		return chartType;
	}

	/**
	 * @param chartType the chartType to set
	 */
	public void setChartType(AnalyticsReportImageTypes chartType) {
		this.chartType = chartType;
	}

}
