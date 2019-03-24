/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.response;

/**
 * @author jose
 *
 */
public class TimelineVolumeReportResponse extends ServerTaskResponse {

	public TimelineVolumeReportResponse() {
		super();
	}

	public TimelineVolumeReportResponse(ServerTaskResponse call) {
		super();
		this.setError(call.isError());
		this.setMessage(call.getMessage());
	}

}
