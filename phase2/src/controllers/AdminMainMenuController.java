package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for handling the input from the GUI buttons for the Admin main menu screen
 */
public class AdminMainMenuController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="createEventButton"
    private Button createEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelEventButton"
    private Button cancelEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="messageUserButton"
    private Button messageUserButton; // Value injected by FXMLLoader

    @FXML // fx:id="messageSpeakersButton"
    private Button messageSpeakersButton; // Value injected by FXMLLoader

    @FXML // fx:id="messageAttendeesButton"
    private Button messageAttendeesButton; // Value injected by FXMLLoader

    @FXML // fx:id="createUserAccountButton"
    private Button createUserAccountButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteUserAccountButton"
    private Button deleteUserAccountButton; // Value injected by FXMLLoader

    @FXML // fx:id="logoutButton"
    private Button logoutButton; // Value injected by FXMLLoader

    @FXML // fx:id="changeCapacity"
    private Button changeCapacity; // Value injected by FXMLLoader

    /**
     * This method is called by the FXMLLoader when initialization is complete. It overrides the initializes
     * method from the Controller class
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert createEventButton != null : "fx:id=\"createEventButton\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
        assert cancelEventButton != null : "fx:id=\"cancelEventButton\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
        assert messageUserButton != null : "fx:id=\"messageUserButton\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
        assert messageSpeakersButton != null : "fx:id=\"messageSpeakersButton\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
        assert messageAttendeesButton != null : "fx:id=\"messageAttendeesButton\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
        assert createUserAccountButton != null : "fx:id=\"createUserAccountButton\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
        assert deleteUserAccountButton != null : "fx:id=\"deleteUserAccountButton\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
        assert changeCapacity != null : "fx:id=\"changeCapacity\" was not injected: check your FXML file 'Admin Main Menu.fxml'.";
    }

    /**
     * Method that directs the program to the cancel event scene when this button is clicked
     */
    @FXML
    void onCancelEventButtonClicked() {
        setSceneView(SceneViewType.CANCEL_EVENT);
    }

    /**
     * Method that directs the program to the create event scene when this button is clicked
     */
    @FXML
    void onCreateEventButtonClicked() {
        setSceneView(SceneViewType.CREATE_EVENT);
    }

    /**
     * Method that directs the program to create account scene when this button is clicked
     */
    @FXML
    void onCreateUserAccountButtonClicked() {
        setSceneView(SceneViewType.CREATE_ACCOUNT);
    }

    /**
     * Method that directs the program to the delete account scene when this button is clicked
     */
    @FXML
    void onDeleteUserAccountButtonClicked() {
        setSceneView(SceneViewType.DELETE_ACCOUNT);
    }

    /**
     * Method that directs the program to the login scene when this button is clicked
     */
    @FXML
    void onLogoutButtonClicked() {
        setSceneView(SceneViewType.LOGIN);
    }

    /**
     * Method that directs the program to the Message All Attendees scene when this button is clicked
     */
    @FXML
    void onMessageAllAttendeesButtonClicked() {
        setSceneView(SceneViewType.MESSAGE_ALL_ATTENDEES);
    }

    /**
     * Method that directs the program to the message all speakers scene when this button is clicked
     */
    @FXML
    void onMessageAllSpeakersButtonClicked() {
        setSceneView(SceneViewType.MESSAGE_ALL_SPEAKERS);
    }

    /**
     * Method that directs the program to the message user scene when this button is clicked
     */
    @FXML
    void onMessageUserButtonClicked() {
        setSceneView(SceneViewType.MESSAGE_USER);
    }

    /**
     * Method that directs the program to the change capacity scene when this button is clicked
     */
    @FXML
    void onChangeCapacityClicked() {
        setSceneView(SceneViewType.CHANGE_CAPACITY);

    }
}
