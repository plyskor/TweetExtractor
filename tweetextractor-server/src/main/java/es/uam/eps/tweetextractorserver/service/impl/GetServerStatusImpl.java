/**
 * 
 */
package es.uam.eps.tweetextractorserver.service.impl;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import es.uam.eps.tweetextractor.model.service.sei.GetServerStatusSei;
import es.uam.eps.tweetextractorserver.TweetExtractorServer;

/**
 * @author Jose Antonio García del Saz
 *
 */
@WebService(endpointInterface = "es.uam.eps.tweetextractor.model.service.sei.GetServerStatusSei",serviceName = "GetServerStatus")
public class GetServerStatusImpl implements GetServerStatusSei{
	@Resource
    private WebServiceContext svcCtx;
	/**
	 * 
	 */
	public GetServerStatusImpl() {
		super();
	}
	@Override
	@WebMethod(action="getServerStatus")
	public boolean getServerStatus() {
		MessageContext msgCtx = svcCtx.getMessageContext();
		ServletContext context = (ServletContext) 
                msgCtx.get(MessageContext.SERVLET_CONTEXT);
	    TweetExtractorServer server = (TweetExtractorServer) context.getAttribute("Server");
	    return (server!=null);
	}

}
