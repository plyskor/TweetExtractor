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
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;

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
	
}
