/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.model.Extraction;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class CreateExtractionTask extends TwitterExtractorFXTask<Integer>{
	private Extraction extraction;
	private Logger logger = LoggerFactory.getLogger(CreateExtractionTask.class);
	/**
	 * @param springContext the context to set
	 * @param extraction the extraction to set
	 */
	public CreateExtractionTask(AnnotationConfigApplicationContext springContext, Extraction extraction) {
		super(springContext);
		this.extraction = extraction;
	}


	@Override
	protected Integer call() throws Exception {
		if(this.extraction==null)return -1;
		ExtractionServiceInterface extractionService = springContext.getBean(ExtractionServiceInterface.class);
		extractionService.persist(extraction);
		logger.info("Extraction with id:"+ extraction.getIdDB()+ " has been succesfully created in database.");
		return 0;
	}

}
