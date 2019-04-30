/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.springframework.stereotype.Controller;
import javax.persistence.JoinColumn;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingReportRegister;

/**
 * @author jose
 *
 */

@Controller
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TrendsReport extends AnalyticsCategoryReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -5010570674578247720L;
	@Column(name="n")
	private int n;
	@ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	   @JoinTable(name = "trends_report_extraction", 
	         joinColumns = { @JoinColumn(name = "report_identifier") }, 
	         inverseJoinColumns = { @JoinColumn(name = "extraction_identifier") })
    private List<Extraction> extractions = new ArrayList<>();
	@ElementCollection(fetch=FetchType.LAZY)
	@CollectionTable(name="perm_trend_report_filter_list", joinColumns=@JoinColumn(name="report"))
	@Column(name="filter_element")
	private List<String> stringFilterList= new ArrayList<>();
	/**
	 * 
	 */
	public TrendsReport() {
		super();
	}
	
	/**
	 * @param n
	 */
	public TrendsReport(int n) {
		super();
		this.n = n;
	}

	/**
	 * @return the n
	 */
	public int getN() {
		return n;
	}
	/**
	 * @param n the n to set
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * @return the extractions
	 */
	public List<Extraction> getExtractions() {
		return extractions;
	}

	/**
	 * @param extractions the extractions to set
	 */
	public void setExtractions(List<Extraction> extractions) {
		this.extractions = extractions;
	}

	/**
	 * @return the stringFilterList
	 */
	public List<String> getStringFilterList() {
		return stringFilterList;
	}

	/**
	 * @param stringFilterList the stringFilterList to set
	 */
	public void setStringFilterList(List<String> stringFilterList) {
		this.stringFilterList = stringFilterList;
	}
	@Override
	public PieDataset constructPieDataset(List<PlotStrokeConfiguration> categories) {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
		if (this.getCategories() != null && categories != null) {
			List<AnalyticsReportCategoryRegister> list = this.getCategories().get(0).getResult();
			for(AnalyticsReportCategoryRegister register : list) {
				TrendingReportRegister castedRegister = (TrendingReportRegister) register;
				pieDataset.setValue(castedRegister.getLabel(), castedRegister.getVolume());
			}
		}
		return pieDataset;
	}
	@Override
	public DefaultCategoryDataset constructDefaultCategoryDataset(List<PlotStrokeConfiguration> categories) {
		DefaultCategoryDataset ret = new DefaultCategoryDataset();
		if (this.getCategories() != null && categories != null) {
			List<AnalyticsReportCategoryRegister> list = this.getCategories().get(0).getResult();
			for(AnalyticsReportCategoryRegister register : list) {
				TrendingReportRegister castedRegister = (TrendingReportRegister) register;
				ret.setValue(castedRegister.getVolume(), castedRegister.getLabel(), "Number of tweets");
			}
		}
		return ret;
	}
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport#constructXYDataset(java.util.List)
	 */
	@Override
	public XYDataset constructXYDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport#constructIntervalXYDataset(java.util.List)
	 */
	@Override
	public IntervalXYDataset constructIntervalXYDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	
}
