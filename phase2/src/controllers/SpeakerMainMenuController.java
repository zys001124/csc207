package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the speaker main menu scene that handles the input from the scene and navigates to the correct
 * scene when a button is clicked
 */
public class SpeakerMainMenuController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="messageAllAttendeesOfEventButton"
    private Button messageAllAttendeesOfEventButton; // Value injected by FXMLLoader

    @FXML // fx:id="messageAttendeeButton"
    private Button messageAttendeeButton; // Value injected by FXMLLoader

    @FXML // fx:id="seeEventScheduleButton"
    private Button seeEventScheduleButton; // Value injected by FXMLLoader

    @FXML // fx:id="logoutButton"
    private Button logoutButton; // Value injected by FXMLLoader

    /**
     * Method that directs the user to the login scene when this button is clicked.
     */
    @FXML
    void onLogoutClicked() {
        setSceneView(SceneViewType.LOGIN);
    }

    /**
     * Method that directs the user to the message all attending event scene when this button is clicked
     */
    @FXML
    void onMessageAllAttendeesClicked() {
        setSceneView(SceneViewType.MESSAGE_ALL_ATTENDING_EVENT);
    }

    /**
     * Method that directs the user to the message user scene when this button is clicked
     */
    @FXML
    void onMessageAttendeeClicked() {
        setSceneView(SceneViewType.MESSAGE_USER);
    }

    /**
     * Method that directs the user to the event schedule scene when this button is clicked
     */
    @FXML
    void onSeeEventScheduleClicked() {
        setSceneView(SceneViewType.EVENT_SCHEDULE);
    }


    /**
     * initialize method to initialize the buttons in the scene and the controller to be clicked
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert messageAllAttendeesOfEventButton != null : "fx:id=\"messageAllAttendeesOfEventButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";
        assert messageAttendeeButton != null : "fx:id=\"messageAttendeeButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";
        assert seeEventScheduleButton != null : "fx:id=\"seeEventScheduleButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";

    }
}
