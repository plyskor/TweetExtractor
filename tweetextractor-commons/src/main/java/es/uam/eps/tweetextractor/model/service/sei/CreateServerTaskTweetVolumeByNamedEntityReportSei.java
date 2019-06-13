/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import java.util.List;
import javax.jws.WebService;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTweetVolumeByNamedEntityReportResponse;

/**
 * @author jgarciadelsaz
 *
 */
@WebService
public interface CreateServerTaskTweetVolumeByNamedEntityReportSei {
	public CreateServerTaskTweetVolumeByNamedEntityReportResponse createServerTaskTweetVolumeByNamedEntityReport(int userId,List<Integer>extractions,int languageID,String preferencesName);
}
