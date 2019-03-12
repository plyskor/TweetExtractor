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

import es.uam.eps.tweetextractor.dao.service.UserService;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineVolumeReport;
import es.uam.eps.tweetextractor.model.servertask.impl.ServerTaskTimelineVolumeReport;
import es.uam.eps.tweetextractor.model.servertask.response.ServerTaskResponse;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTimelineVolumeReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTimelineVolumeReportSei;
import es.uam.eps.tweetextractor.server.TweetExtractorServer;

/**
 * @author jose
 *
 */
@Controller
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTimelineVolumeReportSei",serviceName = "CreateServerTaskTimelineVolumeReport")
public class CreateServerTaskTimelineVolumeReportImpl implements CreateServerTaskTimelineVolumeReportSei{
	@Resource
    private WebServiceContext svcCtx;
	@Transient
	UserServiceInterface uServ;
	@Transient
	ServerTaskServiceInterface stServ;
	public CreateServerTaskTimelineVolumeReportImpl() {
		super();
	}
	@WebMethod(action="createServerTaskUpdateExtractionIndef")
	@Override
	public CreateServerTaskTimelineVolumeReportResponse createServerTaskTimelineVolumeReport(@WebParam(name = "id")int userId) {
		CreateServerTaskTimelineVolumeReportResponse reply = new CreateServerTaskTimelineVolumeReportResponse();
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	    TweetExtractorServer server = (TweetExtractorServer) context.getAttribute("Server");
	    if(server==null) {
	    	reply.setError(true);
	    	reply.setMessage("Server instance not found");
	    	return reply;
	    }
	    stServ=server.getSpringContext().getBean(ServerTaskServiceInterface.class);
	    uServ=server.getSpringContext().getBean(UserServiceInterface.class);
		if (userId<=0) {
			reply.setError(true);
			reply.setMessage("ID is not valid");
			return reply;
		}
	    User u = uServ.findById(userId);
		if(u==null) {
			reply.setError(true);
			reply.setMessage("User does not exist");
			return reply;
		}
		ServerTaskTimelineVolumeReport task = new ServerTaskTimelineVolumeReport();
		task.setUser(u);
		TimelineVolumeReport report = new TimelineVolumeReport();
		task.setReport(report);
		try{
	    	stServ.persist(task);
	    	task.setTaskType(TaskTypes.TTVR);
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
