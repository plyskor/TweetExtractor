/**
 * 
 */
package es.uam.eps.tweetextractorserver.service.impl;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.LaunchServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.LaunchServerTaskSei;
import es.uam.eps.tweetextractorserver.TweetExtractorServer;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;

/**
 * @author jgarciadelsaz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.LaunchServerTaskSei",serviceName = "LaunchServerTask")
public class LaunchServerTaskImpl implements LaunchServerTaskSei {
	@Resource
    private WebServiceContext svcCtx;
	public LaunchServerTaskImpl() {
		super();
	}
	@WebMethod(action="launchServerTask")
	@Override
	public LaunchServerTaskResponse launchServerTask(@WebParam(name = "id")int id) {
		LaunchServerTaskResponse reply = new LaunchServerTaskResponse();
		if(id<=0) {
			reply.setError(true);
			reply.setMessage(id+" is not a valid task id");
			return reply;
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	     TweetExtractorServer server = (TweetExtractorServer) context.getAttribute("Server");
	     if(server==null) {
	    	 reply.setError(true);
	    	 reply.setMessage("Could not find server instance. Please, restart the server.");
	    	 return reply;
	     }
	     ServerTask task=server.findById(id);
	     if(task==null) {
	    	 reply.setError(true);
	    	 reply.setMessage("Task "+id+" doesn't exist");
	    	 return reply;
	     }
	     if(task.getStatus()!=Constants.ST_READY) {
	    	 reply.setError(true);
	    	 reply.setMessage("Task "+id+" is not ready to run");
	    	 return reply;
	     }
	     ServerTaskResponse response = server.launchServerTask(task);
	     if(response==null)return null;
	     reply.setError(response.isError());
	     reply.setMessage(response.getMessage());
	     reply.setResponse(response);
	     return reply;
	}

}
