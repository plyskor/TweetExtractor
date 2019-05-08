package es.uam.eps.tweetextractor.spring.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import es.uam.eps.tweetextractor.model.Credentials;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.GeoLocation;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.CategoryBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.PieChartConfiguration;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.graphics.TweetExtractorChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.WorldCloudChartConfiguration;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYBarChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.graphics.XYChartGraphicPreferences;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsList;
import es.uam.eps.tweetextractor.model.analytics.nlp.CustomStopWordsListID;
import es.uam.eps.tweetextractor.model.analytics.nlp.StopWord;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERConfiguration;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERToken;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSet;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNERTokenSetID;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorNamedEntity;
import es.uam.eps.tweetextractor.model.analytics.nlp.TweetExtractorTopic;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReport;
import es.uam.eps.tweetextractor.model.analytics.report.TrendsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.AnalyticsReportCategory;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineTopNHashtagsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TimelineVolumeReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingHashtagsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingUserMentionsReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingUsersReport;
import es.uam.eps.tweetextractor.model.analytics.report.impl.TrendingWordsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportCategoryRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.TimelineReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TrendingWordsReportRegister;
import es.uam.eps.tweetextractor.model.filter.Filter;
import es.uam.eps.tweetextractor.model.filter.impl.FilterContains;
import es.uam.eps.tweetextractor.model.filter.impl.FilterContainsExact;
import es.uam.eps.tweetextractor.model.filter.impl.FilterFrom;
import es.uam.eps.tweetextractor.model.filter.impl.FilterHashtag;
import es.uam.eps.tweetextractor.model.filter.impl.FilterList;
import es.uam.eps.tweetextractor.model.filter.impl.FilterMention;
import es.uam.eps.tweetextractor.model.filter.impl.FilterNot;
import es.uam.eps.tweetextractor.model.filter.impl.FilterOr;
import es.uam.eps.tweetextractor.model.filter.impl.FilterSince;
import es.uam.eps.tweetextractor.model.filter.impl.FilterTo;
import es.uam.eps.tweetextractor.model.filter.impl.FilterUntil;
import es.uam.eps.tweetextractor.model.filter.impl.FilterUrl;
import es.uam.eps.tweetextractor.model.reference.AvailableTwitterLanguage;
import es.uam.eps.tweetextractorserver.model.servertask.AnalyticsServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.ExtractionServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.ScheduledServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.ServerTask;
import es.uam.eps.tweetextractorserver.model.servertask.impl.ServerTaskTimelineVolumeReport;
import es.uam.eps.tweetextractorserver.model.servertask.impl.ServerTaskTopNHashtagsReport;
import es.uam.eps.tweetextractorserver.model.servertask.impl.ServerTaskTrendsReport;
import es.uam.eps.tweetextractorserver.model.servertask.impl.ServerTaskUpdateExtractionIndef;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("es.uam.eps.tweetextractor.dao"),
		@ComponentScan("es.uam.eps.tweetextractor.dao.service"),
		@ComponentScan("es.uam.eps.tweetextractor.analytics.dao"),
		@ComponentScan("es.uam.eps.tweetextractor.analytics.dao.service") })
public class TweetExtractorSpringConfig {

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://db.preciapps.com/te_op00?currentSchema=te_op00&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
		dataSource.setUsername("te_op00_update");
		dataSource.setPassword("te_op00_update");
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		Properties props = new Properties();
		props.put("hibernate.show_sql", "false");
		props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.put("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
		props.put("transaction.auto_close_session", "true");
		props.put("hibernate.c3p0.timeout", "20000");
		props.put("hibernate.hbm2ddl.auto", "update");
		factoryBean.setHibernateProperties(props);
		factoryBean.setAnnotatedClasses(User.class, Extraction.class, Tweet.class, GeoLocation.class, ServerTask.class,
				Credentials.class, Filter.class, FilterContains.class, FilterContainsExact.class, FilterFrom.class,
				FilterTo.class, FilterHashtag.class, FilterList.class, FilterMention.class, FilterNot.class,
				FilterOr.class, FilterSince.class, FilterUntil.class, FilterUrl.class, ExtractionServerTask.class,
				AnalyticsServerTask.class, ServerTaskUpdateExtractionIndef.class, AnalyticsReport.class,
				TimelineVolumeReport.class, TimelineReport.class, TimelineReportRegister.class,
				TimelineReportVolumeRegister.class, ServerTaskTimelineVolumeReport.class, AnalyticsReportImage.class,
				ScheduledServerTask.class, AnalyticsReportRegister.class, TweetExtractorChartGraphicPreferences.class,
				XYChartGraphicPreferences.class, PlotStrokeConfiguration.class,
				CategoryBarChartGraphicPreferences.class, XYBarChartGraphicPreferences.class,AnalyticsReportCategory.class,
				AnalyticsReportCategoryRegister.class,ServerTaskTopNHashtagsReport.class,TimelineTopNHashtagsReport.class,
				TrendsReport.class,TrendingHashtagsReport.class,TrendingUsersReport.class,TrendingUserMentionsReport.class,
				TrendingWordsReport.class,ServerTaskTrendsReport.class,TrendingReportRegister.class,PieChartConfiguration.class,
				AvailableTwitterLanguage.class,CustomStopWordsList.class,StopWord.class,CustomStopWordsListID.class,
				TrendingWordsReportRegister.class,WorldCloudChartConfiguration.class,TweetExtractorNamedEntity.class,
				TweetExtractorTopic.class,TweetExtractorNERConfiguration.class,TweetExtractorNERToken.class,TweetExtractorNERTokenSet.class,
				TweetExtractorNERTokenSetID.class);
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}
}