package es.uam.eps.tweetextractorfx.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.model.Extraction;
import es.uam.eps.tweetextractor.model.Tweet;
import es.uam.eps.tweetextractor.model.User;
import es.uam.eps.tweetextractor.model.filter.Filter;
import es.uam.eps.tweetextractor.model.wrapper.TweetListWrapper;
import es.uam.eps.tweetextractor.model.wrapper.UserListWrapper;


public  class XMLManager {
	

	public XMLManager() {
	}

	public static List<User> loadUserList() {
		try {
			List<User> ret = new ArrayList<User>();
			File file = new File(Constants.PERSISTENCE_PATH + ".auth/users.xml");
			if(!file.exists())return null;
			/* Cargamos el contexto XML */
			JAXBContext context = JAXBContext.newInstance(UserListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			UserListWrapper wrapper = (UserListWrapper) um.unmarshal(file);
			ret.clear();
			ret.addAll(wrapper.getUsers());
			return ret;

		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			ErrorDialog.showErrorLoadUsers(e.getMessage());
			return null;
		}
	}
	public static void saveUserList(List<User> userList) throws Exception {
	    try {
	    	/* Si el fichero de usuarios no existe, lo creamos */
	    	File authDir = new File (Constants.AUTH_PATH);
	    	if(!authDir.exists()) {
	    		authDir.mkdirs();
				/*Ocultamos el directorio auth en entornos DOS*/
	    		String OS = System.getProperty("os.name").toLowerCase();
	    		if(OS.indexOf("win") >= 0) {
	            Path path = Paths.get(Constants.AUTH_PATH);
	            Files.setAttribute(path, "dos:hidden", true);
	    		}
	    	}
			File file = new File(Constants.FILE_USERS);
			if(!file.exists())
			file.createNewFile();
	        JAXBContext context = JAXBContext
	                .newInstance(UserListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        UserListWrapper wrapper = new UserListWrapper();
	        wrapper.setUsers(userList);
	        
	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	    } catch (Exception e) { // catches ANY exception
	    	e.printStackTrace();
	       throw(e);
	    }
	}
	private static void saveTweetList(Extraction extraction) throws Exception {
	    try {
	    	/* Creamos ficheros y directorios */
	    	File extractionsDir = new File (Constants.EXTRACTION_DATA_PATH);
	    	if(!extractionsDir.exists()) {
	    		extractionsDir.mkdirs();
				/*Ocultamos el directorio extractionData en entornos DOS*/
	    		String OS = System.getProperty("os.name").toLowerCase();
	    		if(OS.indexOf("win") >= 0) {
	            Path path = Paths.get(Constants.EXTRACTION_DATA_PATH);
	            Files.setAttribute(path, "dos:hidden", true);
	    		}
	    	}
	    	File extractionDir = new File (Constants.EXTRACTION_DATA_PATH+extraction.getId()+"/");
	    	if(!extractionDir.exists()) {
	    		extractionDir.mkdirs();
	    	}
			File file = new File(Constants.EXTRACTION_DATA_PATH+extraction.getId()+"/"+"tweets.xml");
			if(!file.exists())
			file.createNewFile();
	        JAXBContext context = JAXBContext
	                .newInstance(TweetListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        TweetListWrapper wrapper = new TweetListWrapper();
	        wrapper.setTweets(extraction.getTweetList());
	        
	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	    } catch (Exception e) { // catches ANY exception
	    	e.printStackTrace();
	       throw(e);
	    }
	}
	public static void saveTweetListToFile(Extraction extraction, File file) throws Exception {
		try {
			if(!file.exists())
			file.createNewFile();
	        JAXBContext context = JAXBContext
	                .newInstance(TweetListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        TweetListWrapper wrapper = new TweetListWrapper();
	        wrapper.setTweets(extraction.getTweetList());
	        
	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	    } catch (Exception e) { // catches ANY exception
	    	e.printStackTrace();
	       throw(e);
	    }
	}
	public static void saveExtraction(Extraction extraction) throws Exception {
	    try {
	    	/* Creamos ficheros y archivos */
	    	File extractionsDir = new File (Constants.EXTRACTION_DATA_PATH);
	    	if(!extractionsDir.exists()) {
	    		extractionsDir.mkdirs();
				/*Ocultamos el directorio extractionData en entornos DOS*/
	    		String OS = System.getProperty("os.name").toLowerCase();
	    		if(OS.indexOf("win") >= 0) {
	            Path path = Paths.get(Constants.EXTRACTION_DATA_PATH);
	            Files.setAttribute(path, "dos:hidden", true);
	    		}
	    	}
	    	File extractionDir = new File (Constants.EXTRACTION_DATA_PATH+extraction.getId()+"/");
	    	if(!extractionDir.exists()) {
	    		extractionDir.mkdirs();
	    	}
			File file = new File(Constants.EXTRACTION_DATA_PATH+extraction.getId()+"/"+"properties.xml");
			if(!file.exists())
			file.createNewFile();
	        JAXBContext context = JAXBContext
	                .newInstance(Extraction.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        
	        // Marshalling and saving XML to the file.
	        m.marshal(extraction, file);
	        saveTweetList(extraction);
	    } catch (Exception e) { // catches ANY exception
	    	e.printStackTrace();
	       throw(e);
	    }
	}
	public static Extraction loadExtraction(String id) {
		try {
			File file = new File(Constants.EXTRACTION_DATA_PATH + id+"/properties.xml");
			if(!file.exists())return null;
			/* Cargamos el contexto XML */
			JAXBContext context = JAXBContext.newInstance(Extraction.class);
			Unmarshaller um = context.createUnmarshaller();
			// Reading XML from the file and unmarshalling.
			Extraction ret = (Extraction) um.unmarshal(file);
			ret.addTweets(loadTweetList(ret));
			for(Filter filter:ret.getFilterXmlList()) {
				filter.loadXml();
			}
			ret.loadFiltersXml();
			return ret;
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			ErrorDialog.showErrorLoadExtraction(e.getMessage());
			return null;
		}
	}
	public static void deleteExtraction(Extraction extraction) {
		if(extraction!=null) {
			File file = new File(Constants.EXTRACTION_DATA_PATH +extraction.getId());
			if(file.exists()) {
			for(File f: file.listFiles()) {
				f.delete();
			}
			}
		}
	}
	public static List<Tweet> loadTweetList(Extraction extraction) {
		try {
			List<Tweet> ret = new ArrayList<Tweet>();
			File file = new File(Constants.EXTRACTION_DATA_PATH + extraction.getId()+"/tweets.xml");
			if(!file.exists())return null;
			/* Cargamos el contexto XML */
			JAXBContext context = JAXBContext.newInstance(TweetListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			TweetListWrapper wrapper = (TweetListWrapper) um.unmarshal(file);
			ret.clear();
			if(wrapper.getTweets()!=null)ret.addAll(wrapper.getTweets());
			return ret;
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			ErrorDialog.showErrorLoadExtraction(e.getMessage());
			return null;
		}
	}
}
