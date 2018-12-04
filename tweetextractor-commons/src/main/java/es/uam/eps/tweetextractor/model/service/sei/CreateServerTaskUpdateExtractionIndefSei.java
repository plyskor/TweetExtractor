/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.CreateServerTaskUpdateExtractionIndefResponse;

/**
 * @author jgarciadelsaz
 *
 */
@WebService
public interface CreateServerTaskUpdateExtractionIndefSei {
	public CreateServerTaskUpdateExtractionIndefResponse createServerTaskUpdateExtractionIndef(int id);
}
