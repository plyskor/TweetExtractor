/**
 * 
 */
package es.uam.eps.tweetextractor.server.service.impl;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.Transient;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.service.DeleteServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.DeleteServerTaskSei;
import es.uam.eps.tweetextractor.server.Server;

/**
 * @author jgarciadelsaz
 *
 */
@Controller
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.DeleteServerTaskSei",serviceName = "DeleteServerTask")
public class DeleteServerTaskImpl implements DeleteServerTaskSei{
	@Resource
    private WebServiceContext svcCtx;
	@Autowired(required = true)
	@Transient
	ServerTaskServiceInterface stServ;
	@Override
	@WebMethod(action="deleteServerTask")
	public DeleteServerTaskResponse deleteServerTask(@WebParam(name = "id")int id) {
		DeleteServerTaskResponse reply = new DeleteServerTaskResponse();
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	    Server server = (Server) context.getAttribute("Server");
	    if(server==null) {
	    	reply.setError(true);
	    	reply.setMessage("Server instance not found");
	    	return reply;
	    }
	    stServ=server.getSpringContext().getBean(ServerTaskServiceInterface.class);
		if(id>=0) {
			reply.setError(true);
			reply.setMessage("ID is not valid");
			return reply;
		}
	    ServerTask task = server.findById(id);
	    if (task==null) {
	    	reply.setError(true);
	    	reply.setMessage("Task not found for id: "+id);
	    	return reply;
	    }
	    if(task.getStatus()==Constants.ST_RUNNING) {
	    	server.interruptTask(task);
	    }
	    server.deleteTaskFromServer(task);
	    stServ.deleteById(task.getId());
		reply.setError(false);
		reply.setMessage("Task with id: "+id+" has been succesfully deleted");
		return reply;
	}

}
