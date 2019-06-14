/**
 * 
 */
package es.uam.eps.tweetextractorserver.model.servertask.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import es.uam.eps.tweetextractor.model.Constants.TaskTypes;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERToken;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorTopic;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsTweetVolumeByNamedEntitiesReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.AnalyticsTweetVolumeByNLPReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.AnalyticsTweetVolumeByNLPReportRegisterWrapper;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractorserver.model.servertask.AnalyticsServerTask;

/**
 * @author joseantoniogarciadelsaz
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = TaskTypes.Values.TYPE_TASK_TWEET_VOLUME_BY_NAMED_ENTITY)
@XmlRootElement(name = "serverTaskTweetVolumeByNamedEntitiesReport")
public class ServerTaskTweetVolumeByNamedEntitiesReport extends AnalyticsServerTask {

	@XmlTransient
	@Transient
	private static final long serialVersionUID = 3282147921540620858L;

	/**
	 * 
	 */
	public ServerTaskTweetVolumeByNamedEntitiesReport() {
		super();
		setLogger(LoggerFactory.getLogger(ServerTaskTweetVolumeByNamedEntitiesReport.class));
	}

	/**
	 * @param id
	 * @param user
	 */
	public ServerTaskTweetVolumeByNamedEntitiesReport(int id, User user) {
		super(id, user);
		setLogger(LoggerFactory.getLogger(ServerTaskTweetVolumeByNamedEntitiesReport.class));
	}

	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorserver.model.servertask.ServerTask#implementation()
	 */
	@Override
	public void implementation() throws Exception {
		getLogger().info("Generating tweet volume by named entities report...");
		AnalyticsTweetVolumeByNamedEntitiesReport report = (AnalyticsTweetVolumeByNamedEntitiesReport) getReport();
		report.setExtractions(eServ.findListByReport(report));
		permanentClearReport();
		List<Integer> extractionIDList = new ArrayList<>();
		for (Extraction e : report.getExtractions()) {
			extractionIDList.add(e.getIdDB());
		}
		List<Tweet> tweetList = new ArrayList<>();
		/* Select tweets by language */
		for (Extraction e : report.getExtractions()) {
			e.setTweetList(tServ.findByExtraction(e));
			for (Tweet t : e.getTweetList()) {
				if (report.getPreferences().getIdentifier().getLanguage().getShortName().equals(t.getLang())&&!containsTweet(tweetList,t.getId())) {
						tweetList.add(t);
				}
			}
		}
		if (tweetList.isEmpty()) {
			getLogger().info("These extractions contain no tweets in language "
					+ report.getPreferences().getIdentifier().getLanguage().getLongName());
			arServ.saveOrUpdate(report);
			return;
		}
		for (TweetExtractorNamedEntity ne : report.getPreferences().getNamedEntities()) {
			AnalyticsReportCategory category = report.getCategoryByName(ne.getName());
			AnalyticsTweetVolumeByNLPReportRegisterWrapper register = new AnalyticsTweetVolumeByNLPReportRegisterWrapper();
			register.setCategory(category);
			category.getResult().add(register);
			category.setReport(report);
			AnalyticsTweetVolumeByNLPReportRegister neRegister = new AnalyticsTweetVolumeByNLPReportRegister();
			neRegister.setTopicLabel(ne.getName());
			neRegister.setRegister(register);
			register.getVolumeList().add(neRegister);
			Set<Integer> markedTweets = new HashSet<>();
			for (TweetExtractorTopic topic : ne.getTopics()) {
				for (TweetExtractorNERToken token : topic.getLinkedTokens()) {
					for (String term : token.getTerms()) {
						markedTweets.addAll(tServ.getTweetIDsContainingTermInExtractions(term, extractionIDList));
						if (true)
							break;
					}
				}
			}
			neRegister.setValue(markedTweets.size());
			regServ.saveOrUpdate(register);
		}
		report.setLastUpdatedDate(new Date());
		arServ.update(report);
		getLogger().info("Tweet volume by named entities report saved to database with id:" + report.getId());
		finish();
	}

	public boolean containsTweet(List<Tweet> list, long id) {
		boolean ret = false;
		if (list != null && id > 0) {
			for (Tweet t : list) {
				if (t.getId() == id) {
					ret = true;
				}
			}
		}
		return ret;
	}
}
