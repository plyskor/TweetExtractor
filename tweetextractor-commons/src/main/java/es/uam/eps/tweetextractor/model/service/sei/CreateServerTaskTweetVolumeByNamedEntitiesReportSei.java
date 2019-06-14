/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import java.util.List;

import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.service.CreateServerTaskTweetVolumeNLPReportResponse;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@WebService
public interface CreateServerTaskTweetVolumeByNamedEntitiesReportSei {
	public CreateServerTaskTweetVolumeNLPReportResponse createServerTaskTweetVolumeByNamedEntitiesReport(int userId,List<Integer>extractions,int languageID,String preferencesName);

}
