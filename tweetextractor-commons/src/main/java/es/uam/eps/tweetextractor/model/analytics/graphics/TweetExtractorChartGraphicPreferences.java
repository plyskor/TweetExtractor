/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.graphics;
import java.awt.Font;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;
@NamedQuery(name="findTweetExtractorChartGraphicPreferencesByUserAndChartType", query="SELECT p from TweetExtractorChartGraphicPreferences p where p.user=:user and p.chartType=:chartType")

/**
 * @author jose
 *
 */
@Entity
@Table(name="perm_analytics_report_graphics_preferences")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Polymorphism(type = PolymorphismType.IMPLICIT)
@DiscriminatorColumn(name = "graphic_type",length=5, discriminatorType = DiscriminatorType.STRING)
public abstract class TweetExtractorChartGraphicPreferences implements  Serializable{
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 6970311968280155891L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int id;
	@XmlTransient
	@Column(name = "graphic_type", length=5,nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	public AnalyticsReportImageTypes chartType;
	@XmlTransient
	@ManyToOne
	private User user;
	@Column(name = "name",length=30)
	private String name;
	//Font name to use
	private String fontName;
	//Colour for the chart title
	@Column(name = "hex_title_colour",length=8)
	private String hexTitleColour;
	//Colour for range gird line
	@Column(name = "hex_range_grid_line_colour",length=8)
	private String hexRangeGridLineColour;
	@Column(name = "hex_plot_background_paint_colour",length=8)
	private String hexPlotBackgroundPaintColour;
	@Column(name = "hex_chart_background_paint_colour",length=8)
	private String hexChartBackgroundPaintColour;
	@Column(name = "hex_grid_band_paint_colour",length=8)
	private String hexGridBandPaintColour;
	@Column(name = "hex_axis_label_colour",length=8)
	private String hexAxisLabelColour = "#666666";
	@Column(name = "title_font_type")
	private int titleFontType;
	@Column(name = "title_font_size")
	private int titleFontSize;
	@Column(name = "axis_title_font_type")
	private int axisTitleFontType;
	@Column(name = "axis_title_font_size")
	private int axisTitleFontSize;
	@Column(name = "regular_font_type")
	private int regularFontType;
	@Column(name = "regular_font_size")
	private int regularFontSize;
	@Column(name = "legend")
	private boolean legend;
	@Column(name = "tooltips")
	private boolean tooltips;
	@Column(name = "urls")
	private boolean urls;

	/**
	 * 
	 */
	public TweetExtractorChartGraphicPreferences() {
		super();
		this.fontName="Palatino";
		this.hexTitleColour="#000000";
		this.hexRangeGridLineColour="#C0C0C0";
		this.hexPlotBackgroundPaintColour="#FFFFFF";
		this.hexChartBackgroundPaintColour="#FFFFFF";
		this.hexGridBandPaintColour="#FF0000";
		this.titleFontType=Font.PLAIN;
		this.titleFontSize=16;
		this.axisTitleFontType=Font.BOLD;
		this.axisTitleFontSize=15;
		this.regularFontType=Font.PLAIN;
		this.regularFontSize=11;
		this.legend=true;
		this.tooltips=false;
		this.urls=false;
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
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * @return the hexPlotBackgroundPaintColour
	 */
	public String getHexPlotBackgroundPaintColour() {
		return hexPlotBackgroundPaintColour;
	}
	/**
	 * @param hexPlotBackgroundPaintColour the hexPlotBackgroundPaintColour to set
	 */
	public void setHexPlotBackgroundPaintColour(String hexPlotBackgroundPaintColour) {
		this.hexPlotBackgroundPaintColour = hexPlotBackgroundPaintColour;
	}
	/**
	 * @return the hexAxisLabelColour
	 */
	public String getHexAxisLabelColour() {
		return hexAxisLabelColour;
	}
	/**
	 * @param hexAxisLabelColour the hexAxisLabelColour to set
	 */
	public void setHexAxisLabelColour(String hexAxisLabelColour) {
		this.hexAxisLabelColour = hexAxisLabelColour;
	}
	/**
	 * @return the titleFontType
	 */
	public int getTitleFontType() {
		return titleFontType;
	}
	/**
	 * @param titleFontType the titleFontType to set
	 */
	public void setTitleFontType(int titleFontType) {
		this.titleFontType = titleFontType;
	}
	/**
	 * @return the titleFontSize
	 */
	public int getTitleFontSize() {
		return titleFontSize;
	}
	/**
	 * @param titleFontSize the titleFontSize to set
	 */
	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}
	/**
	 * @return the axisTitleFontType
	 */
	public int getAxisTitleFontType() {
		return axisTitleFontType;
	}
	/**
	 * @param axisTitleFontType the axisTitleFontType to set
	 */
	public void setAxisTitleFontType(int axisTitleFontType) {
		this.axisTitleFontType = axisTitleFontType;
	}
	/**
	 * @return the axisTitleFontSize
	 */
	public int getAxisTitleFontSize() {
		return axisTitleFontSize;
	}
	/**
	 * @param axisTitleFontSize the axisTitleFontSize to set
	 */
	public void setAxisTitleFontSize(int axisTitleFontSize) {
		this.axisTitleFontSize = axisTitleFontSize;
	}
	/**
	 * @return the regularFontType
	 */
	public int getRegularFontType() {
		return regularFontType;
	}
	/**
	 * @param regularFontType the regularFontType to set
	 */
	public void setRegularFontType(int regularFontType) {
		this.regularFontType = regularFontType;
	}
	/**
	 * @return the regularFontSize
	 */
	public int getRegularFontSize() {
		return regularFontSize;
	}
	/**
	 * @param regularFontSize the regularFontSize to set
	 */
	public void setRegularFontSize(int regularFontSize) {
		this.regularFontSize = regularFontSize;
	}
	/**
	 * @return the hexChartBackgroundPaintColour
	 */
	public String getHexChartBackgroundPaintColour() {
		return hexChartBackgroundPaintColour;
	}
	/**
	 * @param hexChartBackgroundPaintColour the hexChartBackgroundPaintColour to set
	 */
	public void setHexChartBackgroundPaintColour(String hexChartBackgroundPaintColour) {
		this.hexChartBackgroundPaintColour = hexChartBackgroundPaintColour;
	}
	/**
	 * @return the hexGridBandPaintColour
	 */
	public String getHexGridBandPaintColour() {
		return hexGridBandPaintColour;
	}
	/**
	 * @param hexGridBandPaintColour the hexGridBandPaintColour to set
	 */
	public void setHexGridBandPaintColour(String hexGridBandPaintColour) {
		this.hexGridBandPaintColour = hexGridBandPaintColour;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the legend
	 */
	public boolean isLegend() {
		return legend;
	}
	/**
	 * @param legend the legend to set
	 */
	public void setLegend(boolean legend) {
		this.legend = legend;
	}
	/**
	 * @return the tooltips
	 */
	public boolean isTooltips() {
		return tooltips;
	}
	/**
	 * @param tooltips the tooltips to set
	 */
	public void setTooltips(boolean tooltips) {
		this.tooltips = tooltips;
	}
	/**
	 * @return the urls
	 */
	public boolean isUrls() {
		return urls;
	}
	/**
	 * @param urls the urls to set
	 */
	public void setUrls(boolean urls) {
		this.urls = urls;
	}

}
