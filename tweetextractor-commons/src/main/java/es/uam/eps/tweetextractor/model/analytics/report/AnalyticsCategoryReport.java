/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Controller;

import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
@NamedQuery(name="findCategoriesByReport", query="SELECT c from AnalyticsReportCategory c WHERE c.report.id=:id")

/**
 * @author jose
 *
 */
@Controller
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AnalyticsCategoryReport extends AnalyticsRepresentableReport {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -1016706791178392779L;
	@OneToMany(fetch=FetchType.EAGER,cascade = {CascadeType.ALL},mappedBy="report")
	private List<AnalyticsReportCategory> categories;
	
	/**
	 * 
	 */
	public AnalyticsCategoryReport() {
		super();
		categories=new ArrayList<>();
	}
	/**
	 * @return the categories
	 */
	public List<AnalyticsReportCategory> getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<AnalyticsReportCategory> categories) {
		this.categories = categories;
	}
	public AnalyticsReportCategory getCategoryByName(String name) {
		if(getCategories()==null) {
			return null;
		}
		for(AnalyticsReportCategory category:getCategories()) {
			if(category.getCategoryName().equals(name)) {
				return category;
			}
		}
		return null;
	}
	@Override
	public boolean isEmpty() {
		boolean toReturn=true;
		if(this.getCategories()==null||this.getCategories().isEmpty()) {
			return toReturn;
		}
		for(AnalyticsReportCategory category: this.getCategories()) {
			if(category.getResult()!=null&&!category.getResult().isEmpty()) {
				toReturn=false;
			}
		}
		return toReturn;
	}
}
