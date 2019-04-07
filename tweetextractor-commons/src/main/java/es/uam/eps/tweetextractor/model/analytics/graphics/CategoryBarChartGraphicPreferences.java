/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.graphics;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;

/**
 * @author jose
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value=AnalyticsReportImageTypes.Values.TYPE_BAR_CHART)
public class CategoryBarChartGraphicPreferences extends XYChartGraphicPreferences {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -3356308374359568140L;
	@Column(name = "shadow_visible")
	private boolean shadowVisible;
	@Column(name = "shadow_x_offset")
	private int shadowXOffset;
	@Column(name = "shadow_y_offset")
	private int shadowYOffset;
	@Column(name = "hex_shadow_paint",length=8)
	private String hexShadowPaint;
	@Column(name = "maximum_bar_width")
	private double maximumBarWidth;
	public CategoryBarChartGraphicPreferences() {
		super();
		this.shadowVisible=true;
		this.shadowXOffset=2;
		this.shadowYOffset=0;
		this.hexShadowPaint="#C0C0C0";
		this.maximumBarWidth=0.1;
	}
	/**
	 * @return the shadowVisible
	 */
	public boolean isShadowVisible() {
		return shadowVisible;
	}
	/**
	 * @param shadowVisible the shadowVisible to set
	 */
	public void setShadowVisible(boolean shadowVisible) {
		this.shadowVisible = shadowVisible;
	}
	/**
	 * @return the shadowXOffset
	 */
	public int getShadowXOffset() {
		return shadowXOffset;
	}
	/**
	 * @param shadowXOffset the shadowXOffset to set
	 */
	public void setShadowXOffset(int shadowXOffset) {
		this.shadowXOffset = shadowXOffset;
	}
	/**
	 * @return the shadowYOffset
	 */
	public int getShadowYOffset() {
		return shadowYOffset;
	}
	/**
	 * @param shadowYOffset the shadowYOffset to set
	 */
	public void setShadowYOffset(int shadowYOffset) {
		this.shadowYOffset = shadowYOffset;
	}
	/**
	 * @return the hexShadowPaint
	 */
	public String getHexShadowPaint() {
		return hexShadowPaint;
	}
	/**
	 * @param hexShadowPaint the hexShadowPaint to set
	 */
	public void setHexShadowPaint(String hexShadowPaint) {
		this.hexShadowPaint = hexShadowPaint;
	}
	/**
	 * @return the maximumBarWidth
	 */
	public double getMaximumBarWidth() {
		return maximumBarWidth;
	}
	/**
	 * @param maximumBarWidth the maximumBarWidth to set
	 */
	public void setMaximumBarWidth(double maximumBarWidth) {
		this.maximumBarWidth = maximumBarWidth;
	}


}
