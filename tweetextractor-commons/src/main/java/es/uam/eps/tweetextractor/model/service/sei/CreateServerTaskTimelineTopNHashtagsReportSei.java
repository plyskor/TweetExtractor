/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import java.util.List;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.CreateServerTaskTopNHashtagsReportResponse;

/**
 * @author jose
 *
 */
@WebService
public interface CreateServerTaskTimelineTopNHashtagsReportSei {
	public CreateServerTaskTopNHashtagsReportResponse createServerTaskTopNHashtagsReport(int nHashtags,int userId,List<String> hashtagFilter);
}
