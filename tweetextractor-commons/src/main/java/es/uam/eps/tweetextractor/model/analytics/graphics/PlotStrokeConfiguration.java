/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.graphics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jose
 *
 */
@Entity
@Table(name = "perm_plot_stroke_configuration")
public class PlotStrokeConfiguration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int id;
	@Column(name = "stroke_type",length=5)
	private String strokeType;
	@Column(name = "category_index")
	private int categoryIndex;
	
	private XYChartGraphicPreferences preferences; 
	/**
	 * 
	 */
	
	public PlotStrokeConfiguration() {
		super();
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

}
