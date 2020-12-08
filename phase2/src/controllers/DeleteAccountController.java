package controllers;

import exceptions.UserNotFoundException;
import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import useCaseClasses.UserManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * A controller for handling inputs when the Admin is deleting
 * an user account
 */
public class DeleteAccountController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userListField"
    private ListView<Label> userListField; // Value injected by FXMLLoader

    @FXML // fx:id="userNameField"
    private TextField userNameField; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    @FXML // fx:id="createMessageLabel"
    private Label createMessageLabel; // Value injected by FXMLLoader

    /**
     * Method that sends the user back to their corresponding main menu
     * @param event Action event when method is called upon (not used)
     */
    @FXML
    void onBackButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU);
    }

    /**
     * Method that handles the program input when the change button is clicked and
     * delete user account
     * @param event Action event when method is called upon (not used)
     */
    @FXML
    void onDeleteButtonClicked(ActionEvent event) {

        String label = "";

        String userName = userNameField.getText();

        InputProcessResult result = deleteAccount(userName);

        if (result == InputProcessResult.SUCCESS) {
            label = "Account deleted successfully.";
        } else if (result == InputProcessResult.USER_NOT_FOUND) {
            label = "This user does not exist. Try again.";
        } else if (result == InputProcessResult.INVALID_USER_TYPE) {
            label = "This user can not be deleted. Try again.";
        }
        createMessageLabel.setText(label);
    }

    /**
     * Initializes the scene buttons and labels and text boxes
     */
    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert userNameField != null : "fx:id=\"userNameField\" was not injected: check your FXML file 'Delete User Account.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Delete User Account.fxml'.";
        assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'Delete User Account.fxml'.";
        assert createMessageLabel != null : "fx:id=\"createMessageLabel\" was not injected: check your FXML file 'Delete User Account.fxml'.";
        assert userListField != null : "fx:id=\"userListField\" was not injected: check your FXML file 'Delete User Account.fxml'.";
    }

    private InputProcessResult deleteAccount(String userName) {

        try {
            if (userManager.checkAdmin(userName)) {
                return InputProcessResult.INVALID_USER_TYPE;
            }
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        if (!userManager.doesUserExist(userName)) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        UUID id = userManager.getUserID(userName);

        userManager.removeUser(id);
        return InputProcessResult.SUCCESS;
    }

    /**
     * Sets the event manager for this scene
     * @param userManager the user manager to be set to
     */
    @Override
    public void setUserManager(UserManager userManager) {
        super.setUserManager(userManager);

        setUserList();
        userManager.addObserver((o, changes, addedOrChanged, retrievedFromDatabase) -> setUserList());
    }

    private List<Label> getUserLabels() {
        List<String> labels = userManager.getDeleteAccountLabels();
        ArrayList<Label> result = new ArrayList<>();
        for (String label : labels) {
            result.add(new Label(label));
        }
        return result;
    }

    private void setUserList() {
        userListField.getItems().setAll(getUserLabels());
    }

}

