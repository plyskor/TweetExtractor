/**
 * 
 */
package es.uam.eps.tweetextractor.server.service.sei;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlType;

import es.uam.eps.tweetextractor.model.service.GetServerTaskStatusResponse;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService
public interface GetServerTaskStatus {
	public GetServerTaskStatusResponse getServerTaskStatus(int id);
}
