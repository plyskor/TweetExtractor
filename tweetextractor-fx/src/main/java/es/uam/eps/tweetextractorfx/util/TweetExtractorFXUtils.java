/**
 * 
 */
package es.uam.eps.tweetextractorfx.util;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorfx.twitterapi.TwitterExtractor;

/**
 * @author joseantoniogarciadelsaz
 *
 */
public class TweetExtractorFXUtils {
	
	/**
	 * 
	 */
	public TweetExtractorFXUtils() {
		super();
	}

	public static void initializeReferenceTables(Credentials credentials, AnnotationConfigApplicationContext springContext) {
		if(springContext!=null) {
			ReferenceAvailableLanguagesServiceInterface languagesService;
			languagesService = springContext.getBean(ReferenceAvailableLanguagesServiceInterface.class);
			List<AvailableTwitterLanguage> list = languagesService.findAll();
			if(list==null||list.isEmpty()) {
				TwitterExtractor extractor = new TwitterExtractor(credentials, springContext);
				list = extractor.getSupportedLanguages();
				languagesService.persistList(list);
			}
		}
	}
}
