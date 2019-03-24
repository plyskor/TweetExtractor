/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.response;

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
