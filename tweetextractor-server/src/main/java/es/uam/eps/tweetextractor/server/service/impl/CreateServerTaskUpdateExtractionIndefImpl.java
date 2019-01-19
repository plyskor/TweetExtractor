/**
 * 
 */
package es.uam.eps.tweetextractor.server.service.impl;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.PersistenceException;
import javax.persistence.Transient;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.servertask.impl.ServerTaskUpdateExtractionIndef;
import es.uam.eps.tweetextractor.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskUpdateExtractionIndefResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskUpdateExtractionIndefSei;
import es.uam.eps.tweetextractor.server.Server;

/**
 * @author jgarciadelsaz
 *
 */
@Controller
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskUpdateExtractionIndefSei",serviceName = "CreateServerTaskUpdateExtractionIndef")
public class CreateServerTaskUpdateExtractionIndefImpl implements CreateServerTaskUpdateExtractionIndefSei {
	@Resource
    private WebServiceContext svcCtx;
	@Transient
	ExtractionServiceInterface eServ;
	@Transient
	ServerTaskServiceInterface stServ;
	public CreateServerTaskUpdateExtractionIndefImpl() {
		super();
	}
	@WebMethod(action="createServerTaskUpdateExtractionIndef")
	@Override
	public CreateServerTaskUpdateExtractionIndefResponse createServerTaskUpdateExtractionIndef(@WebParam(name = "id")int id) {
		CreateServerTaskUpdateExtractionIndefResponse reply = new CreateServerTaskUpdateExtractionIndefResponse();
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	    Server server = (Server) context.getAttribute("Server");
	    if(server==null) {
	    	reply.setError(true);
	    	reply.setMessage("Server instance not found");
	    	return reply;
	    }
	    eServ=server.getSpringContext().getBean(ExtractionServiceInterface.class);
	    stServ=server.getSpringContext().getBean(ServerTaskServiceInterface.class);
		if (id<=0) {
			reply.setError(true);
			reply.setMessage("ID is not valid");
			return reply;
		}
		Extraction e = eServ.findById(id);
		if(e==null) {
			reply.setError(true);
			reply.setMessage("Extraction with id "+id+" does not exist");
		}
	    ServerTaskUpdateExtractionIndef task = new ServerTaskUpdateExtractionIndef(e);
	    try{
	    	stServ.persist(task);
	    	task.setTaskType(TaskTypes.TUEI);
	    }catch(PersistenceException ex ) {
	    	reply.setError(true);
	    	reply.setMessage(ex.getMessage());
	    	return reply;
	    }
	    server.addTaskToServer(task);
	    ServerTaskResponse res=task.call();
	    if(res.isError()) {
	    	reply.setError(false);
		    reply.setMessage("Task has been added to the server with id: "+task.getId()+"\nWARNING: Task is not ready to run");
		    reply.setId(task.getId());
		    return reply;
	    }
	    reply.setError(false);
	    reply.setMessage("Task has been added to the server with id: "+task.getId());
	    reply.setId(task.getId());
		return reply;
	}
	 
}
