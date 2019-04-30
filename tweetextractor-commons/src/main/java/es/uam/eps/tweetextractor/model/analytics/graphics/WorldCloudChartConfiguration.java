/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportImageTypes;

/**
 * @author jose
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value=AnalyticsReportImageTypes.Values.TYPE_WORD_CLOUD_CHART)
public class WorldCloudChartConfiguration extends TweetExtractorChartGraphicPreferences {
	@XmlTransient 
	@Transient
	private static final long serialVersionUID = 8120340985728160942L;
	@Column(name="padding")
	private int padding;
	@Column(name="font_min")
	private int fontMin;
	@Column(name="font_max")
	private int fontMax;
	@Column(name="n_words")
	private int nWords;
	@Column(name="min_word_length")
	private int minWordLength;
	@Column(name="wcc_type")
	private int type;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_worldcloud_configuration_color_lists", joinColumns=@JoinColumn(name="preferences"))
	@Column(name="color")
	private List<String> colorList = new ArrayList<>();
	/**
	 * @param name
	 */
	public WorldCloudChartConfiguration(String name) {
		super(name);
		this.padding=2;
		this.fontMin=25;
		this.fontMax=80;
		this.nWords=350;
		this.minWordLength=3;
		this.colorList.add("#4055F1");
		this.colorList.add("#408DF1");
		this.colorList.add("#40AAF1");
		this.colorList.add("#40C5F1");
		this.colorList.add("#40D3F1");
		this.colorList.add("#FFFFFF");
		this.type=Constants.WCC_CIRCULAR;
		this.clearJFreeAtribbutes();
	}

	/**
	 * 
	 */
	public WorldCloudChartConfiguration() {
		super();
		this.padding=2;
		this.fontMin=25;
		this.fontMax=80;
		this.nWords=350;
		this.minWordLength=3;
		this.colorList.add("#4055F1");
		this.colorList.add("#408DF1");
		this.colorList.add("#40AAF1");
		this.colorList.add("#40C5F1");
		this.colorList.add("#40D3F1");
		this.colorList.add("#FFFFFF");
		this.type=Constants.WCC_CIRCULAR;
		this.clearJFreeAtribbutes();
	}

	/**
	 * @return the padding
	 */
	public int getPadding() {
		return padding;
	}

	/**
	 * @param padding the padding to set
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}

	/**
	 * @return the fontMin
	 */
	public int getFontMin() {
		return fontMin;
	}

	/**
	 * @param fontMin the fontMin to set
	 */
	public void setFontMin(int fontMin) {
		this.fontMin = fontMin;
	}

	/**
	 * @return the fontMax
	 */
	public int getFontMax() {
		return fontMax;
	}

	/**
	 * @param fontMax the fontMax to set
	 */
	public void setFontMax(int fontMax) {
		this.fontMax = fontMax;
	}

	/**
	 * @return the nWords
	 */
	public int getnWords() {
		return nWords;
	}

	/**
	 * @param nWords the nWords to set
	 */
	public void setnWords(int nWords) {
		this.nWords = nWords;
	}

	/**
	 * @return the minWordLength
	 */
	public int getMinWordLength() {
		return minWordLength;
	}

	/**
	 * @param minWordLength the minWordLength to set
	 */
	public void setMinWordLength(int minWordLength) {
		this.minWordLength = minWordLength;
	}

	/**
	 * @return the colorList
	 */
	public List<String> getColorList() {
		return colorList;
	}

	/**
	 * @param colorList the colorList to set
	 */
	public void setColorList(List<String> colorList) {
		this.colorList = colorList;
	}
	public List<Color> getAwtColorList(){
		List<Color> ret = new ArrayList<>();
		for(String hexCode : this.colorList) {
			ret.add(Color.decode(hexCode));
		}
		return ret;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	
}
