/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import javax.xml.ws.WebServiceException;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.sei.GetServerStatusSei;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class GetServerStatus extends TweetExtractorCXFService implements GetServerStatusSei {
	private GetServerStatusSei client ;
	/**
	 * @param endpoint the server endpoint to set
	 */
	public GetServerStatus(String endpoint) {
		super(endpoint);
		factory.setServiceClass(GetServerStatusSei.class); 
		factory.setAddress(endpoint+Constants.GET_SERVER_STATUS_ENDPOINT);
		client= (GetServerStatusSei) factory.create(); 
	}
	public boolean getServerStatus() {
		
		if(client!=null) {
			try {
				return client.getServerStatus();
			}catch (WebServiceException e) {
				return false;
			}
		}
		return false;
	}

}
