/**
 * 
 */
package es.uam.eps.tweetextractorfx.error;

import java.util.List;

import es.uam.eps.tweetextractor.model.Constants.AnalyticsReportTypes;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Jose Antonio Garcia del Saz
 *
 */
public class ErrorDialog {
	
	private ErrorDialog() {
		super();
	}

	public static Alert showErrorLoadUsers(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error reading users");
		alert.setContentText("An error ocurred while reading the users:" + message);
		alert.showAndWait();
		return alert;
	}

	public static Alert showErrorLoginFailed() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Incorrect password");
		alert.setContentText("The password you entered is not correct. Please try again.");
		return alert;
	}

	public static Alert showErrorExistsUSer() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Unexisting user");
		alert.setContentText("There's no account registered with that nickname. Please, try again.");
		return alert;
	}

	public static Alert showErrorPassEmpty() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty password");
		alert.setContentText("Plese, enter the password for this account.");
		return alert;
	}

	public static Alert showErrorUserEmpty() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty username");
		alert.setContentText("Please, enter the username of the account to log in.");
		return alert;
	}

	public static void showErrorSaveUser(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error writing users");
		alert.setContentText("An error has ocurred while saving the new user:\n" + message);
		alert.showAndWait();
		
	}

	public static Alert showSuccessCreateUser() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("User created");
		alert.setContentText("The new user account has been succesfully created.");
		return alert;
	}

	public static Alert showErrorPasswordCheck() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Unsafe password");
		alert.setContentText(
				"Passwords must have between 6 and 16 characters.\nThey also must contain a lower case, an upper case and a number.");
		return alert;
	}

	public static Alert showErrorExistingUser() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("User already exists");
		alert.setContentText(
				"This user account already exists. Please, introduce another name for the account or go to the log in screen.");
		return alert;
	}

	public static Alert showErrorEmptyUser() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Invalid account name");
		alert.setContentText("The user name for the account must have at least 3 characters.");
		return alert;
	}

	public static Alert showErrorPasswordMismatch() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Password mismatch");
		alert.setContentText("The passwords you entered are not equal. Please, try again");
		return alert;
	}

	public static void showErrorSaveCredentials(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error");
		alert.setHeaderText("Error saving credentials");
		alert.setContentText("An error has occured while saving your credentials.\nError:\n" + message);
		alert.showAndWait();
		
	}

	public static void showErrorExistingCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Credentials already exist");
		alert.setContentText(
				"These credentials already belong to this account. Please, choose any other credentials to add.");
		alert.showAndWait();
		
	}

	public static void showErrorEmptyCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty fields");
		alert.setContentText("Please, introduce the tokens for the new credentials.");
		alert.showAndWait();
		
	}

	public static void showErrorEmptyNickname() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty Nickname");
		alert.setContentText("Please, select the user's nickname that you want to use as the origin of the tweets.");
		alert.showAndWait();
		
	}

	public static void showErrorEmptyUrl() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No URL selected");
		alert.setContentText("Please, select the URL parameter for the extraction.");
		alert.showAndWait();
		
	}

	public static void showErrorWrongValues() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Somoe of the fields are not correct");
		alert.setContentText("Remember that Twitter accounts and list names are single words with no spaces.");
		alert.showAndWait();
		
	}

	public static void showErrorEmptyFields() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty fields");
		alert.setContentText("Please, select a Twitter account and a list available on that account.");
		alert.showAndWait();
		
	}

	public static void showErrorSelectDateSince() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No date selected");
		alert.setContentText("Please, select a date from which you would like to extract tweets");
		alert.showAndWait();
		
	}

	public static void showErrorEmptyNicknameTo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No username selected");
		alert.setContentText("Please, select the user's nickname that you want to use as the destiny of the tweets");
		alert.showAndWait();
		
	}

	public static void showErrorSelectDateTo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No date selected");
		alert.setContentText("Please, select the date until which you would like to extract tweets");
		alert.showAndWait();
		
	}

	public static void showErrorEmptyNicknameFrom() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No username selected");
		alert.setContentText("Please, select the user's nickname that you want to use as the origin of the tweets.");
		alert.showAndWait();
		
	}

	public static void showErrorSelectFilterRemove() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No filter selected");
		alert.setContentText("Please, select a filter from the list on the right in order to delete it.");
		alert.showAndWait();
		
	}

	public static void showErrorNotEnoughFilters() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Logic operations");
		alert.setContentText("Please, select at least two filters from the right to apply the OR application to them.");
		alert.showAndWait();
		
	}

	public static void showErrorNotEnoughFiltersNot() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Logic opeartions");
		alert.setContentText("Please, select at least one filter from the right to apply the NOT operation.");
		alert.showAndWait();
		
	}

	public static void showErrorUndoLogic() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Logic operations");
		alert.setContentText("Please, select a logic filter (OR or NOT) in order to undo the logic operation.");
		alert.showAndWait();
		
	}

	public static void showErrorSelectFilterAdd() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No filter selected");
		alert.setContentText("Please, select a filter type from the list on the left to add it to the extraction.");
		alert.showAndWait();
		
	}

	public static void showSuccessUpdatePassword() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Password updated");
		alert.setContentText("Your password has been succesfully updated");
		alert.showAndWait();
		
	}

	public static void showErrorNoCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No credentials available");
		alert.setContentText(
				"This user account has no registered credentials for the Twitter API.\nPlease, add some credentials from the home screen.");
		alert.showAndWait();
		
	}

	public static void showErrorNoSelectedCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No selected credentials");
		alert.setContentText("Select some credentials to perform this action.");
		alert.showAndWait();
		
	}

	public static void showErrorLoadExtraction(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Reading error");
		alert.setContentText("An error has ocurred loading an extraction:\n" + message);
		alert.showAndWait();
		
	}

	public static void showErrorNoSelectedExtraction() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No extraction selected");
		alert.setContentText("Select an extraction to perform this action.");
		alert.showAndWait();
		
	}
	public static void showErrorNoSelectedExtractionForFilter() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No extraction selected");
		alert.setContentText("Select at least one extraction to use on your report.");
		alert.showAndWait();
		
	}
	public static Alert showErrorTwitterExecution(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Twitter Error");
		alert.setHeaderText("The Twitter API has thrown the following error:");
		alert.setContentText(message);
		return alert;
	}

	public static void showErrorDB(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error connecting to database");
		alert.setHeaderText("Error connecting to database");
		alert.setContentText("An error has ocurred contacting the database:\n\n" + message + "\n\nPlease try again.");
		alert.showAndWait();
		
	}

	public static Alert showUpdateQueryResults(int added) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Extraction update has finished");
		alert.setContentText("A total of " + added + " new tweet(s) has/have been added to the extraction");
		return alert;
	}

	public static void showErrorEmptyExtraction() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty extraction");
		alert.setContentText("Add at least one filter to the extraction");
		alert.showAndWait();
		
	}

	public static void showErrorNoTweetSelected() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No tweet selected");
		alert.setContentText("Select a tweet from the list above to delete it from the extraction");
		alert.showAndWait();
		
	}

	public static Alert showErrorUnknownLogin() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Unknown log in error");
		alert.setContentText("An unknown error has occurred while logging in. Please, try again.");
		return alert;
	}

	public static Alert showErrorUnknownRegister() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Unknown register error");
		alert.setContentText("An unknown error has occurred while registering the new account. Please, try again.");
		return alert;
	}

	public static Alert showErrorExportTweets(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Unknown exportation error");
		alert.setContentText("An unknown error has occurred while exporting tweets:\n\n"+message+"\n\nPlease, try again.");
		return alert;
	}
	public static Alert showSuccessExport() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Exportation succesful");
		alert.setContentText("The tweets have succesfully been exported to the XML file.");
		return alert;
	}

	public static void showSucessServerConnectionTest(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Connection succesful");
		alert.setContentText("The server "+text+" has a TweetExtractor server instance running.");
		alert.showAndWait();
	}

	public static void showErrorServerConnectionTest() {		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Connection error");
		alert.setContentText("An error has occurred connecting to that server. Please, introduce a server with a TweetExtractor server instance running.");
		alert.showAndWait();	
	}

	public static void showErrorServerPortNAN() {	
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("TCP Port");
		alert.setHeaderText("Incorrect port value");
		alert.setContentText("The port must be a number between 0 and 65535");
		alert.showAndWait();
	}

	public static void showErrorEmptyServerHost() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Empty field");
		alert.setHeaderText("Server host is empty");
		alert.setContentText("Please, introduce the host or IP address of the server containing the TweetExtractor instance.\n\nExample: 'example.org'");		
		alert.showAndWait();
	}

	public static void showErrorEmptyServerPort() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Empty field");
		alert.setHeaderText("Server TCP port is empty");
		alert.setContentText("Please, introduce the port where the TweetExtractor server instance is listening.\n\nExample: '8080'");
		alert.showAndWait();
	}
	public static void showErrorEmptyServerAppName() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Empty field");
		alert.setHeaderText("Server application name is empty.");
		alert.setContentText("Please, introduce the TweetExtractor server instance name.\n\nExample: 'tweetextractor-server-1.0.0.0'");
		alert.showAndWait();
		}

	public static void showErrorServerHostMalformed() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Malformed server host");
		alert.setContentText("Please, introduce a valid server host or IP address.\n\nExample: 'example.org'");		
		alert.showAndWait();
	}

	public static void showErrorSelectTaskType() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No type selected");
		alert.setContentText("Please, select a type of asynchronous task to create in the server");		
		alert.showAndWait();
	}

	public static void showErrorUserHasNoExtraction() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("User has no extractions");
		alert.setContentText("Please, create an extraction from the home screen first.");		
		alert.showAndWait();
	}

	public static void showErrorCreateServerTask(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Error creating server task");
		alert.setContentText("The server has thrown the following error attempting to create the server task:\n\n'"+message+"'");		
		alert.showAndWait();
	}

	public static void showSuccessCreateServerTask(int id) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Server task created");
		alert.setContentText("Server task has been created with id: "+id);		
		alert.showAndWait();
	}

	public static void showErrorRefreshServerTasksList(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Error refreshing server tasks");
		alert.setContentText("An error has been thrown by the server while refreshing the tasks list:\n\n'"+message+"'");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedServerTask() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No server task selected");
		alert.setContentText("Please, select a task first to perform this action.");		
		alert.showAndWait();
	}

	public static void showErrorPerformServerAction(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("An error has ocurred on the server");
		alert.setContentText("Server has thrown this error trying to perform this operation:\n\n'"+message+"'");		
		alert.showAndWait();
	}

	public static void showTaskHasStarted(int id) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Task "+id+" has started running");
		alert.setContentText("Your task has started running on the server.");		
		alert.showAndWait();
	}

	public static void showErrorConfigureServer() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Couldn't connect to server");
		alert.setContentText("Could not establish a connection with a TweetExtractor server instance.\n\nPlease, configure the server adress from the home screen.");		
		alert.showAndWait();
	}

	public static void showErrorExtractionIsCurrentlyUpdating() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Couldn't update extraction");
		alert.setContentText("This extraction is currently being updated by another process.");		
		alert.showAndWait();
	}

	public static void showErrorScheduleServerTask(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("An error has ocurred scheduling your task");
		alert.setContentText("Server has thrown this error trying to schedule this task:\n\n'"+message+"'");		
		alert.showAndWait();
	}

	public static void showSuccessScheduleServerTask() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Server task has been scheduled");
		alert.setContentText("The server task has been scheduled and will be executed on the chosen date and time.");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedDateTime() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No date selected");
		alert.setContentText("Please select a valid date and time to schedule your task.");		
		alert.showAndWait();
	}
	public static void showErrorTimeAlreadyPassed() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Time already passed");
		alert.setContentText("Please select a date and time from the future.");		
		alert.showAndWait();
	}
	public static void showErrorNotAvailableYet() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Feature not available");
		alert.setContentText("This feature has not yet been implemented. It will be available in a future release of TweetExtractor");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedGraphics() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No chart selected");
		alert.setContentText("Please, select a graphic chart first to perform this action.");		
		alert.showAndWait();	
	}

	public static void showErrorNoSelectedReport() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No report selected");
		alert.setContentText("Please, select a report first to perform this action.");		
		alert.showAndWait();	
	}

	public static void showErrorNoReportsForThisChart(List<AnalyticsReportTypes> compatibles) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Didn't find compatible reports");
		StringBuilder builder = new StringBuilder();
		builder.append("It seems that you don't have any analytics report compatible with that type of chart.\n");
		if(compatibles.isEmpty()) {
			builder.append("No report is compatible with this chart yet.");
		}else {
			builder.append("This kind of chart is only compatible with these reports:\n");
			for(AnalyticsReportTypes type : compatibles) {
				builder.append(type.toString()+"\n");
			}
		}
		alert.setContentText(builder.toString());		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedChartConfig() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No saved preferences selected");
		alert.setContentText("Please, select a saved preference first to perform this action.");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedChartType() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No chart type selected");
		alert.setContentText("Please, select a chart type first to create a graphic chart.");		
		alert.showAndWait();
	}

	public static Alert showSuccessCreateGraphicChart(int id) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Generation successful");
		alert.setContentText("Your graphic chart has been successfully generated with id "+id+" and will now appear on your graphic charts list.");		
		return alert;
	}

	public static void showSuccessExportChart() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Chart succesfully exported");
		alert.setContentText("You graphic chart has been succesfully exported.");		
		alert.showAndWait();
	}

	public static void showErrorEmptyReport() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty report");
		alert.setContentText("You selected an empty report. Please add some data to it first manually or with an asynchronous task.");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedStopWordsList() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No list selected");
		alert.setContentText("Please, select a custom stop words list first to perform that action.");		
		alert.showAndWait();
	}

	public static void showErrorEmptyStopWordsListName() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No name given");
		alert.setContentText("Please, give a name to your custom stop words list.");		
		alert.showAndWait();
	}

	public static void showErrorCustomStopWordsListNameExists() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("List already exists");
		alert.setContentText("You already have a list named like that for this language, please select another name.");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedStopWord() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No word selected");
		alert.setContentText("Please, select a stop word first to perform that action.");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedPixelBoundary() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No image selected");
		alert.setContentText("You need to open an image as the pixel boundary for your word cloud chart.\nIf you dont want to do so, you can allways select another type of word cloud chart.");		
		alert.showAndWait();
	}

	public static Alert showErrorGeneratingWordCloud() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Unknown Error");
		alert.setContentText("Sorry, but it seems that an unknown error has ocurred while generating your word cloud.");		
		return alert;
	}

	public static Alert showGenericMessageUpdateReport(String value) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Unknown Error");
		alert.setContentText("Sorry, but it seems that an unknown error has ocurred while updating your report:\n\n"+value);		
		return alert;
	}

	public static Alert showSuccessUpdateReport(int id) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Update succeded");
		alert.setContentText("The report with id "+id + " has been succesfully updated.");		
		return alert;
	}

	public static Alert showSuccessDeleteReport(int id) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Delete succeded");
		alert.setContentText("The report with id "+id + " has been succesfully deleted.");		
		return alert;
	}

	public static Alert showErrorDeleteReport() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Unknown Error");
		alert.setContentText("Sorry, but it seems that an unknown error has ocurred while deleting your report.");		
		return alert;
	}

	public static void showSuccessCreateNewReport(int id) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Report "+ id + " created");
		alert.setContentText("Your new report has been successfully saved with id: "+id+".");		
		alert.showAndWait();
	}

	public static void showErrorEmptyNERPreferencesName() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No name given");
		alert.setContentText("Please, give a name to your new NER preferences.");		
		alert.showAndWait();
	}

	public static void showErrorNERPreferencesNameExists() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Name already exists");
		alert.setContentText("You already have a preferences set named like that for this language, please select another name.");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedNERPreferences() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No preferences selected");
		alert.setContentText("Please, select a NER preferences set first to perform that action.");		
		alert.showAndWait();
	}

	public static void showErrorNoSelectedNamedEntity() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No named entity selected");
		alert.setContentText("Please, select a named entity first to perform that action.");		
		alert.showAndWait();		
	}

	public static void showErrorNoSelectedTopic() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No topic selected");
		alert.setContentText("Please, select a topic first to perform that action.");		
		alert.showAndWait();		
	}

	public static void showErrorEmptyName(String nameHolder) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No name given");
		alert.setContentText("Please, give a name to your "+nameHolder);		
		alert.showAndWait();
	}

	public static void showErrorNameTooLong(int maxChars) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Name is too long");
		alert.setContentText("The maximum length permitted for this name is "+maxChars+".\nPlease give a shorter name.");		
		alert.showAndWait();
	}

	public static void showErrorNamedEntityAlreadyExists() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Named Entity Exists");
		alert.setContentText("You already have a named entity named like that. Please select a different name.");		
		alert.showAndWait();		
	}

	public static void showErrorTopicAlreadyExists() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Topic Already Exists");
		alert.setContentText("You already have a topic named like that. Please select a different name.");		
		alert.showAndWait();			
	}

	public static void showNoMoreTokensToClassify() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No more tokens to classify");
		alert.setContentText("You already classified every token from this set, pick another one or start classifying some tweets.");		
		alert.showAndWait();		
	}

	public static void showErrorNoTokenSetsAvailable() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No token sets available");
		alert.setContentText("You have no populated token sets for this selected language. Please create and populate (tokenize) a token set first.");		
		alert.showAndWait();				
	}

	public static void showErrorNoSelectedTokenSet() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No token set selected");
		alert.setContentText("Please, select a token set first to perform that action.");		
		alert.showAndWait();
	}

	public static Alert showErrorTokenizeTask(Exception exception) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Error occurred while tokenizing");
		if(exception==null) {
			alert.setContentText("An unknown error has occurred tokenizing your extractions. See the logs for further information.");
		}else {
			alert.setContentText("An exception has been thrown tokenizing your extractions:\n\n"+exception.getMessage());
		}
		return alert;
	}

	public static void showSuccessTokenize(int nTokens) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Tokens succesfully extracted");
		alert.setContentText("You succesfully populated your token set with "+nTokens+" tokens");		
		alert.showAndWait();		
	}
}
