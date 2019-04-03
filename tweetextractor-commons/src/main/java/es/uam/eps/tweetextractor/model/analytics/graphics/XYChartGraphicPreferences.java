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
@DiscriminatorValue(value=AnalyticsReportImageTypes.Values.TYPE_TIME_SERIES_CHART)
public class XYChartGraphicPreferences extends TweetExtractorChartGraphicPreferences {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 81784048182671939L;
	@Column(name = "outline_visible",length=8)
	private boolean outlineVisible;
	@Column(name = "range_axis_line_visible",length=8)
	private boolean rangeAxisLineVisible;
	@Column(name = "range_axis_tick_marks_visible",length=8)
	private boolean rangeAxistTickMarksVisible;
	@Column(name = "range_axis_hex_tick_label_paint",length=8)
	private String rangeAxisHexTickLabelPaint;
	@Column(name = "domain_axis_hex_tick_label_paint",length=8)
	private String domainAxisHexTickLabelPaint;
	
	/**
	 * 
	 */
	public XYChartGraphicPreferences() {
		super();
		this.outlineVisible=false;
		this.rangeAxisLineVisible=true;
		this.rangeAxistTickMarksVisible=false;
		this.rangeAxisHexTickLabelPaint="#666666";
		this.domainAxisHexTickLabelPaint="#666666";
		
	}

	/**
	 * @return the outlineVisible
	 */
	public boolean isOutlineVisible() {
		return outlineVisible;
	}

	/**
	 * @param outlineVisible the outlineVisible to set
	 */
	public void setOutlineVisible(boolean outlineVisible) {
		this.outlineVisible = outlineVisible;
	}

	/**
	 * @return the rangeAxisLineVisible
	 */
	public boolean isRangeAxisLineVisible() {
		return rangeAxisLineVisible;
	}

	/**
	 * @param rangeAxisLineVisible the rangeAxisLineVisible to set
	 */
	public void setRangeAxisLineVisible(boolean rangeAxisLineVisible) {
		this.rangeAxisLineVisible = rangeAxisLineVisible;
	}

	/**
	 * @return the rangeAxistTickMarksVisible
	 */
	public boolean isRangeAxistTickMarksVisible() {
		return rangeAxistTickMarksVisible;
	}

	/**
	 * @param rangeAxistTickMarksVisible the rangeAxistTickMarksVisible to set
	 */
	public void setRangeAxistTickMarksVisible(boolean rangeAxistTickMarksVisible) {
		this.rangeAxistTickMarksVisible = rangeAxistTickMarksVisible;
	}

	/**
	 * @return the rangeAxisHexTickLabelPaint
	 */
	public String getRangeAxisHexTickLabelPaint() {
		return rangeAxisHexTickLabelPaint;
	}

	/**
	 * @param rangeAxisHexTickLabelPaint the rangeAxisHexTickLabelPaint to set
	 */
	public void setRangeAxisHexTickLabelPaint(String rangeAxisHexTickLabelPaint) {
		this.rangeAxisHexTickLabelPaint = rangeAxisHexTickLabelPaint;
	}

	/**
	 * @return the domainAxisHexTickLabelPaint
	 */
	public String getDomainAxisHexTickLabelPaint() {
		return domainAxisHexTickLabelPaint;
	}

	/**
	 * @param domainAxisHexTickLabelPaint the domainAxisHexTickLabelPaint to set
	 */
	public void setDomainAxisHexTickLabelPaint(String domainAxisHexTickLabelPaint) {
		this.domainAxisHexTickLabelPaint = domainAxisHexTickLabelPaint;
	}

}
