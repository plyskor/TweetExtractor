/**
 * 
 */
package es.uam.eps.tweetextractorserver.service.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import es.uam.eps.tweetextractor.model.service.GetServerTaskStatusResponse;
import es.uam.eps.tweetextractor.model.service.sei.GetServerTaskStatusSei;
import es.uam.eps.tweetextractorserver.TweetExtractorServer;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTask;
import javax.annotation.Resource;


/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.GetServerTaskStatusSei",serviceName = "GetServerTaskStatus")
public class GetServerTaskStatusImpl implements GetServerTaskStatusSei {
	@Resource
    private WebServiceContext svcCtx;
	public GetServerTaskStatusImpl() {
		super();
	}
	@WebMethod(action="getServerTaskStatus")
	public GetServerTaskStatusResponse getServerTaskStatus(@WebParam(name = "id")int id) {
		GetServerTaskStatusResponse ret= new GetServerTaskStatusResponse();
		if(id<=0) {
			ret.setError(true);
			ret.setMessage("Invalid ID");
			return ret;
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	     TweetExtractorServer server = (TweetExtractorServer) context.getAttribute("Server");
	     if(server==null) {
	    	 ret.setError(true);
	    	 ret.setMessage("Could not find server instance");
	    	 return ret;
	     }
	     ServerTask task=server.findById(id);
	     if(task==null) {
	    	 ret.setError(true);
	    	 ret.setMessage("Task does not exist");
	    	 return ret;
	     }else {
	    	 ret.setError(false);
	    	 ret.setStatus(task.getStatus());
	    	 ret.setMessage("OK");
	    	 return ret;
	     }
	     
	        
	}
}
