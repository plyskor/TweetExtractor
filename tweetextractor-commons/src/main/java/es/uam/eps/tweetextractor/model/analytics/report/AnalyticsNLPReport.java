/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.AnalyticsTweetVolumeByNLPReportRegister;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public abstract class AnalyticsNLPReport extends AnalyticsCategoryReport{
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -6072696707835480742L;
	@ManyToOne
	private TweetExtractorNERConfiguration preferences;
	
	/**
	 * 
	 */
	public AnalyticsNLPReport() {
		super();
	}

	@Override
	public DefaultCategoryDataset constructDefaultCategoryDataset(List<PlotStrokeConfiguration> categories) {
		DefaultCategoryDataset ret = new DefaultCategoryDataset();
		if (this.getCategories() != null && categories != null) {
			for (AnalyticsReportCategory category : this.getCategories()) {
				for (AnalyticsReportCategoryRegister register : category.getResult()) {
					AnalyticsTweetVolumeByNLPReportRegister castedRegister = (AnalyticsTweetVolumeByNLPReportRegister) register;
					if (castedRegister.getValue() != 0) {
						ret.setValue(castedRegister.getValue(), category.getCategoryName(), "");
					}
				}
			}
		}
		return ret;
	}

	@Override
	public XYDataset constructXYDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	@Override
	public IntervalXYDataset constructIntervalXYDataset(List<PlotStrokeConfiguration> categories) {
		return null;
	}

	@Override
	public PieDataset constructPieDataset(List<PlotStrokeConfiguration> categories) {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		if (this.getCategories() != null && categories != null) {
			for (AnalyticsReportCategory category : this.getCategories()) {
				for (AnalyticsReportCategoryRegister register : category.getResult()) {
					AnalyticsTweetVolumeByNLPReportRegister castedRegister = (AnalyticsTweetVolumeByNLPReportRegister) register;
					if (castedRegister.getValue() != 0) {
						pieDataset.setValue(category.getCategoryName(), castedRegister.getValue());
					}
				}
			}
		}
		return pieDataset;
	}
	public int getSizePositiveValue() {
		int ret=0;
		for (AnalyticsReportCategory category : this.getCategories()) {
			if(category.getResult()!=null&&!category.getResult().isEmpty()) {
				AnalyticsTweetVolumeByNLPReportRegister register = (AnalyticsTweetVolumeByNLPReportRegister) category.getResult().get(0);
				if(register.getValue()>0) {
					ret++;
				}
			}
		}
		return ret;
	}

	/**
	 * @return the preferences
	 */
	public TweetExtractorNERConfiguration getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(TweetExtractorNERConfiguration preferences) {
		this.preferences = preferences;
	}
	
}
