/**
 * 
 */
package es.uam.eps.tweetextractorserver.service.impl;

import java.util.List;

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

import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineTopNHashtagsReport;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTopNHashtagsReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTimelineTopNHashtagsReportSei;
import es.uam.eps.tweetextractorserver.TweetExtractorServer;
import es.uam.eps.tweetextractorserver.model.servertask.impl.ServerTaskTopNHashtagsReport;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;

/**
 * @author jose
 *
 */
@Controller
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTimelineTopNHashtagsReportSei",serviceName = "CreateServerTaskTopNHashtagsReport")
public class CreateServerTaskTimelineTopNHashtagsReportImpl implements CreateServerTaskTimelineTopNHashtagsReportSei{
	@Resource
    private WebServiceContext svcCtx;
	@Transient
	private UserServiceInterface uServ;
	@Transient
	private TweetServiceInterface tServ;
	@Transient
	private ServerTaskServiceInterface stServ;
	@Transient
	private AnalyticsReportServiceInterface arServ;
	@WebMethod(action="createServerTaskTopNHashtagsReport")
	@Override
	public CreateServerTaskTopNHashtagsReportResponse createServerTaskTopNHashtagsReport(@WebParam(name = "nHashtags")int nHashtags,@WebParam(name = "userId") int userId,@WebParam(name = "hashtagFilter")List<String> hashtagFilter) {
		CreateServerTaskTopNHashtagsReportResponse reply = new CreateServerTaskTopNHashtagsReportResponse();
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
	    arServ=server.getSpringContext().getBean(AnalyticsReportServiceInterface.class);
	    tServ=server.getSpringContext().getBean(TweetServiceInterface.class);
		if (userId<=0) {
			reply.setError(true);
			reply.setMessage("ID is not valid");
			return reply;
		}
		if (nHashtags<=0||nHashtags>10) {
			reply.setError(true);
			reply.setMessage("Number of Top Hashtags must belong to integers in [1,10]");
			return reply;
		}
	    User u = uServ.findById(userId);
		if(u==null) {
			reply.setError(true);
			reply.setMessage("User does not exist");
			return reply;
		}
		ServerTaskTopNHashtagsReport task = new ServerTaskTopNHashtagsReport();
		TimelineTopNHashtagsReport report = new TimelineTopNHashtagsReport(nHashtags);
		task.setUser(u);
		report.setUser(u);
		try{
	    	stServ.persist(task);
	    	task.setTaskType(TaskTypes.TTNHR);
	    	arServ.persist(report);
			task.setReport(report);
			stServ.saveOrUpdate(task);
	    }catch(PersistenceException ex ) {
	    	reply.setError(true);
	    	reply.setMessage(ex.getMessage());
	    	return reply;
	    }
		List<String> topNHashtags=null;
		topNHashtags = (hashtagFilter!=null&&!hashtagFilter.isEmpty()) ? tServ.findTopNHashtagsFiltered(nHashtags, hashtagFilter):tServ.findTopNHashtags(nHashtags);
		for(String hashtag:topNHashtags) {
			AnalyticsReportCategory category = new AnalyticsReportCategory(hashtag);
			category.setReport(report);
			report.getCategories().add(category);
		}
		arServ.saveOrUpdate(report);
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
