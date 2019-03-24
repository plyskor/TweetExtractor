/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.response;

/**
 * @author jgarciadelsaz
 *
 */
public class ScheduleTaskOnDateResponse extends ServerTaskResponse {
private int error_code;

	public ScheduleTaskOnDateResponse() {
		super();
	}

	/**
	 * @return the error_code
	 */
	public int getError_code() {
		return error_code;
	}

	/**
	 * @param error_code the error_code to set
	 */
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

}
