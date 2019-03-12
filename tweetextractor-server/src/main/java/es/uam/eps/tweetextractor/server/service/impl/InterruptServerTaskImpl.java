/**
 * 
 */
package es.uam.eps.tweetextractor.server.service.impl;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.service.InterruptServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.InterruptServerTaskSei;
import es.uam.eps.tweetextractor.server.TweetExtractorServer;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.InterruptServerTaskSei",serviceName = "InterruptServerTask")
public class InterruptServerTaskImpl implements InterruptServerTaskSei {
	@Resource
    private WebServiceContext svcCtx;
	public InterruptServerTaskImpl() {
		super();
	}
	@WebMethod(action="interruptServerTask")
	public InterruptServerTaskResponse interruptServerTask(@WebParam(name = "id")int id) {
		InterruptServerTaskResponse ret= new InterruptServerTaskResponse();
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
	    	 if(task.getStatus()!=Constants.ST_RUNNING) {
	    		 ret.setError(true);
	    		 ret.setMessage("Task is not running");
	    		 return ret;
	    	 }
	    	 server.interruptTask(task);
	    	 ret.setError(false);
	    	 ret.setMessage("Task has been interrupted");
	    	 return ret;
	     }
	     
	        
	}
}