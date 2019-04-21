/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.impl;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractor.model.reference.nlp.CustomStopWordsList;

/**
 * @author jose
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = AnalyticsReportTypes.Values.TYPE_TRENDING_WORDS_REPORT)
@XmlRootElement(name = "trendingWordsReport")
public class TrendingWordsReport extends TrendsReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -6935441753670688388L;
	@ManyToOne
	private AvailableTwitterLanguage language;
	@ManyToOne
	private CustomStopWordsList stopWordsList;
	/**
	 * @param language
	 * @param stopWordsList
	 */
	public TrendingWordsReport(AvailableTwitterLanguage language, CustomStopWordsList stopWordsList) {
		super();
		this.language = language;
		this.stopWordsList = stopWordsList;
	}
	/**
	 * 
	 */
	public TrendingWordsReport() {
		super();
	}
	/**
	 * @param n
	 */
	public TrendingWordsReport(int n) {
		super(n);
	}
	/**
	 * @return the language
	 */
	public AvailableTwitterLanguage getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(AvailableTwitterLanguage language) {
		this.language = language;
	}
	/**
	 * @return the stopWordsList
	 */
	public CustomStopWordsList getStopWordsList() {
		return stopWordsList;
	}
	/**
	 * @param stopWordsList the stopWordsList to set
	 */
	public void setStopWordsList(CustomStopWordsList stopWordsList) {
		this.stopWordsList = stopWordsList;
	}
	

}
