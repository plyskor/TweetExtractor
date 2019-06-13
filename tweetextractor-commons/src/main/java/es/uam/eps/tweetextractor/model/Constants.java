/**
 * 
 */
package es.uam.eps.tweetextractor.model;

import java.awt.Font;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * @author Jose Antonio García del Saz
 *
 */

public final class Constants {
	/*
	 * Preference key
	 */
	public static final String PREFERENCE_SERVER_HOST = "PREFERENCE_SERVER_HOST";
	public static final String PREFERENCE_SERVER_PORT = "PREFERENCE_SERVER_PORT";
	public static final String PREFERENCE_SERVER_NAMEAPP = "PREFERENCE_SERVER_NAMEAPP";
	public static final String PREFERENCE_SERVER_ADDRESS = "PREFERENCE_SERVER_ADDRESS";


	
	public enum FilterTypes {
		FC(Values.TYPE_FILTER_CONTAINS), FCE(Values.TYPE_FILTER_CONTAINS_EXACT), FOR(Values.TYPE_FILTER_OR),
		FNOT(Values.TYPE_FILTER_NOT), FHASH(Values.TYPE_FILTER_HASHTAG), FFROM(Values.TYPE_FILTER_FROM),
		FTO(Values.TYPE_FILTER_TO), FLST(Values.TYPE_FILTER_LIST), FMNT(Values.TYPE_FILTER_MENTION),
		FURL(Values.TYPE_FILTER_URL), FSNC(Values.TYPE_FILTER_SINCE), FUNTL(Values.TYPE_FILTER_UNTIL),
		FATT(Values.TYPE_FILTER_ATTITUDE), FQST(Values.TYPE_FILTER_QUESTION);
		private FilterTypes(String type) {
			
		}

		public static class Values {
			// Available filters
			public static final String TYPE_FILTER_CONTAINS = "FC";
			public static final String TYPE_FILTER_CONTAINS_EXACT = "FCE";
			public static final String TYPE_FILTER_OR = "FOR";
			public static final String TYPE_FILTER_NOT = "FNOT";
			public static final String TYPE_FILTER_HASHTAG = "FHASH";
			public static final String TYPE_FILTER_FROM = "FFROM";
			public static final String TYPE_FILTER_TO = "FTO";
			public static final String TYPE_FILTER_LIST = "FLST";
			public static final String TYPE_FILTER_MENTION = "FMNT";
			public static final String TYPE_FILTER_URL = "FURL";
			public static final String TYPE_FILTER_SINCE = "FSNC";
			public static final String TYPE_FILTER_UNTIL = "FUNTL";
			public static final String TYPE_FILTER_ATTITUDE = "FATT";
			public static final String TYPE_FILTER_QUESTION = "FQST";
		}
	}
	@XmlType(name = "taskType")
	@XmlEnum
	public enum TaskTypes {
		@XmlEnumValue("ET")
		ET(Values.TYPE_EXTRACTION_TASK),
		@XmlEnumValue("AT")
		AT(Values.TYPE_ANALYTICS_TASK),
		@XmlEnumValue("TUEI")
		TUEI(Values.TYPE_TASK_UPDATE_EXTRACTION_INDEF),
		@XmlEnumValue("TTVR")
		TTVR(Values.TYPE_TASK_TIMELINE_VOLUME_REPORT),
		@XmlEnumValue("TTNHR")
		TTNHR(Values.TYPE_TASK_TIMELINE_TOP_N_HASHTAGS_REPORT),
		@XmlEnumValue("TTTR")
		TTTR(Values.TYPE_TASK_TRENDS_REPORT),
		@XmlEnumValue("TVNE")
		TVNE(Values.TYPE_TASK_VOLUME_BY_NAMED_ENTITY),
		@XmlEnumValue("SCHT")
		SCHT(Values.TYPE_SCHEDULED_TASK);
		private TaskTypes(String type) {
			
		}
		public static class Values {
			// Available server tasks
			public static final String TYPE_EXTRACTION_TASK = "ET";
			public static final String TYPE_ANALYTICS_TASK = "AT";
			public static final String TYPE_TASK_UPDATE_EXTRACTION_INDEF = "TUEI";
			public static final String TYPE_TASK_TIMELINE_VOLUME_REPORT = "TTVR";
			public static final String TYPE_SCHEDULED_TASK="SCHT";
			public static final String TYPE_TASK_TIMELINE_TOP_N_HASHTAGS_REPORT="TTNHR";
			public static final String TYPE_TASK_TRENDS_REPORT="TTTR";
			public static final String TYPE_TASK_VOLUME_BY_NAMED_ENTITY="TVNE";

		}
	}
	@XmlType(name = "analyticsReportType")
	@XmlEnum
	public enum AnalyticsReportTypes {
		@XmlEnumValue("TR")
		TR(Values.TYPE_TIMELINE_REPORT),
		@XmlEnumValue("TVR")
		TVR(Values.TYPE_TIMELINE_VOLUME_REPORT),
		@XmlEnumValue("TTNHR")
		TTNHR(Values.TYPE_TIMELINE_TOP_N_HASHTAGS_REPORT),
		@XmlEnumValue("TRHR")
		TRHR(Values.TYPE_TRENDING_HASHTAGS_REPORT),
		@XmlEnumValue("TRUR")
		TRUR(Values.TYPE_TRENDING_USERS_REPORT),
		@XmlEnumValue("TRUMR")
		TRUMR(Values.TYPE_TRENDING_USERS_MENTION_REPORT),
		@XmlEnumValue("TVNE")
		TVNE(Values.TYPE_TWEET_VOLUME_BY_NAMED_ENTITIES),
		@XmlEnumValue("TRWR")
		TRWR(Values.TYPE_TRENDING_WORDS_REPORT);
		private AnalyticsReportTypes(String type) {
			
		}
		public static class Values {
			// Available server tasks
			public static final String TYPE_TIMELINE_REPORT = "TR";
			public static final String TYPE_TIMELINE_VOLUME_REPORT = "TVR";
			public static final String TYPE_TIMELINE_TOP_N_HASHTAGS_REPORT = "TTNHR";
			public static final String TYPE_TRENDING_HASHTAGS_REPORT = "TRHR";
			public static final String TYPE_TRENDING_USERS_REPORT = "TRUR";
			public static final String TYPE_TRENDING_USERS_MENTION_REPORT = "TRUMR";
			public static final String TYPE_TRENDING_WORDS_REPORT = "TRWR";
			public static final String TYPE_TWEET_VOLUME_BY_NAMED_ENTITIES = "TVNE";

		}
	}
	public static final ImmutableList<AnalyticsReportTypes> TRENDS_REPORT_TYPES= ImmutableList.of(AnalyticsReportTypes.TRHR,AnalyticsReportTypes.TRUR,AnalyticsReportTypes.TRUMR,AnalyticsReportTypes.TRWR);

	@XmlType(name = "chartType")
	@XmlEnum
	public enum AnalyticsReportImageTypes {
		@XmlEnumValue("TSC")
		TSC(Values.TYPE_TIME_SERIES_CHART),
		@XmlEnumValue("BARC")
		BARC(Values.TYPE_BAR_CHART),
		@XmlEnumValue("PCH")
		PCH(Values.TYPE_PIE_CHART),
		@XmlEnumValue("P3DCH")
		P3DCH(Values.TYPE_3D_PIE_CHART),
		@XmlEnumValue("WCC")
		WCC(Values.TYPE_WORD_CLOUD_CHART),
		@XmlEnumValue("BXYC")
		BXYC(Values.TYPE_XY_BAR_CHART);
		private AnalyticsReportImageTypes(String type) {
			
		}
		public static class Values {
			// Available charts
			public static final String TYPE_TIME_SERIES_CHART = "TSC";
			public static final String TYPE_BAR_CHART = "BARC";
			public static final String TYPE_XY_BAR_CHART = "BXYC";
			public static final String TYPE_PIE_CHART = "PCH";
			public static final String TYPE_3D_PIE_CHART = "P3DCH";
			public static final String TYPE_WORD_CLOUD_CHART = "WCC";
		}
	}
	/*
	 * WordCloud Types  
	 */
	public static final int WCC_PIXEL_BOUNDARY=0;
	public static final int WCC_CIRCULAR=1;
	public static final int WCC_RECTANGULAR=2;
	
	/*
	 * Languages with default stop words set provided by lucene
	 */
	public static final ImmutableList<String> LANGUAGE_AVAILABLE_DEFAULT_STOP_SET= ImmutableList.of("English","Spanish","Arabic","French","Italian","German");

	// Stroke choices
	public static final String STROKE_LINE = "line";
	public static final String STROKE_DASH = "dash";
	public static final String STROKE_DOT = "dot";
	/*Analytics Report <-> Graphic Chart (Compatibility)*/
	public static final ImmutableList<AnalyticsReportTypes> TIME_SERIES_CHART_COMPATIBLE_REPORTS= ImmutableList.of(AnalyticsReportTypes.TVR,AnalyticsReportTypes.TTNHR);
	public static final ImmutableList<AnalyticsReportTypes> XY_BAR_CHART_COMPATIBLE_REPORTS= ImmutableList.of(AnalyticsReportTypes.TVR);
	public static final ImmutableList<AnalyticsReportTypes> CATEGORY_BAR_CHART_COMPATIBLE_REPORTS= ImmutableList.of(AnalyticsReportTypes.TVR,AnalyticsReportTypes.TTNHR,AnalyticsReportTypes.TRHR,AnalyticsReportTypes.TRUR,AnalyticsReportTypes.TRUMR,AnalyticsReportTypes.TRWR);
	public static final ImmutableList<AnalyticsReportTypes> PIE_CHART_COMPATIBLE_REPORTS= ImmutableList.of(AnalyticsReportTypes.TRHR,AnalyticsReportTypes.TRUR,AnalyticsReportTypes.TRUMR,AnalyticsReportTypes.TRWR);
	public static final ImmutableList<AnalyticsReportTypes> PIE_3D_CHART_COMPATIBLE_REPORTS= ImmutableList.of(AnalyticsReportTypes.TRHR,AnalyticsReportTypes.TRUR,AnalyticsReportTypes.TRUMR,AnalyticsReportTypes.TRWR);
	public static final ImmutableList<AnalyticsReportTypes> WORD_CLOUD_CHART_COMPATIBLE_REPORTS= ImmutableList.of(AnalyticsReportTypes.TRWR);

	public static final ImmutableMap<AnalyticsReportImageTypes, ImmutableList<AnalyticsReportTypes>> REPORT_CHART_TYPES_COMPATIBILITY =new ImmutableMap.Builder<AnalyticsReportImageTypes, ImmutableList<AnalyticsReportTypes>>()
			.put(AnalyticsReportImageTypes.TSC,TIME_SERIES_CHART_COMPATIBLE_REPORTS)
			.put(AnalyticsReportImageTypes.BXYC,XY_BAR_CHART_COMPATIBLE_REPORTS)
			.put(AnalyticsReportImageTypes.BARC,CATEGORY_BAR_CHART_COMPATIBLE_REPORTS)
			.put(AnalyticsReportImageTypes.PCH,PIE_CHART_COMPATIBLE_REPORTS)
			.put(AnalyticsReportImageTypes.P3DCH,PIE_3D_CHART_COMPATIBLE_REPORTS)
			.put(AnalyticsReportImageTypes.WCC,WORD_CLOUD_CHART_COMPATIBLE_REPORTS).build();

	/*
	 * Generic Strings
	 */
	// Available filters
	public static final String STRING_FILTER_CONTAINS = "Contains";
	public static final String STRING_FILTER_CONTAINS_EXACT = "Contains literally";
	public static final String STRING_FILTER_HASHTAG = "Contains hashtags";
	public static final String STRING_FILTER_FROM = "Tweeted by the account";
	public static final String STRING_FILTER_TO = "In repply to";
	public static final String STRING_FILTER_LIST = "Tweeted by an account from the list";
	public static final String STRING_FILTER_MENTION = "Mentioning users";
	public static final String STRING_FILTER_URL = "With an URL containing";
	public static final String STRING_FILTER_SINCE = "Since date";
	public static final String STRING_FILTER_UNTIL = "Up to date";
	public static final String STRING_FILTER_ATTITUDE = "With an attitude";
	public static final String STRING_FILTER_QUESTION = "Containing one or more questions";
	/*
	 * Classes
	 */
	public static final String CLASS_FILTER_CONTAINS = "es.uam.eps.tweetextractor.model.filter.impl.FilterContains";
	public static final String CLASS_FILTER_CONTAINS_EXACT = "es.uam.eps.tweetextractor.model.filter.impl.FilterContainsExact";
	public static final String CLASS_FILTER_OR = "es.uam.eps.tweetextractor.model.filter.impl.FilterOr";
	public static final String CLASS_FILTER_NOT = "es.uam.eps.tweetextractor.model.filter.impl.FilterNot";
	public static final String CLASS_FILTER_HASHTAG = "es.uam.eps.tweetextractor.model.filter.impl.FilterHashtag";
	public static final String CLASS_FILTER_FROM = "es.uam.eps.tweetextractor.model.filter.impl.FilterFrom";
	public static final String CLASS_FILTER_TO = "es.uam.eps.tweetextractor.model.filter.impl.FilterTo";
	public static final String CLASS_FILTER_LIST = "es.uam.eps.tweetextractor.model.filter.impl.FilterList";
	public static final String CLASS_FILTER_MENTION = "es.uam.eps.tweetextractor.model.filter.impl.FilterMention";
	public static final String CLASS_FILTER_URL = "es.uam.eps.tweetextractor.model.filter.impl.FilterUrl";
	public static final String CLASS_FILTER_SINCE = "es.uam.eps.tweetextractor.model.filter.impl.FilterSince";
	public static final String CLASS_FILTER_UNTIL = "es.uam.eps.tweetextractor.model.filter.impl.FilterUntil";
	public static final String CLASS_FILTER_ATTITUDE = "Con una actitud";
	public static final String CLASS_FILTER_QUESTION = "Contiene una o más preguntas";
	/*
	 * Paths
	 */
	public static final String PERSISTENCE_PATH = System.getProperty("user.home") + "/TweetExtractorFX/";
	public static final String AUTH_PATH = PERSISTENCE_PATH + ".auth/";
	public static final String EXTRACTION_DATA_PATH = PERSISTENCE_PATH + ".extractionData/";
	/*
	 * Files
	 */
	public static final String FILE_USERS = AUTH_PATH + "users.xml";
	/*
	 * Service endpoints
	 */
	public static final String GET_SERVER_TASK_STATUS_ENDPOINT="getServerTaskStatus";
	public static final String INTERRUPT_SERVER_TASK_ENDPOINT="interruptServerTask";
	public static final String CREATE_UPDATE_EXTRACTION_INDEF_SERVER_TASK_ENDPOINT="createServerTaskUpdateExtractionIndef";
	public static final String CREATE_TIMELINE_VOLUME_REPORT_SERVER_TASK_ENDPOINT="createServerTaskTimelineVolumeReport";
	public static final String CREATE_TRENDS_REPORT_SERVER_TASK_ENDPOINT="createServerTaskTrendsReport";
	public static final String CREATE_TIMELINE_TOP_N_HASHTAGS_REPORT_SERVER_TASK_ENDPOINT="createServerTaskTimelineTopNHashtagsReportImpl";
	public static final String DELETE_SERVER_TASK_ENDPOINT="deleteServerTask";
	public static final String CREATE_SERVER_TASK_TWEET_VOLUME_BY_NAMED_ENTITY_REPORT="createServerTaskTweetVolumeByNamedEntityReport";
	public static final String LAUNCH_SERVER_TASK_ENDPOINT="launchServerTask";
	public static final String GET_SERVER_STATUS_ENDPOINT="getServerStatus";
	public static final String GET_USER_SERVER_TASKS_ENDPOINT="getUserServerTasks";
	public static final String SET_SERVER_TASK_READY_ENDPOINT="setServerTaskReady";
	public static final String SCHEDULE_SERVER_TASK_ENDPOINT="scheduleServerTask";
	public static final double MIN_FREQUNCY_TOKENIZER_RATIO=0;
	public static final int MAX_SCHEDULED_SERVER_TASKS=250;
	public static final int SUCCESS=0;
	public static final int ERROR=-1;
	/*
	 * Limits 
	 */
	public static final int MAX_CHARS_TOPIC_NAME=55;
	public static final int MAX_CHARS_NAMED_ENTITY_NAME=55;
	/*
	 * Login Status
	 */
	public static final int SUCCESS_LOGIN=0;
	public static final int UNKNOWN_LOGIN_ERROR=-1;
	public static final int EMPTY_USER_LOGIN_ERROR=-2;
	public static final int EXIST_USER_LOGIN_ERROR=-3;
	public static final int INCORRECT_PASSWORD_LOGIN_ERROR=-4;
	public static final int EMPTY_PASSWORD_LOGIN_ERROR=-5;
	/*
	 * Register Status
	 */
	public static final int SUCCESS_REGISTER=0;
	public static final int UNKNOWN_REGISTER_ERROR=-1;
	public static final int PASSWORD_MISMATCH_REGISTER_ERROR=-2;
	public static final int UNSAFE_PASSWORD_REGISTER_ERROR=-3;
	public static final int EXIST_USER_REGISTER_ERROR=-4;
	public static final int EMPTY_USER_REGISTER_ERROR=-5;
	/*
	 * Schedule task Status
	 */

	public static final int SCHEDULE_NO_TASK_FOUND=-1;
	public static final int SCHEDULE_NOT_READY=-2;
	public static final int SCHEDULE_DATE_PAST=-3;
	public static final int SCHEDULE_EXCEPTION=-4;
	/*
	 * Update Status
	 */
	public static final int SUCCESS_UPDATE=0;
	public static final int CONNECTION_UPDATE_ERROR=-1;
	public static final int RATE_LIMIT_UPDATE_ERROR=-2;
	public static final int UNKNOWN_UPDATE_ERROR=-3;
	/*
	 * Export Status
	 */
	public static final int SUCCESS_EXPORT=0;
	public static final int UNKNOWN_EXPORT_ERROR=-1;
	/*
	 * Server Task Status
	 */
	public static final int ST_NEW=0;
	public static final int ST_READY=1;
	public static final int ST_RUNNING=2;
	public static final int ST_STOPPED=3;
	public static final int ST_FINISHED=4;
	public static final int ST_INTERRUPTED=5;
	public static final int ST_HALT = 6;
	public static final int ST_SCHEDULED = 7;
	public static final int ST_OUTDATED = 8;
	public static final ImmutableMap<Integer, String> TASK_STATUS_MAP =
		       new ImmutableMap.Builder<Integer, String>()
		           .put(ST_NEW,"NEW")
		           .put(ST_READY,"READY")
		           .put(ST_RUNNING,"RUNNING")
		           .put(ST_STOPPED,"STOPPED")
		           .put(ST_FINISHED,"FINISHED")
		           .put(ST_INTERRUPTED,"INTERRUPTED")
		           .put(ST_HALT,"HALT")
		           .put(ST_SCHEDULED,"SCHEDULED")
		           .put(ST_OUTDATED,"OUTDATED")
		           .build();
	public static final ImmutableMap<String,Integer > FONT_TYPES_MAP =
		       new ImmutableMap.Builder<String, Integer>()
		       .put("Plain", Font.PLAIN)
		       .put("Bold",Font.BOLD)
		       .put("Italic",Font.ITALIC)
		       .build();
	public static final ImmutableMap<String,String > STROKE_TYPE_MAP =
		       new ImmutableMap.Builder<String, String>()
		       .put(Constants.STROKE_LINE,"Line" )
		       .put(Constants.STROKE_DOT,"Dot")
		       .put(Constants.STROKE_DASH,"Dash")
		       .build();
	public static final ImmutableMap<AnalyticsReportImageTypes,String > CHART_TYPES_GUI =
		       new ImmutableMap.Builder<AnalyticsReportImageTypes, String>()
		       .put(AnalyticsReportImageTypes.BARC,"3D Categoory Bar Chart" )
		       .put(AnalyticsReportImageTypes.BXYC,"XY Bar Chart")
		       .put(AnalyticsReportImageTypes.PCH,"Pie Chart")
		       .put(AnalyticsReportImageTypes.P3DCH,"3D Pie Chart")
		       .put(AnalyticsReportImageTypes.TSC,"Time Series Line Chart")
		       .put(AnalyticsReportImageTypes.WCC,"Word Cloud")
		       .build();
	public static final ImmutableMap<AnalyticsReportTypes,String > REPORT_TYPES_GUI =
		       new ImmutableMap.Builder<AnalyticsReportTypes, String>()
		       .put(AnalyticsReportTypes.TVR,"Timeline Tweet Volume Report")
		       .put(AnalyticsReportTypes.TRHR,"Trending Hashtags Reports")
		       .put(AnalyticsReportTypes.TTNHR,"Timeline Top N Hashtags Report")
		       .put(AnalyticsReportTypes.TRUMR,"Trending User Mentions Report")
		       .put(AnalyticsReportTypes.TRUR,"Trending Users Report")
		       .put(AnalyticsReportTypes.TRWR,"Trending Words Report")
		       .build();
	/*
	*Types of task available
	**/
    public static final String EXTRACTION_SERVER_TASK_TYPE="Task for extraction";
    public static final String ANALYTICS_SERVER_TASK_TYPE="Task for analytics";
	public static final String UPDATE_EXTRACTION_INDEF_SERVER_TASK_TYPE = "Update an extraction indefinitely";
	public static final String TIMELINE_REPORT_SERVER_TASK_TYPE = "Create a timeline report based on different criteria.";
	public static final String TRENDS_TIMELINE_REPORT_SERVER_TASK_TYPE = "Create a report based on the trendings in your extractions.";
	public static final String NAMED_ENTITIES_VOLUME_SERVER_TASK_TYPE = "Create a report based on the tweet volume for predefined topics";


	/*
	 * Types of Timeline Reports Criteria 
	 */
	
	public static final String TWEET_VOLUME_TIMELINE_REPORT="TVTR";
	public static final String TOP_N_HASHTAGS_VOLUME_TIMELINE_REPORT="TTNHR";
    public static final String OTHER_TIMELINE_REPORT="OTR";
    /*
	 * Types of scheduling for ServerTasks  
	 */
	public static final int SCHEDULE_GIVEN_DATE_TIME=0;
	public static final int SCHEDULE_DELAY=1;
	public static final int SCHEDULE_PERIODICALLY=2;

	




	
}
