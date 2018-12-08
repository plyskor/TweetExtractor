/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask.impl;

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
import es.uam.eps.tweetextractor.model.servertask.ExtractionServerTask;
import es.uam.eps.tweetextractor.model.servertask.response.UpdateExtractionIndefResponse;

/**
 * @author Jose Antonio García del Saz
 *
 */
@Entity
@DiscriminatorValue(value=TaskTypes.Values.TYPE_TASK_UPDATE_EXTRACTION_INDEF)
@XmlRootElement(name="serverTaskUpdateExtractionIndef")
public class ServerTaskUpdateExtractionIndef extends ExtractionServerTask {
	
	private boolean trigger=false;


	
	/**
	 * 
	 */
	
	public ServerTaskUpdateExtractionIndef(Extraction e) {
		super(e);
	}
	/**
	 * 
	 */
	public ServerTaskUpdateExtractionIndef() {
		super();
	}
	@Override
	@Transient
	public UpdateExtractionIndefResponse call()  {
		UpdateExtractionIndefResponse ret = new UpdateExtractionIndefResponse();
		if(this.extraction==null) {
			ret.setError(true);
			ret.setMessage("There is no extraction to update.");
			return ret;
		}
		if(this.getStatus()==Constants.ST_RUNNING) {
			ret.setError(true);
			ret.setMessage("Task is currently running.");
			return ret;
		}
		this.setThread(new Thread(this));
		this.getThread().setName("tweetextractor-server:ServerTask-"+this.getId());
		this.getThread().setDaemon(true);
		ret.setError(false);
		ret.setMessage("Task is ready to run.");
		this.goReady();
		if(this.getStatus()!=Constants.ST_READY) {
			ret.setError(true);
			ret.setMessage("Task is not ready to be called.");
			return ret;
		}
		return ret;	
	}
	public void run() {
		Logger logger = LoggerFactory.getLogger(ServerTaskUpdateExtractionIndef.class);
		while(this.trigger==false) {
		    logger.info("I'm here RUNNING with the id:"+this.getId()+"\n");
		    try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				logger.info("The task with id:"+this.getId()+"has been interrupted.");
				this.setStatus(Constants.ST_INTERRUPTED);
				 ServerTaskService stServce = new ServerTaskService();
				stServce.update(this);
				return;
			}
		}
		return;
	}
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
