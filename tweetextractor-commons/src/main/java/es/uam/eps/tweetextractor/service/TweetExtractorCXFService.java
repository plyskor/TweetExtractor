/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * @author Jose Antonio García del Saz
 *
 */
public abstract class TweetExtractorCXFService {
	protected String endpoint;
	protected JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	/**
	 * @param endpoint the server endpoint to set
	 */
	public TweetExtractorCXFService(String endpoint) {
		this.endpoint=endpoint;
	}
	/**
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}
	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	/**
	 * @return the factory
	 */
	public JaxWsProxyFactoryBean getFactory() {
		return factory;
	}
	/**
	 * @param factory the factory to set
	 */
	public void setFactory(JaxWsProxyFactoryBean factory) {
		this.factory = factory;
	}
	
}
