package controllers;

import entities.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import useCaseClasses.EventManager;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * A controller for the MessageAllAttendingEvent
 */
public class MessageAllAttendingEventController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventList"
    private ListView<Label> eventList; // Value injected by FXMLLoader

    @FXML // fx:id="messageField"
    private TextField messageField; // Value injected by FXMLLoader

    @FXML // fx:id="sendButton"
    private Button sendButton; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="createMessageLabel"
    private Label createMessageLabel; // Value injected by FXMLLoader

    /**
     * Method that sends the user back to their corresponding main menu
     */
    @FXML
    void onBackButtonClicked() {
        setSceneView(SceneViewType.SPEAKER_MAIN_MENU);
    }

    /**
     * Method that executes what to do when the send button is pushed
     * Puts a message on the scene on what happens
     */
    @FXML
    void onSendButtonClicked() {

        String label = "Message successfully sent. \nReturning to Main Menu";

        String text = messageField.getText();

        Label selectedItem = eventList.getSelectionModel().getSelectedItem();
        String eventName = selectedItem.getText().split(" on")[0];

        InputProcessResult result = handleInput(eventName, text);
        if (result == InputProcessResult.SUCCESS) {
            label = "Message successfully sent. \nReturning to Main Menu";
        } else if (result == InputProcessResult.INVALID_INPUT) {
            label = "Could Not understand your input. Maybe you haven't selected an event?";
        }
        createMessageLabel.setText(label);
    }

    /**
     * Initializes the scene buttons and labels and text boxes
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert eventList != null : "fx:id=\"eventList\" was not injected: check your FXML file 'Message All Event Attendees.fxml'.";
        assert messageField != null : "fx:id=\"messageField\" was not injected: check your FXML file 'Message All Event Attendees.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'Message All Event Attendees.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Message All Event Attendees.fxml'.";

    }

    /**
     * Sets the event manager for this scene
     *
     * @param eventManager the event manager to be set to
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);

        setEventList();
        messageManager.addObserver((o, changes, addedOrChanged) -> setEventList());
    }

    /**
     * Helper method to send the message that the speaker will type to all of the
     * users that are enrolled in this event
     * <p>
     * Called in handleInput if the event put in is found
     *
     * @param message the message that will be sent to all the users
     * @param event   the event that all the users are in that are getting sent a message
     */
    private void sendMessage(String message, String event) {
        messageManager.messageAllAttendingEvent(message, eventManager.getEvent(event),
                userManager.getCurrentlyLoggedIn().getId());
    }

    /**
     * Takes in the name of the event and the message the speaker wishes to send out and determines if
     * the name can be found in the events they are hosting. If not It returns a InputProcessResult
     * that prompts them to put in a valid event. If SUCCESS then the message will be sent out to all
     * the users attending that event.
     *
     * @param name    the name of the event to message all users attending
     * @param message - the message that will go out to all users attending name.
     * @return an InputProcessResult which will be given to the UI to determine to either go back
     * to the main menu or prompt user again.
     */
    private InputProcessResult handleInput(String name, String message) {
        FindEvent verify = verifyEvent(name);
        if (verify == FindEvent.FAIL) {
            return InputProcessResult.INVALID_INPUT;
        } else {
            sendMessage(message, name);
            return InputProcessResult.SUCCESS;
        }
    }

    /**
     * Helper method called in handleInput to help determine if the speaker put in the right event
     * name to message the attendees
     * <p>
     * Returns a FindEvent
     *
     * @param name the name of the event the user entered to determine if it exists.
     * @return a FindEvent where SUCCESS will continue the program but FAIL will prompt user to put
     * input in again.
     */
    private FindEvent verifyEvent(String name) {
        //Should we be using userManager.getCurrentlyLoggedIn() or should we just use the user field?
        if (eventManager.listOfEventsHosting(userManager.getCurrentlyLoggedIn().getId()).contains(name)) {
            return FindEvent.SUCCESS;
        } else {
            return FindEvent.FAIL;
        }
    }

    private List<Label> getEventLabels(Collection<Event> events) {
        ArrayList<Label> labels = new ArrayList<>();
        for (Event event : events) {
            String labelText = event.getEventTitle() + " on " + event.getEventTime().format(DateTimeFormatter.ofPattern("MMMM dd, HH:mm")) + ".";
            Label eventLabel = new Label(labelText);
            labels.add(eventLabel);
        }
        return labels;
    }

    private void setEventList() {
        UUID currentUserId = userManager.getCurrentlyLoggedIn().getId();
        List<Event> events = eventManager.getEventsWithSpeaker(currentUserId);
        eventList.getItems().setAll(getEventLabels(events));
    }

    /**
     * returns a enum for verifyEvent where SUCCESS is when the event is found and FAIL is when
     * it is not found.
     */
    private enum FindEvent {
        SUCCESS,
        FAIL
    }

}



