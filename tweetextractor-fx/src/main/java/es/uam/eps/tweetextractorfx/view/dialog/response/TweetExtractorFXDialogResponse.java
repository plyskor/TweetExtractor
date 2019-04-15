/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.dialog.response;

/**
 * @author jose
 *
 */
public class TweetExtractorFXDialogResponse {
	private int intValue;
	private String stringValue;
	
	/**
	 * 
	 */
	public TweetExtractorFXDialogResponse() {
		super();
	}
	/**
	 * @param intValue
	 * @param stringValue
	 */
	
	public TweetExtractorFXDialogResponse(int intValue, String stringValue) {
		super();
		this.intValue = intValue;
		this.stringValue = stringValue;
	}
	/**
	 * @return the intValue
	 */
	public int getIntValue() {
		return intValue;
	}
	/**
	 * @param intValue the intValue to set
	 */
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	/**
	 * @return the stringValue
	 */
	public String getStringValue() {
		return stringValue;
	}
	/**
	 * @param stringValue the stringValue to set
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	
}
