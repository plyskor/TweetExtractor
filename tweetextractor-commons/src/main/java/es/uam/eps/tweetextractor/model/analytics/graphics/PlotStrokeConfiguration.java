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
	@ManyToOne
	private AnalyticsReportImage chart; 
	/**
	 * 
	 */
	
	public PlotStrokeConfiguration() {
		super();
		this.hexLineColour="#E0FB00";
	}
	public PlotStrokeConfiguration(String strokeType, int categoryIndex) {
		super();
		this.strokeType = strokeType;
		this.categoryIndex = categoryIndex;
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

}
