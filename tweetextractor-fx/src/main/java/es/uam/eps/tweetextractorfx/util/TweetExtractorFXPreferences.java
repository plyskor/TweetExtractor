/**
 * 
 */
package es.uam.eps.tweetextractorfx.util;

import java.util.prefs.Preferences;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class TweetExtractorFXPreferences {
	/**
	 * 
	 */
	private TweetExtractorFXPreferences() {
		super();
	}
	public static void initializePreferences() {
		Preferences prefs = Preferences.userNodeForPackage(es.uam.eps.tweetextractorfx.Main.class);
		if(!isSetPreference(Constants.PREFERENCE_SERVER_ADDRESS)){
			prefs.put(Constants.PREFERENCE_SERVER_HOST,"app.preciapps.com");
			prefs.put(Constants.PREFERENCE_SERVER_PORT,"8080");
			prefs.put(Constants.PREFERENCE_SERVER_NAMEAPP,"tweetextractor-server-1.0.0.0");
			prefs.put(Constants.PREFERENCE_SERVER_ADDRESS,TweetExtractorUtils.buildServerAdress("app.preciapps.com", "tweetextractor-server-1.0.0.0", 8080));
		}
	}
	/**
	 * @param key the preference key
	 * @return true if preference is set
	 */
	public static boolean isSetPreference(String key) {
		Preferences prefs = Preferences.userNodeForPackage(es.uam.eps.tweetextractorfx.Main.class);
		return !prefs.get(key, "NULL").equals("NULL");
	}
	/**
	 * @param key the preference key
	 * @return the preference value
	 */
	public static String getStringPreference(String key){
		Preferences prefs = Preferences.userNodeForPackage(es.uam.eps.tweetextractorfx.Main.class);
		if(isSetPreference(key))return prefs.get(key, "");
		return null;
	}
	/**
	 * @param key the preference key
	 * @return the preference value
	 */
	public static int getIntPreference(String key){
		Preferences prefs = Preferences.userNodeForPackage(es.uam.eps.tweetextractorfx.Main.class);
		return prefs.getInt(key, -1);
	}
	/**
	 * @param key the preference key
	 * @return the preference value
	 */
	public static void setIntPreference(String key,int value){
		Preferences prefs = Preferences.userNodeForPackage(es.uam.eps.tweetextractorfx.Main.class);
		prefs.putInt(key, value);
	}
	/**
	 * @param key the preference key
	 * @return the preference value
	 */
	public static void setStringPreference(String key,String value){
		Preferences prefs = Preferences.userNodeForPackage(es.uam.eps.tweetextractorfx.Main.class);
		prefs.put(key, value);
	}

	
}
