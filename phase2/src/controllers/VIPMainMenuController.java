package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class for the VIP main menu scene that handles the input for which button the user
 * presses for this scene.
 */
public class VIPMainMenuController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="messageUserButton"
    private Button messageUserButton; // Value injected by FXMLLoader

    @FXML // fx:id="enrollInEventButton"
    private Button enrollInEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="unenrollInEventButton"
    private Button unenrollInEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="seeEventSchedueButton"
    private Button seeEventSchedueButton; // Value injected by FXMLLoader

    @FXML // fx:id="logoutButton"
    private Button logoutButton; // Value injected by FXMLLoader

    /**
     * Method that directs the user to the enrol in event scene when this button is clicked
     */
    @FXML
    void onEnrollInEventButtonClicked() {
        setSceneView(SceneViewType.ENROLL_IN_EVENT);
    }

    /**
     * Method that directs the user to the login scene when this button is clicked
     */
    @FXML
    void onLogoutButtonClicked() {
        setSceneView(SceneViewType.LOGIN);
    }

    /**
     * Method that directs the user to the message user scene when this button is clicked
     */
    @FXML
    void onMessageUserButtonClicked() {
        setSceneView(SceneViewType.MESSAGE_USER);
    }

    /**
     * method that directs the user to the event schedule scene when this button is clicked
     */
    @FXML
    void onSeeEventScheduleButtonClicked() {
        setSceneView(SceneViewType.EVENT_SCHEDULE);
    }

    /**
     * Method that directs the user to the unenroll in event scene when this button is clicked
     */
    @FXML
    void onUnenrollInEventButtonClicked() {
        setSceneView(SceneViewType.UNENROLL_IN_EVENT);
    }


    /**
     * initialize method that makes initializes the button fields for both the controller and class
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert messageUserButton != null : "fx:id=\"messageUserButton\" was not injected: check your FXML file 'VIP Main Menu.fxml'.";
        assert enrollInEventButton != null : "fx:id=\"enrollInEventButton\" was not injected: check your FXML file 'VIP Main Menu.fxml'.";
        assert unenrollInEventButton != null : "fx:id=\"unenrollInEventButton\" was not injected: check your FXML file 'VIP Main Menu.fxml'.";
        assert seeEventSchedueButton != null : "fx:id=\"seeEventSchedueButton\" was not injected: check your FXML file 'VIP Main Menu.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'VIP Main Menu.fxml'.";

    }
}
