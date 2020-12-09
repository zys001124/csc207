package controllers;

import entities.User;
import exceptions.NoPasswordException;
import exceptions.NoUsernameException;
import exceptions.UserTypeDoesNotExistException;
import exceptions.UsernameAlreadyExistsException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for handling the input for the create account scene
 * Extends controller.
 */
public class CreateAccountController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="usernameField"
    private TextField usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private TextField passwordField; // Value injected by FXMLLoader

    @FXML // fx:id="userTypeField"
    private TextField userTypeField; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="invalidInputMessage"
    private Label invalidInputMessage; // Value injected by FXMLLoader

    /**
     * Method that takes the user back to their corresponding main menu scene
     */
    @FXML
    void onBackButtonClicked() {
        User.UserType currentUserType = userManager.getCurrentlyLoggedIn().getType();
        if (currentUserType == User.UserType.ORGANIZER) {
            setSceneView(SceneViewType.ORGANIZER_MAIN_MENU);
        } else if (currentUserType == User.UserType.ADMIN) {
            setSceneView(SceneViewType.ADMIN_MAIN_MENU);
        }
    }

    /**
     * Method that handles the input for the scene when the user tries to create an account
     * Will output whether or not a user has been created
     */
    @FXML
    void onCreateButtonClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String type = userTypeField.getText();

        String labelText;

        InputProcessResult result = handleInput(username, password, type);
        if (result == InputProcessResult.USERNAME_TAKEN) {
            labelText = "Username already taken. Try again.";
        } else if (result == InputProcessResult.INVALID_USER_TYPE) {
            labelText = "Please enter a valid user type.";
        } else {
            //labelText = "Success";
            labelText = "Account created successfully.";
        }

        invalidInputMessage.setText(labelText);

    }

    /**
     * Initializes the input fields for this controller
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'Create User Account.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'Create User Account.fxml'.";
        assert userTypeField != null : "fx:id=\"userTypeField\" was not injected: check your FXML file 'Create User Account.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Create User Account.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'Create User Account.fxml'.";

    }

    /**
     * Helper method for handling the input of the user.
     * Creates the user if no exception is found
     *
     * @param username the username of the account to be created
     * @param password the password of the account to be created
     * @param type     the type of account to be created
     * @return InputProcessResult to help the button method decide on what to output if the user has
     * been created or not
     */
    private InputProcessResult handleInput(String username, String password, String type) {
        try {
            User.UserType userType = userManager.parseType(type);
            userManager.addUser(userType, username, password);
            return InputProcessResult.SUCCESS;
        } catch (UsernameAlreadyExistsException e) {
            return InputProcessResult.USERNAME_TAKEN;
        } catch (UserTypeDoesNotExistException e) {
            return InputProcessResult.INVALID_USER_TYPE;
        } catch (NoPasswordException e) {
            return InputProcessResult.NO_PASSWORD_GIVEN;
        } catch (NoUsernameException e) {
            return InputProcessResult.NO_USERNAME_GIVEN;
        }
    }
}

