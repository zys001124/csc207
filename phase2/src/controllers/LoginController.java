package controllers;

import entities.User;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A controller for the login screen
 */
public class LoginController extends Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;

    private LoginListener listener;

    @FXML
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'loginScene.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'loginScene.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'loginScene.fxml'.";

    }

    /**
     * Method that takes the user to their corresponding menu page when they put their
     * Username and password in the login scene
     */
    @FXML
    void onLoginButtonClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String labelText = "";

        InputProcessResult result = verifyLogin(username, password);
        if (result == InputProcessResult.INCORRECT_PASSWORD) {
            labelText = "Incorrect Password, please try again";
        } else if (result == InputProcessResult.USER_NOT_FOUND) {
            labelText = "User not found, please try again";
        } else {
            // change scene
            if (result == InputProcessResult.SUCCESSFUL_ATTENDEE_LOGIN) {
                listener.onSuccessfulLogin();
                setSceneView(SceneViewType.ATTENDEE_MAIN_MENU);
            } else if (result == InputProcessResult.SUCCESSFUL_ORGANIZER_LOGIN) {
                listener.onSuccessfulLogin();
                setSceneView(SceneViewType.ORGANIZER_MAIN_MENU);
            } else if (result == InputProcessResult.SUCCESSFUL_SPEAKER_LOGIN) {
                listener.onSuccessfulLogin();
                setSceneView(SceneViewType.SPEAKER_MAIN_MENU);
            } else if (result == InputProcessResult.SUCCESSFUL_ADMIN_LOGIN) {
                listener.onSuccessfulLogin();
                setSceneView(SceneViewType.ADMIN_MAIN_MENU);
            } else if (result == InputProcessResult.SUCCESSFUL_VIP_LOGIN) {
                listener.onSuccessfulLogin();
                setSceneView(SceneViewType.VIP_MAIN_MENU);
            } else {
                labelText = "ERROR";
            }
        }

        loginMessageLabel.setText(labelText);
    }

    /**
     * Logs the user into the system if the username and password passed
     * as input is a match for a User object in UserManager
     *
     * @param username - The username passed as input
     * @param password - The password passed as input
     * @return an InputProcessResult - The result of the input handling
     */
    private InputProcessResult verifyLogin(String username, String password) {
        try {
            userManager.userLogin(username, password);
        } catch (IncorrectPasswordException e) {
            return InputProcessResult.INCORRECT_PASSWORD;
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        User.UserType type = userManager.getCurrentlyLoggedIn().getType();

        switch (type) {
            case ATTENDEE:
                return InputProcessResult.SUCCESSFUL_ATTENDEE_LOGIN;
            case ORGANIZER:
                return InputProcessResult.SUCCESSFUL_ORGANIZER_LOGIN;
            case SPEAKER:
                return InputProcessResult.SUCCESSFUL_SPEAKER_LOGIN;
            case ADMIN:
                return InputProcessResult.SUCCESSFUL_ADMIN_LOGIN;
            case VIP:
                return InputProcessResult.SUCCESSFUL_VIP_LOGIN;
            default:
                return InputProcessResult.INVALID_INPUT;
        }
    }

    /**
     * Adds a LoginListener to this controller
     *
     * @param listener - the LoginListener
     */
    public void addLoginListener(LoginListener listener) {
        this.listener = listener;
    }

}
