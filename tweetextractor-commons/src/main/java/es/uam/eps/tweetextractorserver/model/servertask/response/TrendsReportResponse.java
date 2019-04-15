/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.response;

/**
 * @author jose
 *
 */
public class TrendsReportResponse extends ServerTaskResponse {
	public TrendsReportResponse() {
		super();
	}

	public TrendsReportResponse(ServerTaskResponse call) {
		super();
		this.setError(call.isError());
		this.setMessage(call.getMessage());
	}
}
