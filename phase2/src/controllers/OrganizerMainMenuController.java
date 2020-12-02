package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class OrganizerMainMenuController extends Controller {

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

    @FXML // fx:id="changeEventCapacityButton"
    private Button changeEventCapacityButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewMessageHistoryButton"
    private Button viewMessageHistoryButton; // Value injected by FXMLLoader

    @FXML // fx:id="logoutButton"
    private Button logoutButton; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert createEventButton != null : "fx:id=\"createEventButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";
        assert cancelEventButton != null : "fx:id=\"cancelEventButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";
        assert messageUserButton != null : "fx:id=\"messageUserButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";
        assert messageSpeakersButton != null : "fx:id=\"messageSpeakersButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";
        assert messageAttendeesButton != null : "fx:id=\"messageAttendeesButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";
        assert createUserAccountButton != null : "fx:id=\"createUserAccountButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";
        assert changeEventCapacityButton != null : "fx:id=\"changeEventCapacityButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";
        assert viewMessageHistoryButton != null : "fx:id=\"viewMessageHistoryButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'Organizer Main Menu.fxml'.";

    }

}