/**
 * 
 */
package es.uam.eps.tweetextractor.model.service.sei;

import java.util.List;

import javax.jws.WebService;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTrendsReportResponse;

/**
 * @author jose
 *
 */
@WebService
public interface CreateServerTaskTrendsReportSei {
	public CreateServerTaskTrendsReportResponse createServerTaskTrendsReport(int userId,AnalyticsReportTypes reportType,int limit,List<Integer>extractions,List<String> filter,int languageID,String stopWordsListName);
}
