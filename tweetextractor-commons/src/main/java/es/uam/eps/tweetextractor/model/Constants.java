/**
 * 
 */
package es.uam.eps.tweetextractor.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.ImmutableMap;

/**
 * @author Jose Antonio García del Saz
 *
 */

public final class Constants {
	/*
	 * Preference key
	 */
	public final static String PREFERENCE_SERVER_HOST = "PREFERENCE_SERVER_HOST";
	public final static String PREFERENCE_SERVER_PORT = "PREFERENCE_SERVER_PORT";
	public final static String PREFERENCE_SERVER_NAMEAPP = "PREFERENCE_SERVER_NAMEAPP";
	public final static String PREFERENCE_SERVER_ADDRESS = "PREFERENCE_SERVER_ADDRESS";


	
	public enum FilterTypes {
		FC(Values.TYPE_FILTER_CONTAINS), FCE(Values.TYPE_FILTER_CONTAINS_EXACT), FOR(Values.TYPE_FILTER_OR),
		FNOT(Values.TYPE_FILTER_NOT), FHASH(Values.TYPE_FILTER_HASHTAG), FFROM(Values.TYPE_FILTER_FROM),
		FTO(Values.TYPE_FILTER_TO), FLST(Values.TYPE_FILTER_LIST), FMNT(Values.TYPE_FILTER_MENTION),
		FURL(Values.TYPE_FILTER_URL), FSNC(Values.TYPE_FILTER_SINCE), FUNTL(Values.TYPE_FILTER_UNTIL),
		FATT(Values.TYPE_FILTER_ATTITUDE), FQST(Values.TYPE_FILTER_QUESTION);
		private FilterTypes(String type) {
			// if (!this.name().equals(type))
			// throw new IllegalArgumentException("Incorrect use of FilterTypes");
		}

		public static class Values {
			// Available filters
			public final static String TYPE_FILTER_CONTAINS = "FC";
			public final static String TYPE_FILTER_CONTAINS_EXACT = "FCE";
			public final static String TYPE_FILTER_OR = "FOR";
			public final static String TYPE_FILTER_NOT = "FNOT";
			public final static String TYPE_FILTER_HASHTAG = "FHASH";
			public final static String TYPE_FILTER_FROM = "FFROM";
			public final static String TYPE_FILTER_TO = "FTO";
			public final static String TYPE_FILTER_LIST = "FLST";
			public final static String TYPE_FILTER_MENTION = "FMNT";
			public final static String TYPE_FILTER_URL = "FURL";
			public final static String TYPE_FILTER_SINCE = "FSNC";
			public final static String TYPE_FILTER_UNTIL = "FUNTL";
			public final static String TYPE_FILTER_ATTITUDE = "FATT";
			public final static String TYPE_FILTER_QUESTION = "FQST";
		}
	}
	@XmlType(name = "taskType")
	@XmlEnum
	public enum TaskTypes {
		@XmlEnumValue("ET")
		ET(Values.TYPE_EXTRACTION_TASK),
		@XmlEnumValue("TUEI")
		TUEI(Values.TYPE_TASK_UPDATE_EXTRACTION_INDEF);
		private TaskTypes(String type) {
			
		}
		public static class Values {
			// Available filters
			public final static String TYPE_EXTRACTION_TASK = "ET";
			public final static String TYPE_TASK_UPDATE_EXTRACTION_INDEF = "TUEI";
		}
	}

	/*
	 * Generic Strings
	 */
	// Available filters
	public final static String STRING_FILTER_CONTAINS = "Contains";
	public final static String STRING_FILTER_CONTAINS_EXACT = "Contains literally";
	public final static String STRING_FILTER_HASHTAG = "Contains hashtags";
	public final static String STRING_FILTER_FROM = "Tweeted by the account";
	public final static String STRING_FILTER_TO = "In repply to";
	public final static String STRING_FILTER_LIST = "Tweeted by an account from the list";
	public final static String STRING_FILTER_MENTION = "Mentioning users";
	public final static String STRING_FILTER_URL = "With an URL containing";
	public final static String STRING_FILTER_SINCE = "Since date";
	public final static String STRING_FILTER_UNTIL = "Up to date";
	public final static String STRING_FILTER_ATTITUDE = "With an attitude";
	public final static String STRING_FILTER_QUESTION = "Containing one or more questions";
	/*
	 * Classes
	 */
	public final static String CLASS_FILTER_CONTAINS = "es.uam.eps.tweetextractor.model.filter.impl.FilterContains";
	public final static String CLASS_FILTER_CONTAINS_EXACT = "es.uam.eps.tweetextractor.model.filter.impl.FilterContainsExact";
	public final static String CLASS_FILTER_OR = "es.uam.eps.tweetextractor.model.filter.impl.FilterOr";
	public final static String CLASS_FILTER_NOT = "es.uam.eps.tweetextractor.model.filter.impl.FilterNot";
	public final static String CLASS_FILTER_HASHTAG = "es.uam.eps.tweetextractor.model.filter.impl.FilterHashtag";
	public final static String CLASS_FILTER_FROM = "es.uam.eps.tweetextractor.model.filter.impl.FilterFrom";
	public final static String CLASS_FILTER_TO = "es.uam.eps.tweetextractor.model.filter.impl.FilterTo";
	public final static String CLASS_FILTER_LIST = "es.uam.eps.tweetextractor.model.filter.impl.FilterList";
	public final static String CLASS_FILTER_MENTION = "es.uam.eps.tweetextractor.model.filter.impl.FilterMention";
	public final static String CLASS_FILTER_URL = "es.uam.eps.tweetextractor.model.filter.impl.FilterUrl";
	public final static String CLASS_FILTER_SINCE = "es.uam.eps.tweetextractor.model.filter.impl.FilterSince";
	public final static String CLASS_FILTER_UNTIL = "es.uam.eps.tweetextractor.model.filter.impl.FilterUntil";
	public final static String CLASS_FILTER_ATTITUDE = "Con una actitud";
	public final static String CLASS_FILTER_QUESTION = "Contiene una o más preguntas";
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
	public static final String DELETE_SERVER_TASK_ENDPOINT="deleteServerTask";
	public static final String LAUNCH_SERVER_TASK_ENDPOINT="launchServerTask";
	public static final String GET_SERVER_STATUS_ENDPOINT="getServerStatus";
	public static final String GET_USER_SERVER_TASKS_ENDPOINT="getUserServerTasks";
	public static final String SET_SERVER_TASK_READY_ENDPOINT="setServerTaskReady";


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
	public static final ImmutableMap<Integer, String> TASK_STATUS_MAP =
		       new ImmutableMap.Builder<Integer, String>()
		           .put(ST_NEW,"NEW")
		           .put(ST_READY,"READY")
		           .put(ST_RUNNING,"RUNNING")
		           .put(ST_STOPPED,"STOPPED")
		           .put(ST_FINISHED,"FINISHED")
		           .put(ST_INTERRUPTED,"INTERRUPTED")
		           .build();
	/*
	*Types of task available
	**/
    public static final String EXTRACTION_SERVER_TASK_TYPE="Task for extraction";
	public static final String UPDATE_EXTRACTION_INDEF_SERVER_TASK_TYPE = "Update an extraction indefinitely";






	
}
