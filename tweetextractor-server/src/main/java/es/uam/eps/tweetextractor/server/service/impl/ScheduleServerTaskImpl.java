/**
 * 
 */
package es.uam.eps.tweetextractor.server.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.service.ScheduleServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.sei.ScheduleServerTaskSei;
import es.uam.eps.tweetextractor.server.TweetExtractorServer;

/**
 * @author jose
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.ScheduleServerTaskSei", serviceName = "ScheduleServerTask")
public class ScheduleServerTaskImpl implements ScheduleServerTaskSei {
	@Resource
	private WebServiceContext svcCtx;

	/**
	 * 
	 */
	public ScheduleServerTaskImpl() {
		super();
	}
	@WebMethod(action = "scheduleServerTask")
	@Override
	public ScheduleServerTaskResponse scheduleServerTask(@WebParam(name = "taskId")int taskId, @WebParam(name = "date")Date date) {
		ScheduleServerTaskResponse reply = new ScheduleServerTaskResponse();
		if (taskId <= 0) {
			reply.setError(true);
			reply.setMessage("Invalid id for task: " + taskId);
			reply.setResponse(null);
			return reply;
		}
		if(date==null||date.before(new Date())) {
			reply.setError(true);
			reply.setMessage("Date is not valid, please provide a date from the future");
			reply.setResponse(null);
			return reply;
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) msgCtx.get(MessageContext.SERVLET_CONTEXT);
		TweetExtractorServer server = (TweetExtractorServer) context.getAttribute("Server");
		if(server==null) {
			reply.setError(true);
			reply.setMessage("Server instance is null");
			reply.setResponse(null);
			return reply;
		}
		int result=server.scheduleTaskOnDate(taskId, date);
		if(result==Constants.ERROR) {
			reply.setError(true);
			reply.setMessage("An error has ocurred, check the server log for more information");
			reply.setResponse(null);
			return reply;
		}
		reply.setError(false);
		return reply;
	}

}
