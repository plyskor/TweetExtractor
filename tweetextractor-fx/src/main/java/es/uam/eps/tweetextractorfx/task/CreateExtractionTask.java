/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class CreateExtractionTask extends TwitterExtractorFXTask<Integer>{
	private Extraction extraction;
	/**
	 * 
	 */
	public CreateExtractionTask(Extraction extraction,AnnotationConfigApplicationContext context) {
		super(context);
		this.extraction=extraction;
	}

	@Override
	protected Integer call() throws Exception {
		if(this.extraction==null)return -1;
		ExtractionServiceInterface extractionService = springContext.getBean(ExtractionServiceInterface.class);
		extractionService.persist(extraction);
		return 0;
	}

}
