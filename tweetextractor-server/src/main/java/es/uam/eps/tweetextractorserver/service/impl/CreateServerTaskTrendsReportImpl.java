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
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.TweetServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingHashtagsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingUserMentionsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingUsersReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingWordsReport;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTrendsReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTrendsReportSei;
import es.uam.eps.tweetextractorserver.TweetExtractorServer;
import es.uam.eps.tweetextractorserver.model.servertask.impl.ServerTaskTrendsReport;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;

/**
 * @author jose
 *
 */
@Controller
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTrendsReportSei",serviceName = "CreateServerTaskTrendsReport")
public class CreateServerTaskTrendsReportImpl implements CreateServerTaskTrendsReportSei {
	@Resource
    private WebServiceContext svcCtx;
	@Transient
	private UserServiceInterface uServ;
	@Transient
	private ExtractionServiceInterface eServ;
	@Transient
	private TweetServiceInterface tServ;
	@Transient
	private ServerTaskServiceInterface stServ;
	@Transient
	private AnalyticsReportServiceInterface arServ;
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTrendsReportSei#createServerTaskTrendsReport(int, es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes, java.util.List)
	 */
	@WebMethod(action="createServerTaskTrendsReport")
	@Override
	public CreateServerTaskTrendsReportResponse createServerTaskTrendsReport(@WebParam(name = "userId")int userId,
			@WebParam(name = "reportType")AnalyticsReportTypes reportType,@WebParam(name = "limit")int limit,@WebParam(name = "extractions") List<Integer> extractions,@WebParam(name = "filter")List<String> filter) {
		CreateServerTaskTrendsReportResponse reply = new CreateServerTaskTrendsReportResponse();
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
	    eServ=server.getSpringContext().getBean(ExtractionServiceInterface.class);
	    if (userId<=0) {
			reply.setError(true);
			reply.setMessage("ID is not valid");
			return reply;
		}
	    if(reportType==null||!Constants.TRENDS_REPORT_TYPES.contains(reportType)) {
	    	reply.setError(true);
			reply.setMessage("Report type is not valid");
			return reply;
	    }
		if (limit<=0||limit>10) {
			reply.setError(true);
			reply.setMessage("Limit value must be an integer in [1,10]");
			return reply;
		}
	    User u = uServ.findById(userId);
		if(u==null) {
			reply.setError(true);
			reply.setMessage("User does not exist");
			return reply;
		}
		ServerTaskTrendsReport task = new ServerTaskTrendsReport();
		TrendsReport report = null;
		String word ="";
		switch (reportType) {
			case TRHR:
				report = new TrendingHashtagsReport();
				word ="hashtags";
				break;
			case TRUR:
				report = new TrendingUsersReport();
				word ="users";
				break;
			case TRUMR:
				report = new TrendingUserMentionsReport();
				word ="user mentions";
				break;
			case TRWR:
				report = new TrendingWordsReport();
				word ="words";
				break;
			default:
				break;
		}
		if(report== null) {
			reply.setError(true);
			reply.setMessage("An error has ocurred creating your report.");
			return reply;
		}
		task.setUser(u);
		report.setUser(u);
		report.setN(limit);
		report.setExtractions(eServ.findListById(extractions));
		report.setStringFilterList(filter);
		try{
	    	stServ.persist(task);
	    	task.setTaskType(TaskTypes.TTTR);
	    	arServ.persist(report);
			task.setReport(report);
			stServ.saveOrUpdate(task);
	    }catch(PersistenceException ex ) {
	    	reply.setError(true);
	    	reply.setMessage(ex.getMessage());
	    	return reply;
	    }
		AnalyticsReportCategory category = new AnalyticsReportCategory("Trending "+ word);
		category.setReport(report);
		report.getCategories().add(category);
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
