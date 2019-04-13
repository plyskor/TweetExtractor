/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.response;

import es.uam.eps.tweetextractor.model.Extraction;

/**
 * @author jose
 *
 */
public class CreateExtractionServerTaskSelectExtractionDialogResponse extends TweetExtractorFXDialogResponse {
	private Extraction extraction;

	/**
	 * 
	 */
	public CreateExtractionServerTaskSelectExtractionDialogResponse() {
		super();
	}

	/**
	 * @param extraction the extraction
	 */
	public CreateExtractionServerTaskSelectExtractionDialogResponse(Extraction extraction) {
		super();
		this.extraction = extraction;
	}

	/**
	 * @param intValue the int value to return
	 * @param stringValue the string value to return
	 */
	public CreateExtractionServerTaskSelectExtractionDialogResponse(int intValue, String stringValue) {
		super(intValue, stringValue);
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
