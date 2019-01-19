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

import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.SetServerTaskReadyResponse;
import es.uam.eps.tweetextractor.model.service.sei.SetServerTaskReadySei;
import es.uam.eps.tweetextractor.server.Server;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.SetServerTaskReadySei", serviceName = "SetServerTaskReady")
public class SetServerTaskReadyImpl implements SetServerTaskReadySei {
	@Resource
	private WebServiceContext svcCtx;

	/**
	 * 
	 */
	public SetServerTaskReadyImpl() {
		super();
	}

	@WebMethod(action = "setServerTaskReady")
	@Override
	public SetServerTaskReadyResponse setServerTaskReady(@WebParam(name = "id") int id) {
		SetServerTaskReadyResponse reply = new SetServerTaskReadyResponse();
		if (id <= 0) {
			reply.setError(true);
			reply.setMessage("Invalid id for task: " + id);
			reply.setResponse(null);
			return reply;
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) msgCtx.get(MessageContext.SERVLET_CONTEXT);
		Server server = (Server) context.getAttribute("Server");
		ServerTask task = server.findById(id);
		if (task == null) {
			reply.setError(true);
			reply.setMessage("No task found for id: " + id);
			reply.setResponse(null);
			return reply;
		}
		ServerTaskResponse sResp =task.call();
		if(sResp.isError()) {
			reply.setError(true);
			reply.setMessage(sResp.getMessage());
			reply.setResponse(null);
			return reply;
		}
		reply.setError(false);
		reply.setMessage(sResp.getMessage());
		reply.setResponse(sResp);
		return reply;
	}

}
