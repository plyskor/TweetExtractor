/**
 * 
 */
package es.uam.eps.tweetextractor.model.filter.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.FilterTypes;
import es.uam.eps.tweetextractor.model.filter.Filter;
import es.uam.eps.tweetextractor.util.LocalDateAdapter;
import javafx.beans.property.StringProperty;

/**
 * @author Jose Antonio García del Saz
 *
 */

@Entity
@DiscriminatorValue(value=FilterTypes.Values.TYPE_FILTER_SINCE)
@XmlRootElement(name="filterSince")
public class FilterSince extends Filter {
	@Column(name="since_date")
	private LocalDate date;
	/**
	 * 
	 */
	public FilterSince() {
		this.setLABEL(Constants.STRING_FILTER_SINCE);
	}
	public FilterSince(FilterSince filter) {
		this.setLABEL(Constants.STRING_FILTER_SINCE);
		if(filter!=null) {
			this.date=filter.getDate();
			this.summary=filter.getSummary();
			this.summaryProperty.set(filter.getSummary());
		}
	}

	@XmlTransient
	@Override
	public StringProperty getSummaryProperty() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(date!=null) {
			summaryProperty.set("Since: "+(date).format(formatter));
			summary=new String(summaryProperty.get());
		}
		return summaryProperty;
	}
	/**
	 * @return the date
	 */
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Override
	public String toQuery() {
		if(date==null) {
			return null;
		}else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return new String("since:"+(date).format(formatter)+" ");
		}
	}
	@Override
	public void loadXml() {
		
	}
	
}
