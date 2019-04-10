/**
 * 
 */
package es.uam.eps.tweetextractor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.analytics.graphics.PlotStrokeConfiguration;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsRepresentableReport;
import javafx.scene.paint.Color;

/**
 * @author Jose Antonio García del Saz
 *
 */
public class TweetExtractorUtils {
  

	/**
	 * 
	 */
	private TweetExtractorUtils() {
		super();
	}

	public static String buildServerAdress(String serverHost, String serverAppName, int port) {
		return "https://" + serverHost + ":" + port + "/" + serverAppName + "/";
	}

	public static byte[] readFileToByteArray(File file) {
		FileInputStream fis = null;
		// Creating a byte array using the length of the file
		// file.length returns long which is cast to int
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();

		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		return bArray;
	}

	public static void writeByteArrayToFile(File file, byte[] byteArray) {
		Logger logger = LoggerFactory.getLogger(TweetExtractorUtils.class);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(byteArray);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
	}
	public static String getKeyFromMap(Map<String, Integer> map, Integer value) {
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
	public static String colorToHex(Color color) {
		String hex1;
	    String hex2;

	    hex1 = Integer.toHexString(color.hashCode()).toUpperCase();

	    switch (hex1.length()) {
	    case 2:
	        hex2 = "000000";
	        break;
	    case 3:
	        hex2 = String.format("00000%s", hex1.substring(0,1));
	        break;
	    case 4:
	        hex2 = String.format("0000%s", hex1.substring(0,2));
	        break;
	    case 5:
	        hex2 = String.format("000%s", hex1.substring(0,3));
	        break;
	    case 6:
	        hex2 = String.format("00%s", hex1.substring(0,4));
	        break;
	    case 7:
	        hex2 = String.format("0%s", hex1.substring(0,5));
	        break;
	    default:
	        hex2 = hex1.substring(0, 6);
	    }
	    return "#"+hex2;
    }
	public static List<PlotStrokeConfiguration> initializePlotStrokeConfiguration(AnalyticsRepresentableReport report,AnalyticsReportImage chart){
		List<PlotStrokeConfiguration> ret = new ArrayList<>();
		if(report==null) {
			return null;
		}
		switch(report.reportType) {
		case TVR:
			PlotStrokeConfiguration nTweets = new PlotStrokeConfiguration(Constants.STROKE_LINE, 0, "#E0FB00", "Tweet Volume", "Number of Tweets", 1.0f, chart);
			ret.add(nTweets);
			break;
		case TR:
			break;
		default:
			break;
		}
		return ret;
	}
}
