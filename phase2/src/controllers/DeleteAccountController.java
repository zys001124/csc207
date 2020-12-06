package controllers;

import entities.User;
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
import java.util.*;


public class DeleteAccountController extends Controller{

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

    @FXML
    void onBackButtonClicked(ActionEvent event) { setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU); }

    @FXML
    void onDeleteButtonClicked(ActionEvent event) {

        String label = "";

        String userName = userNameField.getText();

        InputProcessResult result = deleteAccount(userName);

        if (result == InputProcessResult.SUCCESS){
            label = "Account deleted successfully.";
        } else if (result == InputProcessResult.USER_NOT_FOUND) {
            label = "This user does not exist. Try again.";
        } else if (result == InputProcessResult.INVALID_USER_TYPE) {
            label = "This user can not be deleted. Try again.";
        }
        createMessageLabel.setText(label);
    }

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
            if(userManager.checkAdmin(userName)) {
                return InputProcessResult.INVALID_USER_TYPE;
            }
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        if(!userManager.doesUserExist(userName)) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        UUID id = userManager.getUserID(userName);

        userManager.removeUser(id, false);
        return InputProcessResult.SUCCESS;
    }

    @Override
    public void setUserManager(UserManager userManager) {
        super.setUserManager(userManager);

        setUserList();
    }

    private List<Label> getUserLabels(Collection<User> users) {
        ArrayList<Label> labels = new ArrayList<>();
        for(User user: users) {
            labels.add(new Label(user.getUsername()+": "+user.getType()));
        }
        return labels;
    }

    private void setUserList(){
        userListField.getItems().addAll(getUserLabels(userManager.getUsers()));
    }

}



///**
// * A controller for handling inputs when try to delete a user
// */
//
//public class DeleteAccountController {
//
//    private final UserManager userManager;
//
//    /**
//     * Creates a CreateAccountController with the given UserManager
//     *
//     * @param um - The userManager this controller will use
//     */
//    public DeleteAccountController(UserManager um){
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
//    public InputProcessResult getNextScreen(String input){
//
//        if (input.equals("back")) {
//            return InputProcessResult.BACK;
//        }
//
//        List<User> users = userManager.getUsers();
//
//        for(User u : users){
//            if(u.getUsername().equals(input)){
//                UUID wanted_id = userManager.getUserID(input);
//                if(userManager.getUser(wanted_id).getType().equals(User.UserType.ADMIN)){
//                    return InputProcessResult.INVALID_USER_TYPE;
//                }
//                userManager.removeUser(wanted_id);
//                return InputProcessResult.SUCCESS;
//            }
//        }
//
//        return InputProcessResult.USER_NOT_FOUND;
//
//    }
//
//}
