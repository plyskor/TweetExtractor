/**
 * 
 */
package es.uam.eps.tweetextractor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
