package controllers;

import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import useCaseClasses.UserManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


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


    @FXML
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'loginScene.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'loginScene.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'loginScene.fxml'.";

    }

    @FXML
    void onLoginButtonClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String labelText = "ERROR";

        InputProcessResult result = verifyLogin(username, password);
        if(result == InputProcessResult.INCORRECT_PASSWORD) {
            labelText = "Incorrect Password, please try again";
        }
        else if(result == InputProcessResult.USER_NOT_FOUND) {
            labelText = "User not found, please try again";
        }
        else{
            // change scene
            setSceneView(SceneNavigator.SceneView.MAIN_MENU);
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
    public InputProcessResult verifyLogin(String username, String password) {
        try {
            userManager.userLogin(username, password);
        } catch (IncorrectPasswordException e) {
            return InputProcessResult.INCORRECT_PASSWORD;
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        return InputProcessResult.SUCCESS;
    }

}
