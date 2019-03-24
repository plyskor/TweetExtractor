/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractorfx.util.XMLManager;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class ExportExtractionTask extends TwitterExtractorFXTask<Integer>{
	private Extraction extraction;
	private File file;
	private String message=null;
	private Logger logger = LoggerFactory.getLogger(ExportExtractionTask.class);
	/**
	 * 
	 */
	public ExportExtractionTask(Extraction extraction,File file,AnnotationConfigApplicationContext context) {
		super(context);
		this.extraction=extraction;
		this.file=file;
	}

	@Override
	protected Integer call() throws Exception {
		if (file!=null&&extraction!=null) {
			logger.info("Exporting extraction "+extraction.getIdDB()+" to file "+file.getCanonicalPath()+" ...");
			try {
				XMLManager.saveTweetListToFile(extraction, file);
			} catch (Exception e) {
				this.message=e.getMessage();
				logger.info("Error exporting extraction "+extraction.getIdDB()+" to file "+file.getCanonicalPath()+" => "+e.getMessage());
				return Constants.UNKNOWN_EXPORT_ERROR;
			}
			logger.info("Successfully exported extraction "+extraction.getIdDB()+" to file "+file.getCanonicalPath()+".");
		}
		return Constants.SUCCESS_EXPORT;
	}

	/**
	 * @return the extraction
	 */
	public Extraction getExtraction() {
		return extraction;
	}

	/**
	 * @param extraction the extraction to set
	 */
	public void setExtraction(Extraction extraction) {
		this.extraction = extraction;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the message
	 */
	public String getErrorMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
