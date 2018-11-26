/**
 * 
 */
package es.uam.eps.tweetextractorfx.error;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Jose Antonio Garcia del Saz
 *
 */
public class ErrorDialog {
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
		return;
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
		return;
	}

	public static void showErrorExistingCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Credentials already exist");
		alert.setContentText(
				"These credentials already belong to this account. Please, choose any other credentials to add.");
		alert.showAndWait();
		return;
	}

	public static void showErrorEmptyCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty fields");
		alert.setContentText("Please, introduce the tokens for the new credentials.");
		alert.showAndWait();
		return;
	}

	public static void showErrorEmptyNickname() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty Nickname");
		alert.setContentText("Please, select the user's nickname that you want to use as the origin of the tweets.");
		alert.showAndWait();
		return;
	}

	public static void showErrorEmptyUrl() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No URL selected");
		alert.setContentText("Please, select the URL parameter for the extraction.");
		alert.showAndWait();
		return;
	}

	public static void showErrorWrongValues() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Somoe of the fields are not correct");
		alert.setContentText("Remember that Twitter accounts and list names are single words with no spaces.");
		alert.showAndWait();
		return;
	}

	public static void showErrorEmptyFields() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Empty fields");
		alert.setContentText("Please, select a Twitter account and a list available on that account.");
		alert.showAndWait();
		return;
	}

	public static void showErrorSelectDateSince() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No date selected");
		alert.setContentText("Please, select a date from which you would like to extract tweets");
		alert.showAndWait();
		return;
	}

	public static void showErrorEmptyNicknameTo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No username selected");
		alert.setContentText("Please, select the user's nickname that you want to use as the destiny of the tweets");
		alert.showAndWait();
		return;
	}

	public static void showErrorSelectDateTo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No date selected");
		alert.setContentText("Please, select the date until which you would like to extract tweets");
		alert.showAndWait();
		return;
	}

	public static void showErrorEmptyNicknameFrom() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No username selected");
		alert.setContentText("Please, select the user's nickname that you want to use as the origin of the tweets.");
		alert.showAndWait();
		return;
	}

	public static void showErrorSelectFilterRemove() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No filter selected");
		alert.setContentText("Please, select a filter from the list on the right in order to delete it.");
		alert.showAndWait();
		return;
	}

	public static void showErrorNotEnoughFilters() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Logic operations");
		alert.setContentText("Please, select at least two filters from the right to apply the OR application to them.");
		alert.showAndWait();
		return;
	}

	public static void showErrorNotEnoughFiltersNot() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Logic opeartions");
		alert.setContentText("Please, select at least one filter from the right to apply the NOT operation.");
		alert.showAndWait();
		return;
	}

	public static void showErrorUndoLogic() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Logic operations");
		alert.setContentText("Please, select a logic filter (OR or NOT) in order to undo the logic operation.");
		alert.showAndWait();
		return;
	}

	public static void showErrorSelectFilterAdd() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No filter selected");
		alert.setContentText("Please, select a filter type from the list on the left to add it to the extraction.");
		alert.showAndWait();
		return;
	}

	public static void showSuccessUpdatePassword() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Password updated");
		alert.setContentText("Your password has been succesfully updated");
		alert.showAndWait();
		return;
	}

	public static void showErrorNoCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No credentials available");
		alert.setContentText(
				"This user account has no registered credentials for the Twitter API.\nPlease, add some credentials from the home screen.");
		alert.showAndWait();
		return;
	}

	public static void showErrorNoSelectedCredentials() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No selected credentials");
		alert.setContentText("Select some credentials to perform this action.");
		alert.showAndWait();
		return;
	}

	public static void showErrorLoadExtraction(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Reading error");
		alert.setContentText("An error has ocurred loading an extraction:\n" + message);
		alert.showAndWait();
		return;
	}

	public static void showErrorNoSelectedExtraction() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No extraction selected");
		alert.setContentText("Select an extraction to perform this action.");
		alert.showAndWait();
		return;
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
		return;
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
		return;
	}

	public static void showErrorNoTweetSelected() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("No tweet selected");
		alert.setContentText("Select a tweet from the list above to delete it from the extraction");
		alert.showAndWait();
		return;
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

}
