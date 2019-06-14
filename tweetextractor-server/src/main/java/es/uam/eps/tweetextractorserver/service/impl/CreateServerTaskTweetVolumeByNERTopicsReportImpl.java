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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.AnalyticsReportServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERConfigurationServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ReferenceAvailableLanguagesServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.ServerTaskServiceInterface;
import es.uam.eps.tweetextractor.dao.service.inter.UserServiceInterface;
import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfigurationID;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsTweetVolumeByNERTopicsReport;
import es.uam.eps.tweetextractor.model.service.CreateServerTaskTweetVolumeNLPReportResponse;
import es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTweetVolumeByNERTopicsReportSei;
import es.uam.eps.tweetextractorserver.TweetExtractorServer;
import es.uam.eps.tweetextractorserver.model.servertask.impl.ServerTaskTweetVolumeByNERTopicsReport;
import es.uam.eps.tweetextractorserver.model.servertask.response.ServerTaskResponse;

/**
 * @author jgarciadelsaz
 *
 */
@Controller
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.CreateServerTaskTweetVolumeByNERTopicsReportSei", serviceName = "CreateServerTaskTweetVolumeByNERTopicsReport")
public class CreateServerTaskTweetVolumeByNERTopicsReportImpl
		implements CreateServerTaskTweetVolumeByNERTopicsReportSei {
	@Resource
	private WebServiceContext svcCtx;
	@Transient
	private UserServiceInterface uServ;
	@Transient
	private ExtractionServiceInterface eServ;
	@Transient
	private ServerTaskServiceInterface stServ;
	@Transient
	private AnalyticsReportServiceInterface arServ;
	@Transient
	private ReferenceAvailableLanguagesServiceInterface languageServ;
	@Transient
	private TweetExtractorNERConfigurationServiceInterface configServ;

	@WebMethod(action = "createServerTaskTweetVolumeByNERTopicsReport")
	@Override
	public CreateServerTaskTweetVolumeNLPReportResponse createServerTaskTweetVolumeByNERTopicsReport(
			@WebParam(name = "userId") int userId, @WebParam(name = "extractions") List<Integer> extractions,
			@WebParam(name = "languageID") int languageID,
			@WebParam(name = "NERPreferencesName") String preferencesName) {
		CreateServerTaskTweetVolumeNLPReportResponse reply = new CreateServerTaskTweetVolumeNLPReportResponse();
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) msgCtx.get(MessageContext.SERVLET_CONTEXT);
		TweetExtractorServer server = (TweetExtractorServer) context.getAttribute("Server");
		if (server == null) {
			reply.setError(true);
			reply.setMessage("Server instance not found");
			return reply;
		}
		/* DataSources */
		uServ = server.getSpringContext().getBean(UserServiceInterface.class);
		eServ = server.getSpringContext().getBean(ExtractionServiceInterface.class);
		stServ = server.getSpringContext().getBean(ServerTaskServiceInterface.class);
		arServ = server.getSpringContext().getBean(AnalyticsReportServiceInterface.class);
		languageServ = server.getSpringContext().getBean(ReferenceAvailableLanguagesServiceInterface.class);
		configServ = server.getSpringContext().getBean(TweetExtractorNERConfigurationServiceInterface.class);
		if (userId <= 0) {
			reply.setError(true);
			reply.setMessage("User ID is not valid");
			return reply;
		}
		if (StringUtils.isBlank(preferencesName)) {
			reply.setError(true);
			reply.setMessage("Please provide the NER preferences name");
			return reply;
		}
		if (languageID <= 0) {
			reply.setError(true);
			reply.setMessage("Language ID is not valid");
			return reply;
		}
		User u = uServ.findById(userId);
		if (u == null) {
			reply.setError(true);
			reply.setMessage("User does not exist");
			return reply;
		}
		TweetExtractorNERConfigurationID id = new TweetExtractorNERConfigurationID(languageServ.findById(languageID), u,
				preferencesName);
		TweetExtractorNERConfiguration config = configServ.findById(id);
		if (config == null) {
			reply.setError(true);
			reply.setMessage("NER Preferences not found for user: " + userId + ", language: " + languageID
					+ " and name: " + preferencesName);
			return reply;
		}
		ServerTaskTweetVolumeByNERTopicsReport task = new ServerTaskTweetVolumeByNERTopicsReport();
		AnalyticsTweetVolumeByNERTopicsReport report = new AnalyticsTweetVolumeByNERTopicsReport();
		report.setPreferences(config);
		task.setUser(u);
		report.setReportType(AnalyticsReportTypes.TVT);
		report.setUser(u);
		report.setExtractions(eServ.findListById(extractions));
		task.setTaskType(TaskTypes.TTVT);
		try {
			stServ.persist(task);
			arServ.persist(report);
			task.setReport(report);
			stServ.saveOrUpdate(task);
		} catch (PersistenceException ex) {
			reply.setError(true);
			reply.setMessage(ex.getMessage());
			return reply;
		}
		for(TweetExtractorNamedEntity ne : config.getNamedEntities()) {
			AnalyticsReportCategory category = new AnalyticsReportCategory(ne.getName());
			category.setReport(report);
			report.getCategories().add(category);
			arServ.saveOrUpdate(report);
		}
		server.addTaskToServer(task);
		ServerTaskResponse res = task.call();
		if (res.isError()) {
			reply.setError(false);
			reply.setMessage("Task has been added to the server with id: " + task.getId()
					+ "\nWARNING: Task is not ready to run");
			reply.setId(task.getId());
			return reply;
		}
		reply.setError(false);
		reply.setMessage("Task has been added to the server with id: " + task.getId());
		reply.setId(task.getId());
		return reply;
	}

}
