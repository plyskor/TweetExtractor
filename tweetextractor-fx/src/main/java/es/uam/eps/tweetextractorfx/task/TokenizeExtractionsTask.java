/**
 * 
 */
package es.uam.eps.tweetextractorfx.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERTokenServiceInterface;
import es.uam.eps.tweetextractor.analytics.dao.service.inter.TweetExtractorNERTokenSetServiceInterface;
import es.uam.eps.tweetextractor.analytics.nlp.TweetExtractorNaturalTextProcessor;
import es.uam.eps.tweetextractor.dao.service.inter.ExtractionServiceInterface;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERToken;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.task.status.TokenizeStatus;

/**
 * @author jose
 *
 */
public class TokenizeExtractionsTask extends TwitterExtractorFXTask<TokenizeStatus> {
	private TweetExtractorNERTokenSetServiceInterface tsServ;
	private TweetExtractorNERTokenServiceInterface tokenService;
	private ExtractionServiceInterface eServ;
	private TweetExtractorNERTokenSet tokenSet;
	private Logger logger = LoggerFactory.getLogger(TokenizeExtractionsTask.class);
	public TokenizeExtractionsTask(AnnotationConfigApplicationContext springContext) {
		super(springContext);
		if(springContext!=null) {
			tsServ=this.getSpringContext().getBean(TweetExtractorNERTokenSetServiceInterface.class);
			tokenService=this.getSpringContext().getBean(TweetExtractorNERTokenServiceInterface.class);
			eServ=this.getSpringContext().getBean(ExtractionServiceInterface.class);
		}
	}

	@Override
	protected TokenizeStatus call() throws Exception {
		TokenizeStatus ret = new TokenizeStatus();
		tokenSet.setTokenList(tokenService.findBySet(tokenSet.getIdentifier()));
		TweetExtractorNaturalTextProcessor textProcessor = new TweetExtractorNaturalTextProcessor(this.getSpringContext());
		tokenSet.setExtractions(eServ.findListByNERTokenSet(tokenSet));
		try {
			List<TweetExtractorNERToken> result = textProcessor.tokenize(tokenSet);
			if (result != null && !result.isEmpty()) {
				tokenService.deleteList(tokenSet.getTokenList());
				tokenSet.setTokenList(result);
				tokenService.persistList(tokenSet.getTokenList());
				tokenSet.setLastUpdated(new Date());
				tokenSet.setnTokens(result.size());
				tsServ.saveOrUpdate(tokenSet);
				ret.setException(null);
				ret.setnTokens(tokenSet.getnTokens());
				ret.setStatus(Constants.SUCCESS);
			}
		} catch (Exception e) {
			logger.warn("An exception has been thrown while tokenizing your extractions: "+e.getMessage());
			ret.setStatus(Constants.ERROR);
			ret.setException(e);
			ret.setnTokens(0);
		}
		return ret;
	}

	/**
	 * @return the tokenSet
	 */
	public TweetExtractorNERTokenSet getTokenSet() {
		return tokenSet;
	}

	/**
	 * @param tokenSet the tokenSet to set
	 */
	public void setTokenSet(TweetExtractorNERTokenSet tokenSet) {
		this.tokenSet = tokenSet;
	}

}
