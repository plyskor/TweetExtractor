/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.springframework.stereotype.Controller;

import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;


/**
 * @author jgarciadelsaz
 *
 */
@Controller
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AnalyticsRepresentableReport extends AnalyticsReport {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 4686141095130999306L;
	@OneToMany(orphanRemoval=true,fetch=FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.REMOVE},mappedBy="report")
	@Fetch(value = FetchMode.SUBSELECT)
	protected List<AnalyticsReportImage> graphics;
	/**
	 * 
	 */
	public AnalyticsRepresentableReport() {
		super();
	}
	/**
	 * @return the graphics
	 */
	public List<AnalyticsReportImage> getGraphics() {
		return graphics;
	}
	/**
	 * @param graphics the graphics to set
	 */
	public void setGraphics(List<AnalyticsReportImage> graphics) {
		this.graphics = graphics;
	}
	
	public abstract DefaultCategoryDataset constructDefaultCategoryDataset(String categoryLabel);
	public abstract XYDataset constructXYDataset(String categoryLabel);
	public abstract IntervalXYDataset constructIntervalXYDataset(String categoryLabel) ;
}
