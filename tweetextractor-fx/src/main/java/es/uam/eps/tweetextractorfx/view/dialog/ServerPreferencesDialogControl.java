/**
 *
 */
package es.uam.eps.tweetextractorfx.view.dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.service.GetServerStatus;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author jgarciadelsaz
 *
 */
public class ServerPreferencesDialogControl extends TweetExtractorFXDialogController {
    @FXML
    private TextField serverHost;
    @FXML
    private TextField serverPort;
    @FXML
    private TextField serverAppName;
	/**
	 *
	 */
	public ServerPreferencesDialogControl() {
		super();
	}

	/**
	 * @param mainApplication the mainApplication to set
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
			super.setMainApplication(mainApplication);
			if(TweetExtractorFXPreferences.isSetPreference(Constants.PREFERENCE_SERVER_ADDRESS)) {
			serverHost.setText(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_HOST));
			serverPort.setText(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_PORT));
			serverAppName.setText(TweetExtractorFXPreferences.getStringPreference(Constants.PREFERENCE_SERVER_NAMEAPP));
		}
	}
	/**
	 * @return the serverHost
	 */
	public TextField getServerHost() {
		return serverHost;
	}
	/**
	 * @param serverHost the serverHost to set
	 */
	public void setServerHost(TextField serverHost) {
		this.serverHost = serverHost;
	}
	/**
	 * @return the serverPort
	 */
	public TextField getServerPort() {
		return serverPort;
	}
	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(TextField serverPort) {
		this.serverPort = serverPort;
	}
	/**
	 * @return the serverAppName
	 */
	public TextField getServerAppName() {
		return serverAppName;
	}
	/**
	 * @param serverAppName the serverAppName to set
	 */
	public void setServerAppName(TextField serverAppName) {
		this.serverAppName = serverAppName;
	}
	@FXML
	public void onCancel() {
		if(getDialogStage()!=null) {
			this.getDialogStage().close();
		}
	}
	@FXML
	public void onDefault() {
		getServerHost().setText("app.preciapps.com");
		getServerPort().setText("8080");
		getServerAppName().setText("tweetextractor-server-1.0.1.0");
		
	}
	@FXML
	public void onSave() {
		if(StringUtils.isBlank(serverHost.getText())||serverHost.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerHost();
			
		}
		if(StringUtils.isBlank(serverPort.getText())||serverPort.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerPort();
			
		}
		if(StringUtils.isBlank(serverAppName.getText())||serverAppName.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerAppName();
			
		}
		String patternDomain = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";
		String patternIP= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern pDomain = Pattern.compile(patternDomain);
		Pattern pIP = Pattern.compile(patternIP);
	    Matcher mDomain = pDomain.matcher(serverHost.getText().replace("\r", "").replace("\n", ""));
	    Matcher mIP = pIP.matcher(serverHost.getText().replace("\r", "").replace("\n", ""));
	    if(!mDomain.matches()&&!mIP.matches()) {
	    	ErrorDialog.showErrorServerHostMalformed();
	    	
	    }
		int port=-1;
		try {
			port= Integer.parseInt(serverPort.getText());
			if(port<0||port>65535) {
				ErrorDialog.showErrorServerPortNAN();
				
			}
			TweetExtractorFXPreferences.setStringPreference(Constants.PREFERENCE_SERVER_HOST, serverHost.getText());
			TweetExtractorFXPreferences.setStringPreference(Constants.PREFERENCE_SERVER_PORT, ""+port);
			TweetExtractorFXPreferences.setStringPreference(Constants.PREFERENCE_SERVER_NAMEAPP, serverAppName.getText());
			TweetExtractorFXPreferences.setStringPreference(Constants.PREFERENCE_SERVER_ADDRESS, TweetExtractorUtils.buildServerAdress(serverHost.getText(), serverAppName.getText(), port));
		}catch(NumberFormatException e) {
			ErrorDialog.showErrorServerPortNAN();
			
		}
		this.getDialogStage().close();
	}
	@FXML
	public void onTest() {
		if(StringUtils.isBlank(serverHost.getText())||serverHost.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerHost();
			
		}
		if(StringUtils.isBlank(serverPort.getText())||serverPort.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerPort();
			
		}
		if(StringUtils.isBlank(serverAppName.getText())||serverAppName.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerAppName();
			
		}
		String patternDomain = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";
		String patternIP= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern pDomain = Pattern.compile(patternDomain);
		Pattern pIP = Pattern.compile(patternIP);
	    Matcher mDomain = pDomain.matcher(serverHost.getText().replace("\r", "").replace("\n", ""));
	    Matcher mIP = pIP.matcher(serverHost.getText().replace("\r", "").replace("\n", ""));
	    if(!mDomain.matches()&&!mIP.matches()&&!"localhost".equals(serverHost.getText().replace("\r", "").replace("\n", ""))) {
	    	ErrorDialog.showErrorServerHostMalformed();
	    	
	    }
		int port=-1;
		try {
			port= Integer.parseInt(serverPort.getText());
			if(port<0||port>65535) {
				ErrorDialog.showErrorServerPortNAN();
				
			}
			GetServerStatus service = new GetServerStatus(TweetExtractorUtils.buildServerAdress(serverHost.getText().trim(), serverAppName.getText().trim(), port));
			if(service.getServerStatus()) {
				ErrorDialog.showSucessServerConnectionTest(serverHost.getText());
				
			}else {
				ErrorDialog.showErrorServerConnectionTest();
				
			}
		}catch(NumberFormatException e) {
			ErrorDialog.showErrorServerPortNAN();
			
		}
		
	}
	
}
