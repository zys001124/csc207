/**
 * Sample Skeleton for 'VIP Main Menu.fxml' Controller Class
 */

package controllers;

import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    void onEnrollInEventButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.ENROLL_IN_EVENT);
    }

    @FXML
    void onLogoutButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.LOGIN);
    }

    @FXML
    void onMessageUserButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.MESSAGE_USER);
    }

    @FXML
    void onSeeEventScheduleButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.EVENT_SCHEDULE);
    }

    @FXML
    void onUnenrollInEventButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.UNENROLL_IN_EVENT);
    }


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
