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
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERToken;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorTopic;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsTweetVolumeByNERTopicsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.AnalyticsTweetVolumeByNLPReportRegisterWrapper;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.AnalyticsTweetVolumeByNLPReportRegister;
import es.uam.eps.tweetextractorserver.model.servertask.AnalyticsServerTask;

/**
 * @author jgarciadelsaz
 *
 */
@Controller
@Entity
@DiscriminatorValue(value = TaskTypes.Values.TYPE_TASK_TWEET_VOLUME_BY_NER_TOPICS)
@XmlRootElement(name = "serverTaskTweetVolumeByNERTopicsReport")
public class ServerTaskTweetVolumeByNERTopicsReport extends AnalyticsServerTask {
	@XmlTransient
	@Transient
	private static final long serialVersionUID = -1229771433115959018L;

	public ServerTaskTweetVolumeByNERTopicsReport() {
		super();
		setLogger(LoggerFactory.getLogger(ServerTaskTweetVolumeByNERTopicsReport.class));
	}

	public ServerTaskTweetVolumeByNERTopicsReport(int id, User user) {
		super(id, user);
		setLogger(LoggerFactory.getLogger(ServerTaskTweetVolumeByNERTopicsReport.class));
	}

	@Override
	public void implementation() throws Exception {
		getLogger().info("Generating tweet volume by NER topics report...");
		AnalyticsTweetVolumeByNERTopicsReport report = (AnalyticsTweetVolumeByNERTopicsReport) getReport();
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
			for (TweetExtractorTopic topic : ne.getTopics()) {
				AnalyticsTweetVolumeByNLPReportRegister topicRegister = new AnalyticsTweetVolumeByNLPReportRegister();
				topicRegister.setTopicLabel(topic.getName());
				topicRegister.setRegister(register);
				Set<Integer> markedTweets = new HashSet<>();
				register.getVolumeList().add(topicRegister);
				for (TweetExtractorNERToken token : topic.getLinkedTokens()) {
					for (String term : token.getTerms()) {
						markedTweets.addAll(tServ.getTweetIDsContainingTermInExtractions(term, extractionIDList));
						if (true)
							break;
					}
				}
				topicRegister.setValue(markedTweets.size());
			}
			regServ.saveOrUpdate(register);
		}
		report.setLastUpdatedDate(new Date());
		arServ.update(report);
		getLogger().info("Tweet volume by NER topics report saved to database with id:" + report.getId());
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
