/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.response;

/**
 * @author jose
 *
 */
public class TopNHashtagsResponse extends ServerTaskResponse {
	public TopNHashtagsResponse() {
		super();
	}

	public TopNHashtagsResponse(ServerTaskResponse call) {
		super();
		this.setError(call.isError());
		this.setMessage(call.getMessage());
	}
}
