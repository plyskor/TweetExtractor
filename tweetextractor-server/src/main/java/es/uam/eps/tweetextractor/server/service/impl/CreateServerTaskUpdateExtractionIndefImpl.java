/**
 * 
 */
package es.uam.eps.tweetextractor.server.service.impl;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import es.uam.eps.tweetextractor.dao.service.ExtractionService;
import es.uam.eps.tweetextractor.dao.service.ServerTaskService;
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
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskUpdateExtractionIndefSei",serviceName = "CreateServerTaskUpdateExtractionIndef")
public class CreateServerTaskUpdateExtractionIndefImpl implements CreateServerTaskUpdateExtractionIndefSei {
	@Resource
    private WebServiceContext svcCtx;
	public CreateServerTaskUpdateExtractionIndefImpl() {}
	@WebMethod(action="createServerTaskUpdateExtractionIndef")
	@Override
	public CreateServerTaskUpdateExtractionIndefResponse createServerTaskUpdateExtractionIndef(@WebParam(name = "id")int id) {
		CreateServerTaskUpdateExtractionIndefResponse reply = new CreateServerTaskUpdateExtractionIndefResponse();
		ExtractionService eServ = new ExtractionService();
		ServerTaskService stServ = new ServerTaskService();
		if (id<=0) {
			reply.setError(true);
			reply.setMessage("ID NOT VALID");
			return reply;
		}
		Extraction e = eServ.findById(id);
		if(e==null) {
			reply.setError(true);
			reply.setMessage("EXTRACTION WITH ID "+id+" DOES NOT EXIST");
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	    Server server = (Server) context.getAttribute("Server");
	    if(server==null) {
	    	reply.setError(true);
	    	reply.setMessage("SERVER INSTANCE NOT FOUND");
	    }
	    ServerTaskUpdateExtractionIndef task = new ServerTaskUpdateExtractionIndef(e);
	    try{
	    	stServ.persist(task);
	    }catch(PersistenceException ex ) {
	    	reply.setError(true);
	    	reply.setMessage(ex.getMessage());
	    	return reply;
	    }
	    server.addTaskToServer(task);
	    ServerTaskResponse res=task.call();
	    if(res.isError()) {
	    	reply.setError(false);
		    reply.setMessage("TASK HAS BEEN ADDED TO SERVER INSTANCE WITH ID: "+task.getId()+"\nWARNING: TASK IS NOT READY TO RUN");
		    reply.setId(task.getId());
		    return reply;
	    }
	    reply.setError(false);
	    reply.setMessage("TASK HAS BEEN ADDED TO SERVER INSTANCE WITH ID: "+task.getId());
	    reply.setId(task.getId());
		return reply;
	}
	 
}
