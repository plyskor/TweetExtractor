/**
 * 
 */
package es.uam.eps.tweetextractor.dao.inter;




/**
 * @author joseantoniogarciadelsaz
 *
 */
public interface ReferenceAvailableLanguagesDAOInterface<T>{
	public T findByShortCode(String shortcode);	
	
}
