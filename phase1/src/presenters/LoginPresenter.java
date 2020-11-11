package presenters;

import controllers.InputProcessResult;
import controllers.LoginController;

public class LoginPresenter {

    public String getIntro() {
        return "Welcome to Tech Conference, please login.";
    }

    public String getUsernameInputPrompt() {
        return "Username: ";
    }

    public String getPasswordInputPrompt() {
        return "Password: ";
    }

    public String getLoginResultMessage(InputProcessResult result) {
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
