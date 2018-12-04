/**
 * 
 */
package es.uam.eps.tweetextractor.server.service.impl;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.service.LaunchServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.LaunchServerTaskSei;
import es.uam.eps.tweetextractor.server.Server;

/**
 * @author jgarciadelsaz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.LaunchServerTaskSei",serviceName = "LaunchServerTask")
public class LaunchServerTaskImpl implements LaunchServerTaskSei {
	@Resource
    private WebServiceContext svcCtx;
	public LaunchServerTaskImpl() {}
	@WebMethod(action="launchServerTask")
	@Override
	public LaunchServerTaskResponse launchServerTask(int id) {
		LaunchServerTaskResponse reply = new LaunchServerTaskResponse();
		if(id<=0) {
			reply.setError(true);
			reply.setMessage(id+" IS NOT A VALID ID");
			return reply;
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	     Server server = (Server) context.getAttribute("Server");
	     if(server==null) {
	    	 reply.setError(true);
	    	 reply.setMessage("COULD NOT FIND SERVER INSTANCE");
	    	 return reply;
	     }
	     ServerTask task=server.findById(id);
	     if(task==null) {
	    	 reply.setError(true);
	    	 reply.setMessage("TASK "+id+" DOESNT EXIST");
	    	 return reply;
	     }
	     server.launchServerTask(task);
	     
		return null;
	}

}
