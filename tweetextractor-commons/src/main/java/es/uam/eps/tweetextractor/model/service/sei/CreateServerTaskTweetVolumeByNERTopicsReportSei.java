/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import java.util.List;
import javax.jws.WebService;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTweetVolumeNLPReportResponse;

/**
 * @author jgarciadelsaz
 *
 */
@WebService
public interface CreateServerTaskTweetVolumeByNERTopicsReportSei {
	public CreateServerTaskTweetVolumeNLPReportResponse createServerTaskTweetVolumeByNERTopicsReport(int userId,List<Integer>extractions,int languageID,String preferencesName);
}
