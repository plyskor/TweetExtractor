/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.CreateServerTaskTimelineVolumeReportResponse;

/**
 * @author jose
 *
 */
@WebService
public interface CreateServerTaskTimelineVolumeReportSei {
	public CreateServerTaskTimelineVolumeReportResponse createServerTaskTimelineVolumeReport(int userId);
}
