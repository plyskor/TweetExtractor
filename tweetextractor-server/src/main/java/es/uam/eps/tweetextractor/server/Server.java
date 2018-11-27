/**
 * 
 */
package es.uam.eps.tweetextractor.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import es.uam.eps.tweetextractor.server.service.impl.GetServerTaskStatusImpl;
import es.uam.eps.tweetextractor.server.service.sei.GetServerTaskStatus;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class Server {

	/**
	 * 
	 */
	public Server() {
		publishWebServices();
	}
	
	public void publishWebServices(){
		GetServerTaskStatusImpl implementor = new GetServerTaskStatusImpl(); 
		JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean(); 
		svrFactory.setServiceClass(GetServerTaskStatus.class); 
		svrFactory.setAddress("http://localhost:8080/getServerTaskStatus"); 
		svrFactory.setServiceBean(implementor); 
		svrFactory.create();
	}
}
