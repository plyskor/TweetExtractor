/**
 * 
 */
package es.uam.eps.tweetextractor.model.analytics.report.register;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;

/**
 * @author jose
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TimelineReportRegister<T extends Serializable> extends AnalyticsReportCategoryRegister implements Serializable {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -1191008698226686168L;
	@Column(name="year")
	private int year;
	@Column(name="month")
	private int month;
	@Column(name="day")
	private int day;
	@ManyToOne
	private AnalyticsReportCategory category;
	/**
	 * 
	 */
	public TimelineReportRegister() {
		super();
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * @return the value
	 */
	public abstract T getValue();
	/**
	 * @param value the value to set
	 */
	public abstract void setValue(T value);

	/**
	 * @return the category
	 */
	public AnalyticsReportCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(AnalyticsReportCategory category) {
		this.category = category;
	}

}
