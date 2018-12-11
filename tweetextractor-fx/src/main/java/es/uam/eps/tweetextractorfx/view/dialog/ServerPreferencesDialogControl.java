/**
 *
 */
package es.uam.eps.tweetextractorfx.view.dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.uam.eps.tweetextractor.model.Constants;
import es.uam.eps.tweetextractor.service.GetServerStatus;
import es.uam.eps.tweetextractor.util.TweetExtractorUtils;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.error.ErrorDialog;
import es.uam.eps.tweetextractorfx.util.TweetExtractorFXPreferences;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author jgarciadelsaz
 *
 */
public class ServerPreferencesDialogControl {
	/*Reference to the MainApplication*/
    private MainApplication mainApplication;
    @FXML
    private TextField serverHost;
    @FXML
    private TextField serverPort;
    @FXML
    private TextField serverAppName;
	private Stage dialogStage;
	/**
	 *
	 */
	public ServerPreferencesDialogControl() {
		super();
	}
	/**
	 * @return the mainApplication
	 */
	public MainApplication getMainApplication() {
		return mainApplication;
	}
	/**
	 * @param mainApplication the mainApplication to set
	 */
	public void setMainApplication(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
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
	/**
	 * @return the dialogStage
	 */
	public Stage getDialogStage() {
		return dialogStage;
	}
	/**
	 * @param dialogStage the dialogStage to set
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	@FXML
	public void onCancel() {
		if(getDialogStage()!=null)
		this.getDialogStage().close();
	}
	@FXML
	public void onDefault() {
		getServerHost().setText("app.preciapps.com");
		getServerPort().setText("8080");
		getServerAppName().setText("tweetextractor-server-1.0.0.0");
		return;
	}
	@FXML
	public void onSave() {
		if(serverHost.getText().isEmpty()||serverHost.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerHost();
			return;
		}
		if(serverPort.getText().isEmpty()||serverPort.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerPort();
			return;
		}
		if(serverAppName.getText().isEmpty()||serverAppName.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerAppName();
			return;
		}
		String patternDomain = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";
		String patternIP= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern pDomain = Pattern.compile(patternDomain);
		Pattern pIP = Pattern.compile(patternIP);
	    Matcher mDomain = pDomain.matcher(serverHost.getText().replace("\r", "").replace("\n", ""));
	    Matcher mIP = pIP.matcher(serverHost.getText().replace("\r", "").replace("\n", ""));
	    if(!mDomain.matches()&&!mIP.matches()) {
	    	ErrorDialog.showErrorServerHostMalformed();
	    	return;
	    }
		int port=-1;
		try {
			port= Integer.parseInt(serverPort.getText());
			if(port<0||port>65535) {
				ErrorDialog.showErrorServerPortNAN();
				return;
			}
			TweetExtractorFXPreferences.setStringPreference(Constants.PREFERENCE_SERVER_HOST, serverHost.getText());
			TweetExtractorFXPreferences.setStringPreference(Constants.PREFERENCE_SERVER_PORT, ""+port);
			TweetExtractorFXPreferences.setStringPreference(Constants.PREFERENCE_SERVER_NAMEAPP, serverAppName.getText());
			TweetExtractorFXPreferences.setStringPreference(Constants.PREFERENCE_SERVER_ADDRESS, TweetExtractorUtils.buildServerAdress(serverHost.getText(), serverAppName.getText(), port));
		}catch(NumberFormatException e) {
			ErrorDialog.showErrorServerPortNAN();
			return;
		}
		this.getDialogStage().close();
	}
	@FXML
	public void onTest() {
		if(serverHost.getText().isEmpty()||serverHost.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerHost();
			return;
		}
		if(serverPort.getText().isEmpty()||serverPort.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerPort();
			return;
		}
		if(serverAppName.getText().isEmpty()||serverAppName.getText().trim().equals("")) {
			ErrorDialog.showErrorEmptyServerAppName();
			return;
		}
		String patternDomain = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";
		String patternIP= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		Pattern pDomain = Pattern.compile(patternDomain);
		Pattern pIP = Pattern.compile(patternIP);
	    Matcher mDomain = pDomain.matcher(serverHost.getText().replace("\r", "").replace("\n", ""));
	    Matcher mIP = pIP.matcher(serverHost.getText().replace("\r", "").replace("\n", ""));
	    if(!mDomain.matches()&&!mIP.matches()&&!"localhost".equals(serverHost.getText().replace("\r", "").replace("\n", ""))) {
	    	ErrorDialog.showErrorServerHostMalformed();
	    	return;
	    }
		int port=-1;
		try {
			port= Integer.parseInt(serverPort.getText());
			if(port<0||port>65535) {
				ErrorDialog.showErrorServerPortNAN();
				return;
			}
			GetServerStatus service = new GetServerStatus(TweetExtractorUtils.buildServerAdress(serverHost.getText().trim(), serverAppName.getText().trim(), port));
			if(service.getServerStatus()) {
				ErrorDialog.showSucessServerConnectionTest(serverHost.getText());
				return;
			}else {
				ErrorDialog.showErrorServerConnectionTest();
				return;
			}
		}catch(NumberFormatException e) {
			ErrorDialog.showErrorServerPortNAN();
			return;
		}
		
	}
	
}
