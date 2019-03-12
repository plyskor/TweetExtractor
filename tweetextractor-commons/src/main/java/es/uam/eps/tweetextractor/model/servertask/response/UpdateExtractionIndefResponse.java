/**
 * 
 */
package es.uam.eps.tweetextractor.model.servertask.response;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class UpdateExtractionIndefResponse extends ServerTaskResponse {

	/**
	 * 
	 */
	public UpdateExtractionIndefResponse() {
		super();
	}

	public UpdateExtractionIndefResponse(ServerTaskResponse call) {
		super();
		this.setError(call.isError());
		this.setMessage(call.getMessage());
	}
}
