/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.dao.service.ExtractionService;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.servertask.impl.ServerTaskUpdateExtractionIndef;
import es.uam.eps.tweetextractor.model.servertask.response.ServerTaskResponse;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value=TaskTypes.Values.TYPE_EXTRACTION_TASK)
public abstract class ExtractionServerTask extends ServerTask{
	protected int extractionId;
	@XmlTransient
	@Transient
	protected Extraction extraction;
	/**
	 * @param extraction
	 */
	public ExtractionServerTask(Extraction e) {
		super();
		this.extractionId=e.getIdDB();
		this.extraction=e;
		this.setUser(e.getUser());
	}
	/**
	 * 
	 */
	public ExtractionServerTask() {
		super();
	}

	/**
	 * @return the extractionId
	 */
	public int getExtractionId() {
		return extractionId;
	}
	/**
	 * @param extractionId the extractionId to set
	 */
	public void setExtractionId(int extractionId) {
		this.extractionId = extractionId;
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
	/*
	 * Thread method, executed when task is running.
	 * 
	 */
	public void run() {
		Logger logger = LoggerFactory.getLogger(ServerTaskUpdateExtractionIndef.class);
		logger.info("Starting execution of task with id: " + this.getId());
		/*Check data model integrity*/
		if (this.extraction == null) {
			logger.error("The task with id: " + this.getId() + " has no extraction and has been interrupted.");
			onInterrupt();
			return;
		}
		/*Check data model integrity*/
		if (this.extraction.getUser() == null || !this.getExtraction().getUser().hasAnyCredentials()) {
			logger.error("The task with id: " + this.getId()
					+ " has no user/credentials associated and has been interrupted.");
			onInterrupt();
			releaseExtraction();
			return;
		}
		try {
			/*Try execution catching any exception*/
			implementation();
		}catch (Exception e) {
			/*In case of exception, try to recover*/
			logger.warn("An exception has been thrown in task with id "+this.getId()+"\n"+e.getMessage()+".\nRetrying execution");
			run();
		}
		/*If task finished without interruptions*/
		if(this.getStatus()==Constants.ST_RUNNING) {
			finish();
			releaseExtraction();
		}
		return;
	}
	public void releaseExtraction() {
		if(extraction.isExtracting()){
			extraction.setExtracting(false);
			ExtractionService eServ= new ExtractionService();
			eServ.update(extraction);
		}
	}
	public void blockExtraction() {
		if(!extraction.isExtracting()) {
			extraction.setExtracting(true);
			ExtractionService eServ= new ExtractionService();
			eServ.update(extraction);
		}
	}
}
