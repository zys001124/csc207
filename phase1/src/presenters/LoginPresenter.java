package presenters;

import controllers.InputProcessResult;
import controllers.LoginController;

/**
 * A presenter for the login screen
 */
public class LoginPresenter extends Presenter {

    /**
     * Gets the String that should be displayed on the login screen
     * before the user makes an input
     *
     * @return the String that should be displayed on the login screen
     *      * before the user makes an input
     */
    public String getPreInputText() {
        return "Welcome to Tech Conference, please login.";
    }

    /**
     * Gets the username prompt
     * @return a String - the username prompt
     */
    public String getUsernameInputPrompt() {
        return "Username: ";
    }

    /**
     * Gets the password prompt
     * @return a String - the password prompt
     */
    public String getPasswordInputPrompt() {
        return "Password: ";
    }

    /**
     * Gets the String to be displayed after a user makes an input
     * on the login screen
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return The string that should be displayed after input
     */
    public String getInputResponseText(InputProcessResult result) {
        if(result == InputProcessResult.SUCCESS) {
            return "Login Successful!";
        }
        else if(result == InputProcessResult.INCORRECT_PASSWORD) {
            return "Incorrect password. Please try again";
        }
        else {
            return "User not found. Please try again";
        }
    }
}
