/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.dao.service.ExtractionService;
import es.uam.eps.tweetextractor.dao.service.ServerTaskService;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.servertask.response.UpdateExtractionIndefResponse;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Entity
@DiscriminatorValue(value=TaskTypes.Values.TYPE_TASK_UPDATE_EXTRACTION_INDEF)
@XmlRootElement(name="serverTaskUpdateExtractionIndef")
public class ServerTaskUpdateExtractionIndef extends ServerTask implements Callable<UpdateExtractionIndefResponse>{
	private int extractionId;
	@XmlTransient
	@Transient
	private Extraction extraction;
	private boolean trigger=false;


	
	/**
	 * 
	 */
	
	public ServerTaskUpdateExtractionIndef(Extraction e) {
		super();
		this.extractionId=e.getIdDB();
		this.extraction=e;
		this.setUser(e.getUser());
	}
	/**
	 * 
	 */
	public ServerTaskUpdateExtractionIndef() {
		super();
	}
	@Override
	@Transient
	public UpdateExtractionIndefResponse call() throws Exception {
		UpdateExtractionIndefResponse ret = new UpdateExtractionIndefResponse();
		if(this.extraction==null) {
			ret.setError(true);
			ret.setMessage("There is no extraction to update.");
			return ret;
		}
		if(this.getStatus()!=Constants.ST_READY) {
			ret.setError(true);
			ret.setMessage("Task is not ready to be called.");
			return ret;
		}
		this.getThread().setName("tweetextractor-server:UpdateExtractionIndef?Id="+this.extraction.getId());
		this.getThread().start();
		this.setStatus(Constants.ST_RUNNING);
		ServerTaskService stServce = new ServerTaskService();
		stServce.update(this);
		ret.setError(false);
		ret.setMessage("Task has started running.");
		return ret;	
	}
	public void run() {
		boolean localTrigger=false;
		while(localTrigger==false) {
			Logger logger = LoggerFactory.getLogger(ServerTaskUpdateExtractionIndef.class);
		    logger.info("I'm here with the id:"+this.getId()+"\n");
		    try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				logger.info("The task with id:"+this.getId()+"has been interrupted.");
				this.setStatus(Constants.ST_INTERRUPTED);
				 ServerTaskService stServce = new ServerTaskService();
				stServce.update(this);
				break;
			}
		}
		return;
	}
	

	/**
	 * @return the extractionId
	 */
	@XmlTransient
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
	@XmlTransient
	@Transient
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
	 * @return the trigger
	 */
	@XmlTransient
	public boolean isTrigger() {
		return trigger;
	}
	/**
	 * @param trigger the trigger to set
	 */
	public void setTrigger(boolean trigger) {
		this.trigger = trigger;
	}
	@Override
	public void initialize() {
		ExtractionService eService= new ExtractionService();
		this.extraction=eService.findById(this.getExtractionId());
	}

}
