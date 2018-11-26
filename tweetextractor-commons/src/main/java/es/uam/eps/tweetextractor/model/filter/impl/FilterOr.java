/**
 * 
 */
package es.uam.eps.tweetextractor.model.filter.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import es.uam.eps.tweetextractor.model.Constants.FilterTypes;
import es.uam.eps.tweetextractor.model.filter.Filter;
import javafx.collections.ObservableList;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Entity
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_OR)
@XmlRootElement(name="FilterOr")
public class FilterOr extends Filter {
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(nullable=true)
	private List<Filter> filterList=new ArrayList<Filter>();


	/**
	 * 
	 */
	public FilterOr() {
		super();
		this.summary=new String();
		this.setLABEL("");
	}
	
	public void addAll(ObservableList<Filter> observableList) {
		if(observableList!=null) {
			for(int i=0;i<observableList.size();i++) {
				filterList.add(observableList.get(i));
				if(i==observableList.size()-1) {
					summary=summary.concat("("+observableList.get(i).getSummary() + ")");
				}else {
					summary=summary.concat("("+observableList.get(i).getSummary() + ") OR ");
				}
			}
			summaryProperty.set(summary);
		}
	}

	/**
	 * @return the filterList
	 */
	@XmlElementWrapper(name = "filterOrList")
	@XmlAnyElement(lax = true)
	public List<Filter> getFilterList() {
		return filterList;
	}

	/**
	 * @param filterList the filterList to set
	 */
	public void setFilterList(List<Filter> filterList) {
		this.filterList = filterList;
	}


	@Override
	public String toQuery() {
		if(filterList==null) {
			return null;
		}else {
			String ret = new String("(");
			for(int i=0;i<filterList.size();i++) {
				if(i==filterList.size()-1) {
					ret=ret.concat(filterList.get(i).toQuery());
				}else {
					ret=ret.concat(filterList.get(i).toQuery()+" OR ");
				}
			}
			ret=ret.concat(") ");
			return ret;
		}
	}

	@Override
	public void loadXml() {
		// TODO Auto-generated method stub
		
	}

}
