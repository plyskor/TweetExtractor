/**
 * 
 */
package es.uam.eps.tweetextractor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.filter.Filter;
import es.uam.eps.tweetextractor.util.DateAdapter;
@NamedQuery(name="findExtractionsByUser", query="SELECT e from Extraction e WHERE e.user=:user")


/**
 * @author Jose Antonio García del Saz
 *
 */

@XmlRootElement(name = "extraction")
@XmlSeeAlso({ es.uam.eps.tweetextractor.model.filter.impl.FilterHashtag.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterContains.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterContainsExact.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterList.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterMention.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterSince.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterUntil.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterOr.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterNot.class,
		es.uam.eps.tweetextractor.model.filter.impl.FilterFrom.class })
@XmlType(propOrder={"id","idDB","creationDate","lastModificationDate","filterXmlList"})
@Entity
@Controller
@Table(name = "perm_extraction")
public class Extraction implements Serializable {
	/**
	 * 
	 */
	@XmlTransient
	@Transient
	transient UserServiceInterface service;
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 5761562204007375522L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int idDB;
	@Column(name = "id", length=36, unique=true, nullable=false)
	private String id;
	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Column(name = "last_modification_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModificationDate;
	@XmlTransient
	@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REMOVE},mappedBy="extraction")
	private List<Tweet> tweetList;
	@OneToMany(fetch=FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.REMOVE},mappedBy="extraction")
	@XmlTransient
	private List<Filter> filterList;
	@Transient
	private transient List<Filter> filterXmlList = new ArrayList<>();
	@XmlTransient
	@ManyToOne
	private User user;
	@Column(name = "extracting")
	private boolean extracting;
	public Extraction() {
		creationDate = new Date();
		lastModificationDate = new Date();
		tweetList = new ArrayList<>();
		filterList =new ArrayList<>();
		id = UUID.randomUUID().toString();
		extracting=false;
	}

	/**
	 * @return the idDB
	 */
	public int getIdDB() {
		return idDB;
	}

	/**
	 * @param idDB the idDB to set
	 */
	public void setIdDB(int idDB) {
		this.idDB = idDB;
	}

	public int howManyTweets() {
		if (tweetList == null) {
			return 0;
		} else {
			return tweetList.size();
		}
	}

	/**
	 * @return the user
	 */
	@XmlTransient
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the extracting
	 */
	public boolean isExtracting() {
		return extracting;
	}

	/**
	 * @param extracting the extracting to set
	 */
	public void setExtracting(boolean extracting) {
		this.extracting = extracting;
	}

	/**
	 * @return the creationDate
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the lastModificationDate
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	/**
	 * @param lastModificationDate the lastModificationDate to set
	 */
	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	/**
	 * @return the tweetList
	 */
	@XmlTransient
	public List<Tweet> getTweetList() {
		return tweetList;
	}

	/**
	 * @param tweetList the tweetList to set
	 */
	public void setTweetList(List<Tweet> tweetList) {
		this.tweetList = tweetList;
	}

	/**
	 * @param list the list of tweets to add
	 */
	public void addTweets(List<Tweet> list) {
		if (list != null) {
			tweetList.addAll(list);
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param tweet the tweet to add
	 */
	public void addTweet(Tweet tweet) {
		if (tweet != null) {
			tweet.setExtraction(this);
			tweetList.add(tweet);
		}
	}

	/**
	 * @param list the list of filters to add
	 */
	public void addFilters(List<Filter> list) {
		if (list != null) {
			for(Filter f:list) {
				f.setExtraction(this);
			}
			filterList.addAll(list);
			filterXmlList.addAll(list);
		}
	}

	/**
	 * @return the filterXmlList
	 */
	@XmlElementWrapper(name="filters")
	@XmlAnyElement(lax = true)
	public List<Filter> getFilterXmlList() {
		return filterXmlList;
	}

	/**
	 * @param filterXmlList the filterXmlList to set
	 */
	public void setFilterXmlList(List<Filter> filterXmlList) {
		this.filterXmlList = filterXmlList;
	}

	/**
	 * @param filter the filter to add
	 */
	public void addFilter(Filter filter) {
		if (filter != null) {
			filterList.add(filter);
			filterXmlList.add(filter);
		}
	}

	/**
	 * @return the filterList
	 */
	@XmlTransient
	public List<Filter> getFilterList() {
		return filterList;
	}

	/**
	 * @param filterList the filterList to set
	 */
	public void setFilterList(List<Filter> filterList) {
		this.filterList = filterList;
	}
	public void loadFiltersXml() {
	for(Filter filter:filterXmlList) {
		filterList.add(filter);
	}
	}
	public boolean contains(Tweet toadd) {
		if(toadd==null||tweetList==null||tweetList.isEmpty()) return false;
		for(Tweet own : tweetList) {
			if(own.getId()==toadd.getId())return true;
			if(toadd.getRetweetedTweet()!=-1&&toadd.getRetweetedTweet()==own.getId()) {
				return true;
			}
		}
		return false;
	}
	@XmlTransient
	public String getFiltersColumn() {
		String ret = "";
		for(int i=0; i<getFilterList().size();i++) {
			Filter filter = getFilterList().get(i);
			if(i!=getFilterList().size()-1) {
				ret=ret.concat("( "+filter.getSummary()+" ) AND ");
			}else {
				ret=ret.concat("( "+filter.getSummary()+" )");
			}
		}
		return ret;
	}
	public void refreshUserCredentials(AnnotationConfigApplicationContext springContext) {
		service= springContext.getBean(UserServiceInterface.class);
		this.user=service.findById(user.getIdDB());
	}
	
}
