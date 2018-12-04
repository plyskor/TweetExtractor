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

import es.uam.eps.tweetextractor.dao.service.ServerTaskService;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.service.DeleteServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.DeleteServerTaskSei;
import es.uam.eps.tweetextractor.server.Server;

/**
 * @author jgarciadelsaz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.DeleteServerTaskSei",serviceName = "DeleteServerTask")
public class DeleteServerTaskImpl implements DeleteServerTaskSei{
	@Resource
    private WebServiceContext svcCtx;
	@Override
	@WebMethod(action="deleteServerTask")
	public DeleteServerTaskResponse deleteServerTask(@WebParam(name = "id")int id) {
		DeleteServerTaskResponse reply = new DeleteServerTaskResponse();
		ServerTaskService stServ = new ServerTaskService();
		if(id>=0) {
			reply.setError(true);
			reply.setMessage("ID IS NOT VALID");
			return reply;
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	    Server server = (Server) context.getAttribute("Server");
	    if(server==null) {
	    	reply.setError(true);
	    	reply.setMessage("SERVER INSTANCE NOT FOUND");
	    	return reply;
	    }
	    ServerTask task = server.findById(id);
	    if (task==null) {
	    	reply.setError(true);
	    	reply.setMessage("TASK NOT FOUND FOR ID: "+id);
	    	return reply;
	    }
	    if(task.getStatus()==Constants.ST_RUNNING) {
	    	server.interruptTask(task);
	    }
	    server.deleteTaskFromServer(task);
	    stServ.delete(task.getId());
		reply.setError(false);
		reply.setMessage("TASK WITH ID "+id+" HAS BEEN DELETED");
		return reply;
	}

}
