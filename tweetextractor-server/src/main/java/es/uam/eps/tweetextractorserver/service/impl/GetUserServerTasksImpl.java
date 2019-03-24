/**
 * 
 */
package es.uam.eps.tweetextractorserver.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import es.uam.eps.tweetextractor.model.service.GetUserServerTasksResponse;
import es.uam.eps.tweetextractor.model.service.sei.GetUserServerTasksSei;
import es.uam.eps.tweetextractorserver.TweetExtractorServer;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTaskInfo;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.GetUserServerTasksSei",serviceName = "GetUserServerTasks")
public class GetUserServerTasksImpl implements GetUserServerTasksSei{
	@Resource
    private WebServiceContext svcCtx;
	/**
	 * 
	 */
	public GetUserServerTasksImpl() {
		super();
	}
	@WebMethod(action="getUserServerTasks")
	@Override
	public GetUserServerTasksResponse getUserServerTasks(@WebParam(name = "id")int id) {
		GetUserServerTasksResponse ret = new GetUserServerTasksResponse();
		if(id<=0) {
			ret.setError(true);
			ret.setMessage("Invalid user id: "+id);
			ret.setServerTasksList(null);
			return ret;
		}
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	    TweetExtractorServer server = (TweetExtractorServer) context.getAttribute("Server");
	    if(server==null) {
	    	ret.setError(true);
			ret.setMessage("Could not find server instance");
			ret.setServerTasksList(null);
			return ret;
	    }
	    List<ServerTaskInfo> userTasks=server.getUserServerTasksInfo(id);
	    if(userTasks.isEmpty()) {
	    	ret.setError(true);
			ret.setMessage("User with id "+id+" does not exist or has no tasks.");
			ret.setServerTasksList(null);
			return ret;
	    }
	    ret.setError(false);
	    ret.setServerTasksList(userTasks);
	    ret.setMessage("Ok");
		return ret;
	}

}
