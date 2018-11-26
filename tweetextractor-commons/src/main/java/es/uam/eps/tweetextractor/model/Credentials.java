package es.uam.eps.tweetextractor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "perm_credentials")
public class Credentials {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "identifier")
	private int idDB;
	@Column(name = "consumer_key", length=60, nullable=false)
	private String consumerKey;
	@Column(name = "consumer_secret", length=60, nullable=false)
	private String consumerSecret;
	@Column(name = "access_token", length=60, nullable=false)
	private String accessToken;
	@Column(name = "access_token_secret", length=60, nullable=false)
	private String accessTokenSecret;
	@Column(name = "account_screen_name", length=30, nullable=false)
	private String accountScreenName;
	@ManyToOne
	@XmlTransient
	private User user;
	public Credentials() {
	}

	/**
	 * @return the idDB
	 */
	public int getIdDB() {
		return idDB;
	}

	/**
	 * @return the user
	 */
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
	 * @param idDB the idDB to set
	 */
	public void setIdDB(int idDB) {
		this.idDB = idDB;
	}

	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @return the accountScreenName
	 */
	@XmlAttribute(name="acoountScreenName")
	public String getAccountScreenName() {
		return accountScreenName;
	}

	/**
	 * @param accountScreenName the accountScreenName to set
	 */
	public void setAccountScreenName(String accountScreenName) {
		this.accountScreenName = accountScreenName;
	}

	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the accessTokenSecret
	 */
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * @param accessTokenSecret the accessTokenSecret to set
	 */
	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public boolean equals(Credentials credentials) {
		if(credentials==null)return false;
		return(accountScreenName.equals(credentials.getAccountScreenName())&&(consumerKey.equals(credentials.getConsumerKey())&&consumerSecret.equals(credentials.getConsumerSecret())&&accessToken.equals(credentials.getAccessToken())&&accessTokenSecret.equals(credentials.getAccessTokenSecret())));
	}

}
