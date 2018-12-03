/**
 * 
 */
package es.uam.eps.tweetextractor.service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.InterruptServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.InterruptServerTaskSei;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class InterruptServerTask implements InterruptServerTaskSei{
	private JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	private InterruptServerTaskSei client ;
	/**
	 * 
	 */
	public InterruptServerTask() {
		factory.setServiceClass(InterruptServerTaskSei.class); 
		factory.setAddress(Constants.INTERRUPT_SERVER_TASK_ENDPOINT);
		client= (InterruptServerTaskSei) factory.create(); 
	}
	@Override
	public InterruptServerTaskResponse interruptServerTask(int id) {
		if(client!=null) {
			InterruptServerTaskResponse reply = client.interruptServerTask(id);
			return reply;
		}
		return null;
	}
	

}
