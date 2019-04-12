/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.graphics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author jose
 *
 */
@Entity
@Table(name = "perm_plot_stroke_configuration")
public class PlotStrokeConfiguration implements Serializable{
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 2358809972343867586L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int id;
	@Column(name = "stroke_type",length=5)
	private String strokeType;
	@Column(name = "category_index")
	private int categoryIndex;
	@Column(name = "hex_line_colour",length=8)
	private String hexLineColour ;
	@Column(name = "category_name",length=20)
	private String categoryName;
	@Column(name = "category_label",length=50)
	private String categoryLabel;
	@Column(name = "stroke_width")
	private float strokeWidth;
	@ManyToOne
	private AnalyticsReportImage chart; 
	/**
	 * 
	 */
	
	public PlotStrokeConfiguration() {
		super();
		this.hexLineColour="#E0FB00";
	}
	
	/**
	 * @param strokeType the stroke type
	 * @param categoryIndex the category index
	 * @param hexLineColour the plot colour
	 * @param categoryName the category name
	 * @param categoryLabel the category label
	 * @param strokeWidth the stroke width 
	 * @param chart the graphic chart
	 */
	public PlotStrokeConfiguration(String strokeType, int categoryIndex, String hexLineColour,
			String categoryName, String categoryLabel, float strokeWidth, AnalyticsReportImage chart) {
		super();
		this.strokeType = strokeType;
		this.categoryIndex = categoryIndex;
		this.hexLineColour = hexLineColour;
		this.categoryName = categoryName;
		this.categoryLabel = categoryLabel;
		this.strokeWidth = strokeWidth;
		this.chart = chart;
	}

	/**
	 * @return the strokeType
	 */
	public String getStrokeType() {
		return strokeType;
	}
	/**
	 * @param strokeType the strokeType to set
	 */
	public void setStrokeType(String strokeType) {
		this.strokeType = strokeType;
	}
	/**
	 * @return the categoryIndex
	 */
	public int getCategoryIndex() {
		return categoryIndex;
	}
	/**
	 * @param categoryIndex the categoryIndex to set
	 */
	public void setCategoryIndex(int categoryIndex) {
		this.categoryIndex = categoryIndex;
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
	 * @return the chart
	 */
	public AnalyticsReportImage getChart() {
		return chart;
	}
	/**
	 * @param chart the chart to set
	 */
	public void setChart(AnalyticsReportImage chart) {
		this.chart = chart;
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
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the categoryLabel
	 */
	public String getCategoryLabel() {
		return categoryLabel;
	}
	/**
	 * @param categoryLabel the categoryLabel to set
	 */
	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}
	/**
	 * @return the strokeWidth
	 */
	public float getStrokeWidth() {
		return strokeWidth;
	}
	/**
	 * @param strokeWidth the strokeWidth to set
	 */
	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

}
