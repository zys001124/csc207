package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the message all attendees scene that sends a message to all attendees
 */
public class MessageAllAttendeesController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="messageInput"
    private TextField messageInput; // Value injected by FXMLLoader

    @FXML // fx:id="sendButton"
    private Button sendButton; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="noInputMessage"
    private Label noInputMessage; // Value injected by FXMLLoader

    /**
     * Method that navigates the user back to their corresponding main menu
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
     * Method that executes what to do when the send button is pushed
     * Puts a message on the scene on what happens
     */
    @FXML
    void onSendButtonClicked() {
        String screenMessage;
        String message = messageInput.getText();
        InputProcessResult result = sendMessage(message);
        if (result == InputProcessResult.NO_MESSAGE_DETECTED) {
            screenMessage = "No input detected. Please enter something.";
        } else {
            screenMessage = "Message Sent.";
        }

        noInputMessage.setText(screenMessage);
    }

    /**
     * Initializes the input fields for this controller
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert messageInput != null : "fx:id=\"messageInput\" was not injected: check your FXML file 'Message All Attendees.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'Message All Attendees.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Message All Attendees.fxml'.";
        assert noInputMessage != null : "fx:id=\"noInputMessage\" was not injected: check your FXML file 'Message All Attendees.fxml'.";

    }

    /**
     * Helper method that sends the message to all the attendees if a message is detected
     *
     * @param message the message to be sent to the attendees
     * @return InputProcessResult on if there is a success or not
     */
    private InputProcessResult sendMessage(String message) {
        if (message.equals("")) {
            return InputProcessResult.NO_MESSAGE_DETECTED;
        }

        // Use ID to override normal checks
        for (User attendee : userManager.getAttendees()) {
            messageManager.sendMessageById(userManager.getCurrentlyLoggedIn().getId(), attendee.getId(), message);
        }

        return InputProcessResult.SUCCESS;
    }
}

