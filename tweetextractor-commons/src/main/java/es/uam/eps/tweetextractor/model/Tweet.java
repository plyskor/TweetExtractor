/**
 * 
 */
package es.uam.eps.tweetextractor.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import es.uam.eps.tweetextractor.util.DateAdapter;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

/**
 * Clase para encapsular los datos de la clase twitter4j.Status y guardarlos mejor en XML
 * @author Jose Antonio García del Saz
 *
 */
@Entity
@Table(name="perm_tweet")
public class Tweet implements Serializable {
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -1862882367824622388L;
	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int idDB;
	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Column(name = "last_update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	@Column(name = "favourite_count")
	private int favouriteCount;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "tweet")
	private GeoLocation geoLocation;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_hashtag_list", joinColumns=@JoinColumn(name="tweet"))
	@Column(name="hashtag_list")
	private List<String> hashtagList;
	@Column(name = "id")
	private long id;
	@Column(name = "in_reply_to_screen_name")
	private String inReplyToScreenName;
	@Column(name = "in_reply_to_status_id")
	private long inReplyToStatusId;
	@Column(name = "lang")
	private String lang;
	@ManyToOne(optional=true,cascade=CascadeType.ALL)
	private Tweet quotedTweet;
	@Column(name = "retweet_count")
	private int retweetCount;
	@Column(name = "source")
	private String source;
	@Column(name = "text")
	@Type(type="text")
	private String text;
	@Column(name = "user_screen_name")
	private String userScreenName;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name="perm_user_mention_list", joinColumns=@JoinColumn(name="tweet"))
	@Column(name="user_mention_list")
	private List<String> userMentions;
	@Column(name = "possibly_sensitive")
	private boolean possiblySensitive;
	@Column(name = "favourited")
	private boolean favorited;
	@Column(name = "is_retweet")
	private boolean retweet;
	@Column(name = "is_retweeted")
	private boolean retweeted;
	@Column(name = "retweeted_tweet")
	private long retweetedTweet;
	@ManyToOne
	@XmlTransient
	private Extraction extraction;
	public Tweet() {
		super();
	}

	public Tweet(Status tweet) {
		if (tweet==null)return;
		lastUpdated= new Date();
		if(tweet.getCreatedAt()!=null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createdAt=df.parse(df.format(tweet.getCreatedAt()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		this.favouriteCount=tweet.getFavoriteCount();
		if(tweet.getGeoLocation()!=null)this.geoLocation=new GeoLocation(tweet.getGeoLocation().getLatitude(), tweet.getGeoLocation().getLongitude());
		if(this.getGeoLocation()!=null)this.geoLocation.setTweet(this);
		this.hashtagList= new ArrayList<String>();
		for(HashtagEntity entity : tweet.getHashtagEntities()) {
			hashtagList.add(entity.getText());
		}
		this.id=tweet.getId();
		if((tweet.getInReplyToScreenName())!=null)
		this.inReplyToScreenName= new String (tweet.getInReplyToScreenName());
		this.inReplyToStatusId=tweet.getInReplyToStatusId();
		if(tweet.getLang()!=null)
		this.lang=new String(tweet.getLang());
		if(tweet.getQuotedStatus()!=null) {
			this.quotedTweet= new Tweet (tweet.getQuotedStatus());
			this.quotedTweet.setExtraction(extraction);
		}
		this.retweetCount=tweet.getRetweetCount();
		if (tweet.getSource()!=null)
		this.source=new String (tweet.getSource());
		if((tweet.getText())!=null)
		this.text = new String (tweet.getText());
		if(tweet.getUser().getScreenName()!=null)
		this.userScreenName = new String (tweet.getUser().getScreenName());
		this.userMentions= new ArrayList<String>();
		for(UserMentionEntity entity : tweet.getUserMentionEntities()) {
			this.userMentions.add(entity.getScreenName());
		}
		this.favorited=tweet.isFavorited();
		this.possiblySensitive=tweet.isPossiblySensitive();
		this.retweet=tweet.isRetweet();
		if(tweet.isRetweet()) {
			this.setRetweetedTweet(tweet.getRetweetedStatus().getId());
		}else {
			this.setRetweetedTweet(-1);
		}
		this.retweeted=tweet.isRetweeted();
	}
	
	/**
	 * @return the possiblySensitive
	 */
	public boolean isPossiblySensitive() {
		return possiblySensitive;
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

	/**
	 * @return the extraction
	 */
	@XmlTransient
	public Extraction getExtraction() {
		return extraction;
	}

	/**
	 * @param extraction the extraction to set
	 */
	public void setExtraction(Extraction extraction) {
		this.extraction = extraction;
	}

	/**
	 * @return the retweetedTweet
	 */
	public long getRetweetedTweet() {
		return retweetedTweet;
	}

	/**
	 * @param retweetedTweet the retweetedTweet to set
	 */
	public void setRetweetedTweet(long retweetedTweet) {
		this.retweetedTweet = retweetedTweet;
	}

	/**
	 * @param possiblySensitive the possiblySensitive to set
	 */
	public void setPossiblySensitive(boolean possiblySensitive) {
		this.possiblySensitive = possiblySensitive;
	}

	/**
	 * @return the lastUpdated
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * @return the createdAt
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * @return the favouriteCount
	 */
	public int getFavouriteCount() {
		return favouriteCount;
	}
	/**
	 * @param favouriteCount the favouriteCount to set
	 */
	public void setFavouriteCount(int favouriteCount) {
		this.favouriteCount = favouriteCount;
	}
	/**
	 * @return the geoLocation
	 */
	public GeoLocation getGeoLocation() {
		return geoLocation;
	}
	/**
	 * @param geoLocation the geoLocation to set
	 */
	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	/**
	 * @return the hashtagList
	 */
	@XmlElementWrapper(name = "hashtagList")
    @XmlElement(name = "hashtag")
	public List<String> getHashtagList() {
		return hashtagList;
	}
	/**
	 * @param hashtagList the hashtagList to set
	 */
	public void setHashtagList(List<String> hashtagList) {
		this.hashtagList = hashtagList;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the inReplyToScreenName
	 */
	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}
	/**
	 * @param inReplyToScreenName the inReplyToScreenName to set
	 */
	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}
	/**
	 * @return the inReplyToStatusId
	 */
	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}
	/**
	 * @param inReplyToStatusId the inReplyToStatusId to set
	 */
	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	/**
	 * @return the quotedTweet
	 */
	public Tweet getQuotedTweet() {
		return quotedTweet;
	}
	/**
	 * @param quotedTweet the quotedTweet to set
	 */
	public void setQuotedTweet(Tweet quotedTweet) {
		this.quotedTweet = quotedTweet;
	}
	/**
	 * @return the retweetCount
	 */
	public int getRetweetCount() {
		return retweetCount;
	}
	/**
	 * @param retweetCount the retweetCount to set
	 */
	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the userScreenName
	 */
	public String getUserScreenName() {
		return userScreenName;
	}
	/**
	 * @param userScreenName the userScreenName to set
	 */
	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}
	/**
	 * @return the userMentions
	 */
	@XmlElementWrapper(name = "userMentions")
    @XmlElement(name = "usermention")
	public List<String> getUserMentions() {
		return userMentions;
	}
	/**
	 * @param userMentions the userMentions to set
	 */
	public void setUserMentions(List<String> userMentions) {
		this.userMentions = userMentions;
	}
	/**
	 * @return the favorited
	 */
	public boolean isFavorited() {
		return favorited;
	}
	/**
	 * @param favorited the favorited to set
	 */
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}
	/**
	 * @return the retweet
	 */
	public boolean isRetweet() {
		return retweet;
	}
	/**
	 * @param retweet the retweet to set
	 */
	public void setRetweet(boolean retweet) {
		this.retweet = retweet;
	}
	/**
	 * @return the retweeted
	 */
	public boolean isRetweeted() {
		return retweeted;
	}
	/**
	 * @param retweeted the retweeted to set
	 */
	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}
	
}
