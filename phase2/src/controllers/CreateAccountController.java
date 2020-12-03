/**
 * Sample Skeleton for 'Create User Account.fxml' Controller Class
 */

package controllers;

import entities.User;
import exceptions.InvalidUserTypeException;
import exceptions.UserTypeDoesNotExistException;
import exceptions.UsernameAlreadyExistsException;
import handlers.SceneNavigator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateAccountController extends Controller{

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

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        User.UserType currentUserType = userManager.getCurrentlyLoggedIn().getType();
        if(currentUserType == User.UserType.ORGANIZER) {
            setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
        }
        else if(currentUserType == User.UserType.ADMIN) {
            setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU);
        }
    }

    @FXML
    void onCreateButtonClicked(ActionEvent event){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String type = userTypeField.getText();

        String labelText = "";

        InputProcessResult result = handleInput(username, password, type);
        if(result == InputProcessResult.USERNAME_TAKEN){
            labelText = "Username already taken. Try again.";
        }
        else if(result == InputProcessResult.INVALID_USER_TYPE){
            labelText = "Please enter a valid user type.";
        }
        else if(result == InputProcessResult.UNQUALIFIED_USER){
            labelText = "This user is unqualified.";
        }
        else{
            //labelText = "Success";
            labelText = "Account created successfully.";
        }

        invalidInputMessage.setText(labelText);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'Create User Account.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'Create User Account.fxml'.";
        assert userTypeField != null : "fx:id=\"userTypeField\" was not injected: check your FXML file 'Create User Account.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Create User Account.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'Create User Account.fxml'.";

    }

    public InputProcessResult handleInput(String username, String password, String type){
        try {
            User.UserType userType = userManager.parseType(type);
            userManager.addUser(userType, username, password);
            return InputProcessResult.SUCCESS;
        } catch (UsernameAlreadyExistsException e) {
            return InputProcessResult.USERNAME_TAKEN;
        } catch (UserTypeDoesNotExistException e) {
            return InputProcessResult.INVALID_USER_TYPE;
        } catch (InvalidUserTypeException e) {
            return InputProcessResult.UNQUALIFIED_USER;
        }
    }
}
//public class CreateAccountController extends Controller{
//
//    private final UserManager userManager;
//
//    /**
//     * Creates a CreateAccountController with the given UserManager
//     *
//     * @param um - The userManager this controller will use
//     */
//    public CreateAccountController(UserManager um) {
//        userManager = um;
//    }
//
//    /**
//     * Handles the input given by the user
//     *
//     * @param input the users input
//     * @return an InputProcessResult enum that details what happened
//     * as a result of the given input
//     */
//    public InputProcessResult handleInput(String input) {
//
//        if (input.equals("back")) {
//            return InputProcessResult.BACK;
//        }
//
//        String[] usernamePasswordAndType = input.split(" ");
//
//        // Invalid input
//        if (usernamePasswordAndType.length != 3) {
//            return InputProcessResult.INVALID_INPUT;
//        }
//
//        String username = usernamePasswordAndType[0];
//        String password = usernamePasswordAndType[1];
//
//        try {
//            User.UserType type = userManager.parseType(usernamePasswordAndType[2]);
//            userManager.addUser(type, username, password);
//            return InputProcessResult.SUCCESS;
//        } catch (UsernameAlreadyExistsException e) {
//            return InputProcessResult.USERNAME_TAKEN;
//        } catch (UserTypeDoesNotExistException e) {
//            return InputProcessResult.INVALID_USER_TYPE;
//        } catch (InvalidUserTypeException e) {
//            return InputProcessResult.UNQUALIFIED_USER;
//        }
//
//    }
//
//}
