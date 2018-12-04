/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.DeleteServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.DeleteServerTaskSei;
import es.uam.eps.tweetextractor.model.service.sei.GetServerTaskStatusSei;

/**
 * @author jgarciadelsaz
 *
 */
public class DeleteServerTask implements DeleteServerTaskSei {
	private JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	private DeleteServerTaskSei client ;
	
	/**
	 * 
	 */
	public DeleteServerTask() {
		super();
		factory.setServiceClass(GetServerTaskStatusSei.class); 
		factory.setAddress(Constants.DELETE_SERVER_TASK_ENDPOINT);
		client= (DeleteServerTaskSei) factory.create(); 
	}

	@Override
	public DeleteServerTaskResponse deleteServerTask(int id) {
		if(client!=null) {
			return client.deleteServerTask(id);
		}
		return null;
	}

}
