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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
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

import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;

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
	//Stroke choices
	private static final String STROKE_LINE = "line";
	private static final String STROKE_DASH = "dash";
	private static final String STROKE_DOT = "dot";
	// Properties
		//Line properties
	private float lineWidth = 3.2f;
	private float dashWidth = 5.0f;
		//Colours
	private String hexLineColour = "#E0FB00";
	private String hexRangeGridLineColour = "#C0C0C0";
	private String hexTitleColour = "#E0FB00";
 		//Font properties
	private String fontName = "Lucida Sans";


	/*
		 * 
		 */
	public TweetExtractorChartConstructor(Dataset dataset,TweetExtractorChartGraphicPreferences config) {
		super();
		this.dataset = dataset;
		theme.setTitlePaint(Color.decode(config.getHexTitleColour()));
		theme.setExtraLargeFont(new Font(config.getFontName(), config.getTitleFontType(), config.getTitleFontSize())); // title
		theme.setLargeFont(new Font(config.getFontName(),config.getAxisTitleFontType(), config.getAxisTitleFontSize())); // axis-title
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

	public JFreeChart constructLineChart(String title, String xAxisLabel, String yAxisLabel,
			PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls) {
		try {
			return ChartFactory.createLineChart(title, xAxisLabel, yAxisLabel,
					(CategoryDataset) this.dataset, orientation, legend, tooltips, urls);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart constructTimeSeriesChart(String title, String xAxisLabel, String yAxisLabel, boolean legend,
			boolean tooltips, boolean urls) {
		try {
			JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(title, xAxisLabel, yAxisLabel,
					(XYDataset) this.dataset, legend, tooltips, urls);
			this.theme.apply(timeSeriesChart);
			String[] arg0= {STROKE_LINE};
			int[] arg1= {0};
			return setPreferencesXYChart(timeSeriesChart,arg0,arg1);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public JFreeChart constructXYBarChart(String title, String xAxisLabel, String yAxisLabel,boolean dateAxis) {
		try {
			JFreeChart xyBarChart = ChartFactory.createXYBarChart(title, xAxisLabel, dateAxis, yAxisLabel,(IntervalXYDataset) this.dataset);
			this.theme.apply(xyBarChart);
			return setPreferencesXYBarChart(xyBarChart);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public JFreeChart constructBarChart(String title, String xAxisLabel, String yAxisLabel) {
		try {
			JFreeChart barChart = ChartFactory.createBarChart(title, xAxisLabel, yAxisLabel,(CategoryDataset) this.dataset);
			this.theme.apply(barChart);
			return setPreferencesCategoryBarChart(barChart);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public JFreeChart setPreferencesXYChart(JFreeChart chart, String[] strokeToUse, int[] strokeIndex) {
		if(chart == null) {
			return null;
		}
		XYPlot xyPlot = chart.getXYPlot();
		XYItemRenderer cir = xyPlot.getRenderer();
		if(strokeToUse.length!=strokeIndex.length) {
			return chart;
		}
		for(int i=0; i < strokeIndex.length; i++) {
			BasicStroke stroke = toStroke(strokeToUse[i]);
			cir.setSeriesStroke(strokeIndex[i], stroke); // series line style
		}
		chart.getXYPlot().setOutlineVisible(false);
		chart.getXYPlot().getRangeAxis().setAxisLineVisible(false);
		chart.getXYPlot().getRangeAxis().setTickMarksVisible(false);
		chart.getXYPlot().setRangeGridlineStroke(new BasicStroke());
		chart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.decode("#666666"));
		chart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.decode("#666666"));
		chart.setTextAntiAlias(true);
		chart.setAntiAlias(true);
		chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.decode(this.hexLineColour));
		return chart;
	}
	public JFreeChart setPreferencesCategoryBarChart(JFreeChart chart) {
		if(chart == null) {
			return null;
		}
		chart.getCategoryPlot().setOutlineVisible( false );
	    chart.getCategoryPlot().getRangeAxis().setAxisLineVisible( false );
	    chart.getCategoryPlot().getRangeAxis().setTickMarksVisible( false );
	    chart.getCategoryPlot().setRangeGridlineStroke( new BasicStroke() );
	    chart.getCategoryPlot().getRangeAxis().setTickLabelPaint( Color.decode("#666666") );
	    chart.getCategoryPlot().getDomainAxis().setTickLabelPaint( Color.decode("#666666") );
	    chart.setTextAntiAlias( true );
	    chart.setAntiAlias( true );
	    chart.getCategoryPlot().getRenderer().setSeriesPaint( 0, Color.decode( "#4572a7" ));
	    setPreferencesRendererBarChart(chart);
		return chart;
	}
	private JFreeChart setPreferencesXYBarChart(JFreeChart xyBarChart) {
		if(xyBarChart == null) {
			return null;
		}
		xyBarChart.getXYPlot().setOutlineVisible( false );
	    xyBarChart.getXYPlot().getRangeAxis().setAxisLineVisible( false );
	    xyBarChart.getXYPlot().getRangeAxis().setTickMarksVisible( false );
	    xyBarChart.getXYPlot().setRangeGridlineStroke( new BasicStroke() );
	    xyBarChart.getXYPlot().getRangeAxis().setTickLabelPaint( Color.decode("#666666") );
	    xyBarChart.getXYPlot().getDomainAxis().setTickLabelPaint( Color.decode("#666666") );
	    xyBarChart.setTextAntiAlias( true );
	    xyBarChart.setAntiAlias( true );
	    xyBarChart.getXYPlot().getRenderer().setSeriesPaint( 0, Color.decode( "#4572a7" ));
	    setPreferencesRendererXYBarChart(xyBarChart);
		return xyBarChart;
	}
	private void setPreferencesRendererBarChart(JFreeChart chart) {
		BarRenderer rend = (BarRenderer) chart.getCategoryPlot().getRenderer();
	    rend.setShadowVisible( true );
	    rend.setShadowXOffset( 2 );
	    rend.setShadowYOffset( 0 );
	    rend.setShadowPaint( Color.decode( "#C0C0C0"));
	    rend.setMaximumBarWidth( 0.1);
	}

	private void setPreferencesRendererXYBarChart(JFreeChart xyBarChart) {
		XYBarRenderer rend = (XYBarRenderer) xyBarChart.getXYPlot().getRenderer();
	    rend.setShadowVisible( true );
	    rend.setShadowXOffset( 2 );
	    rend.setShadowYOffset( 0 );
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
	
	/*Function to convert an Image file into a Blob*/
	public byte[] convertFileContentToByteArray(File file) throws IOException {
		byte[] fileContent = null;
	        // initialize string buffer to hold contents of file
		StringBuilder fileContentStr = new StringBuilder("");
		try (BufferedReader reader = new BufferedReader(new FileReader(file));){
	                // initialize buffered reader  
			String line = null;
	                // read lines of file
			while ((line = reader.readLine()) != null) {
	                        //append line to string buffer
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
