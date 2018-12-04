/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskUpdateExtractionIndefResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskUpdateExtractionIndefSei;
import es.uam.eps.tweetextractor.model.service.sei.GetServerTaskStatusSei;

/**
 * @author jgarciadelsaz
 *
 */
public class CreateServerTaskUpdateExtractionIndef implements CreateServerTaskUpdateExtractionIndefSei {
	private JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	private CreateServerTaskUpdateExtractionIndefSei client ;
	
	/**
	 * 
	 */
	public CreateServerTaskUpdateExtractionIndef() {
		super();
		factory.setServiceClass(GetServerTaskStatusSei.class); 
		factory.setAddress(Constants.CREATE_UPDATE_EXTRACTION_INDEF_SERVER_TASK_ENDPOINT);
		client= (CreateServerTaskUpdateExtractionIndefSei) factory.create(); 
	}

	@Override
	public CreateServerTaskUpdateExtractionIndefResponse createServerTaskUpdateExtractionIndef(int id) {
		if(client!=null) {
			return client.createServerTaskUpdateExtractionIndef(id);
		}
		return null;
	}

}
