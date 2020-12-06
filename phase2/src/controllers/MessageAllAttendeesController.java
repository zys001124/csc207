/**
 * Sample Skeleton for 'Message All Attendees.fxml' Controller Class
 */

package controllers;

import entities.User;
import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
    }

    @FXML
    void onSendButtonClicked(ActionEvent event) {
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

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert messageInput != null : "fx:id=\"messageInput\" was not injected: check your FXML file 'Message All Attendees.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'Message All Attendees.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Message All Attendees.fxml'.";
        assert noInputMessage != null : "fx:id=\"noInputMessage\" was not injected: check your FXML file 'Message All Attendees.fxml'.";

    }

    public InputProcessResult sendMessage(String message) {
        if (message.equals("")) {
            return InputProcessResult.NO_MESSAGE_DETECTED;
        }
        for (User user : userManager.getUsers()) {
            if (user.getType().equals(User.UserType.ATTENDEE)) {
                messageManager.sendMessage(userManager.getCurrentlyLoggedIn().getId(), user.getId(), message);
            }
        }
        return InputProcessResult.SUCCESS;
    }


}

