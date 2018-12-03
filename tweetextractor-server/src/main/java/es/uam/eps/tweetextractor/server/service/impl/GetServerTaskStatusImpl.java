/**
 * 
 */
package es.uam.eps.tweetextractor.server.service.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.GetServerTaskStatusResponse;
import es.uam.eps.tweetextractor.model.service.sei.GetServerTaskStatusSei;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.GetServerTaskStatusSei",serviceName = "GetServerTaskStatusSei")
public class GetServerTaskStatusImpl implements GetServerTaskStatusSei {

	public GetServerTaskStatusImpl() {}
	@WebMethod(action="getServerTaskStatus")
	public GetServerTaskStatusResponse getServerTaskStatus(@WebParam(name = "id")int id) {
		if(id==1) {
			GetServerTaskStatusResponse ret = new GetServerTaskStatusResponse(Constants.ST_NEW, false, "ST_NEW");
			return ret;
		}else {
			GetServerTaskStatusResponse ret = new GetServerTaskStatusResponse(-1, true, "VALOR DISTINTO DE 1");
			return ret;
		}
	}
}
