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
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReport;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReportVolumeRegister;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineVolumeReport;
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
import es.uam.eps.tweetextractor.model.servertask.AnalyticsServerTask;
import es.uam.eps.tweetextractor.model.servertask.ExtractionServerTask;
import es.uam.eps.tweetextractor.model.servertask.ServerTask;
import es.uam.eps.tweetextractor.model.servertask.impl.ServerTaskTimelineVolumeReport;
import es.uam.eps.tweetextractor.model.servertask.impl.ServerTaskUpdateExtractionIndef;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("es.uam.eps.tweetextractor.dao"),
		@ComponentScan("es.uam.eps.tweetextractor.dao.service") })
public class TweetExtractorSpringConfig {

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost/te_op00?currentSchema=te_op00&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
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
				TimelineReportVolumeRegister.class, ServerTaskTimelineVolumeReport.class, AnalyticsReportImage.class);
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}
}