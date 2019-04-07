/**
 * 
 */
package es.uam.eps.tweetextractor.analytics.graphics.constructor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uam.eps.tweetextractor.model.analytics.graphics.CategoryBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYChartGraphicPreferences;

/**
 * @author jgarciadelsaz
 *
 */
public class TweetExtractorChartConstructor {
	// Set of data to build the chart
	private Dataset dataset;
	// Logger
	private Logger logger = LoggerFactory.getLogger(TweetExtractorChartConstructor.class);
	// Theme for charts (JFreeChart)
	private StandardChartTheme theme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();
	// Stroke choices
	private static final String STROKE_LINE = "line";
	private static final String STROKE_DASH = "dash";
	private static final String STROKE_DOT = "dot";
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
	public TweetExtractorChartConstructor(Dataset dataset, TweetExtractorChartGraphicPreferences config) {
		super();
		this.dataset = dataset;
		theme.setTitlePaint(Color.decode(config.getHexTitleColour()));
		theme.setExtraLargeFont(new Font(config.getFontName(), config.getTitleFontType(), config.getTitleFontSize())); // title
		theme.setLargeFont(
				new Font(config.getFontName(), config.getAxisTitleFontType(), config.getAxisTitleFontSize())); // axis-title
		theme.setRegularFont(new Font(config.getFontName(), config.getRegularFontType(), config.getRegularFontSize()));
		theme.setRangeGridlinePaint(Color.decode(config.getHexRangeGridLineColour()));
		theme.setPlotBackgroundPaint(Color.decode(config.getHexPlotBackgroundPaintColour()));
		theme.setChartBackgroundPaint(Color.decode(config.getHexChartBackgroundPaintColour()));
		theme.setGridBandPaint(Color.decode(config.getHexGridBandPaintColour()));
		theme.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		theme.setBarPainter(new StandardBarPainter());
		theme.setAxisLabelPaint(Color.decode(config.getHexAxisLabelColour()));
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

	public JFreeChart constructTimeSeriesChart(String title, XYChartGraphicPreferences config ,List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		try {
			JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(title, config.getxAxisLabel(),
					config.getyAxisLabel(), (XYDataset) this.dataset, config.isLegend(), config.isTooltips(), config.isUrls());
			this.theme.apply(timeSeriesChart);
			return setPreferencesXYChart(timeSeriesChart, config,plotStrokeConfiguration);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart constructXYBarChart(String title, XYBarChartGraphicPreferences config,List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		try {
			JFreeChart xyBarChart = ChartFactory.createXYBarChart(title, config.getxAxisLabel(), config.isDateAxis(),
					config.getyAxisLabel(), (IntervalXYDataset) this.dataset);
			this.theme.apply(xyBarChart);
			return setPreferencesXYBarChart(xyBarChart, config,plotStrokeConfiguration);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart constructBarChart(String title, CategoryBarChartGraphicPreferences config,List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		try {
			JFreeChart barChart = ChartFactory.createBarChart(title, config.getxAxisLabel(), config.getyAxisLabel(),
					(CategoryDataset) this.dataset);
			this.theme.apply(barChart);
			return setPreferencesCategoryBarChart(barChart, config,plotStrokeConfiguration);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart setPreferencesXYChart(JFreeChart chart, XYChartGraphicPreferences config, List<PlotStrokeConfiguration> plotStrokeConfiguration) {
		if (chart == null) {
			return null;
		}
		XYPlot xyPlot = chart.getXYPlot();
		XYItemRenderer cir = xyPlot.getRenderer();
		for (PlotStrokeConfiguration strokeToUse :plotStrokeConfiguration) {
			BasicStroke stroke = toStroke(strokeToUse.getStrokeType());
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

	public JFreeChart setPreferencesCategoryBarChart(JFreeChart chart, CategoryBarChartGraphicPreferences config,List<PlotStrokeConfiguration> plotStrokeConfiguration) {
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

	private JFreeChart setPreferencesXYBarChart(JFreeChart xyBarChart, XYBarChartGraphicPreferences config,List<PlotStrokeConfiguration> plotStrokeConfiguration) {
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
		setPreferencesRendererXYBarChart(xyBarChart, config);
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

	private void setPreferencesRendererXYBarChart(JFreeChart xyBarChart, XYBarChartGraphicPreferences config) {
		XYBarRenderer rend = (XYBarRenderer) xyBarChart.getXYPlot().getRenderer();
		rend.setShadowVisible(true);
		rend.setShadowXOffset(2);
		rend.setShadowYOffset(0);
	}

	private BasicStroke toStroke(String style) {
		BasicStroke result = null;
		if (style != null) {
			float[] dash = { dashWidth };
			float[] dot = { this.lineWidth };
			if (style.equalsIgnoreCase(STROKE_LINE)) {
				result = new BasicStroke(lineWidth);
			} else if (style.equalsIgnoreCase(STROKE_DASH)) {
				result = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
			} else if (style.equalsIgnoreCase(STROKE_DOT)) {
				result = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, dot, 0.0f);
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
}
