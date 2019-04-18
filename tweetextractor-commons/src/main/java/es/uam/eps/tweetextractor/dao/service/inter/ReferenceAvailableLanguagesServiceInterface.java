/**
 * 
 */
package es.uam.eps.tweetextractor.dao.service.inter;

import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface ReferenceAvailableLanguagesServiceInterface extends GenericServiceInterface<AvailableTwitterLanguage, Integer> {
	public AvailableTwitterLanguage findByShortCode(String shortcode);	
}
