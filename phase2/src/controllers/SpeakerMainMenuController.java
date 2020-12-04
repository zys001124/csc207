/**
 * Sample Skeleton for 'Speaker Main Menu.fxml' Controller Class
 */

package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SpeakerMainMenuController extends Controller{

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

    @FXML // fx:id="viewMessageHistoryButton"
    private Button viewMessageHistoryButton; // Value injected by FXMLLoader

    @FXML // fx:id="logoutButton"
    private Button logoutButton; // Value injected by FXMLLoader

    @FXML
    void onLogoutClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.LOGIN);
    }

    @FXML
    void onMessageAllAttendeesClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.MESSAGE_ALL_ATTENDING_EVENT);
    }

    @FXML
    void onMessageAttendeeClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.MESSAGE_USER);
    }

    @FXML
    void onSeeEventScheduleClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.EVENT_SCHEDULE);
    }

    @FXML
    void onViewMessageHistoryClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.VIEW_MESSAGES);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert messageAllAttendeesOfEventButton != null : "fx:id=\"messageAllAttendeesOfEventButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";
        assert messageAttendeeButton != null : "fx:id=\"messageAttendeeButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";
        assert seeEventScheduleButton != null : "fx:id=\"seeEventScheduleButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";
        assert viewMessageHistoryButton != null : "fx:id=\"viewMessageHistoryButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'Speaker Main Menu.fxml'.";

    }
}
