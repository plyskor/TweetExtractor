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
@DiscriminatorValue(value=AnalyticsReportImageTypes.Values.TYPE_XY_BAR_CHART)
public class XYBarChartGraphicPreferences extends XYChartGraphicPreferences {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -5257004694368835335L;
	@Column(name = "date_axis")
	private boolean dateAxis;
	@Column(name = "shadow_visible")
	private boolean shadowVisible;
	@Column(name = "shadow_x_offset")
	private int shadowXOffset;
	@Column(name = "shadow_y_offset")
	private int shadowYOffset;
	public XYBarChartGraphicPreferences(String name) {
		super(name);
		this.shadowVisible=true;
		this.shadowXOffset=2;
		this.shadowYOffset=0;
	}
	
	/**
	 * @param name
	 */
	public XYBarChartGraphicPreferences() {
		super("");
	}

	/**
	 * @return the dateAxis
	 */
	public boolean isDateAxis() {
		return dateAxis;
	}
	/**
	 * @param dateAxis the dateAxis to set
	 */
	public void setDateAxis(boolean dateAxis) {
		this.dateAxis = dateAxis;
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

}
