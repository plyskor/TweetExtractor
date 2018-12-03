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
import es.uam.eps.tweetextractor.server.Server;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.InterruptServerTaskSei",serviceName = "InterruptServerTask")
public class InterruptServerTaskImpl implements InterruptServerTaskSei {
	@Resource
    private WebServiceContext svcCtx;
	public InterruptServerTaskImpl() {}
	@WebMethod(action="interruptServerTask")
	public InterruptServerTaskResponse interruptServerTask(@WebParam(name = "id")int id) {
		InterruptServerTaskResponse ret= new InterruptServerTaskResponse();
		if(id<=0) {
			ret.setError(true);
			ret.setMessage("INVALID ID");
			return ret;
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	     Server server = (Server) context.getAttribute("Server");
	     if(server==null) {
	    	 ret.setError(true);
	    	 ret.setMessage("COULD NOT FIND SERVER INSTANCE");
	    	 return ret;
	     }
	     ServerTask task=server.findById(id);
	     if(task==null) {
	    	 ret.setError(true);
	    	 ret.setMessage("TASK DOESNT EXIST");
	    	 return ret;
	     }else {
	    	 if(task.getStatus()!=Constants.ST_RUNNING) {
	    		 ret.setError(true);
	    		 ret.setMessage("TASK IS NOT RUNNING");
	    		 return ret;
	    	 }
	    	 server.interruptTask(task);
	    	 ret.setError(false);
	    	 ret.setMessage("TASK HAS BEEN INTERRUPTED");
	    	 return ret;
	     }
	     
	        
	}
}