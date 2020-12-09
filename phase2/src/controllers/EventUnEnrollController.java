package controllers;

import entities.Event;
import exceptions.EventNotFoundException;
import exceptions.UserNotEnrolledInEventException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import useCaseClasses.EventManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the un-enroll event scene that un-enrolls an attendee from an event
 */
public class EventUnEnrollController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventListView"
    private ListView<Label> eventListView; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="unEnrollButton"
    private Button unEnrollButton; // Value injected by FXMLLoader

    @FXML // fx:id="unEnrollMessageLabel"
    private Label unEnrollMessageLabel; // Value injected by FXMLLoader

    /**
     * Initializes the input fields for this controller
     */
    @FXML
    void initialize() {
        assert eventListView != null : "fx:id=\"eventListView\" was not injected: check your FXML file 'Unenroll Event.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Unenroll Event.fxml'.";
        assert unEnrollButton != null : "fx:id=\"unEnrollButton\" was not injected: check your FXML file 'Unenroll Event.fxml'.";
        assert unEnrollMessageLabel != null : "fx:id=\"unEnrollMessageLabel\" was not injected: check your FXML file 'Unenroll Event.fxml'.";
    }

    /**
     * Method that navigates the user back to their corresponding main menu
     */
    @FXML
    void onBackButtonClicked() {
        setSceneView(SceneViewType.ATTENDEE_MAIN_MENU);
    }

    /**
     * Method that executes what to do when the un-enroll button is pushed
     * and print a message on the scene of the result
     */
    @FXML
    void onUnEnrollButtonClicked() {
        String eventTitle = eventListView.getSelectionModel().getSelectedItem().getText().split("  Time: ")[0];
        String label;
        InputProcessResult result = unEnrollEvent(eventTitle);
        if (result == InputProcessResult.SUCCESS) {
            label = "Un-Enroll Successful!";
        } else if (result == InputProcessResult.EVENT_NOT_FOUND) {
            label = "Event not found. Please try again.";
        } else {
            label = "You are not enrolled in this event. Please try again.";
        }
        unEnrollMessageLabel.setText(label);
    }

    /**
     * Set up the event manager for this scene
     *
     * @param eventManager the eventManager to be set to
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);
        setEventListField();
        eventManager.addObserver((o, changes, addedOrChanged) -> setEventListField());
    }

    /**
     * Helper Method to set up all the events this attendee is currently enrolled in
     * to be displayed in this scene
     */
    private void setEventListField() {
        eventListView.getItems().setAll(getEventLabels(eventManager.getEvents()));
        eventListView.refresh();
    }

    /**
     * Helper Method to set up the all the information for each event on a label
     *
     * @param events List of events to be displayed
     * @return A list of label with information on them
     */
    private List<Label> getEventLabels(List<Event> events) {
        ArrayList<Label> labels = new ArrayList<>();
        for (Event event : events) {
            if (event.hasAttendee(userManager.getCurrentlyLoggedIn().getId())) {
                String labelText = eventManager.getIndividualTitle(event)
                        + "  Time: " + eventManager.getIndividualTime(event, "yyyy-MM-dd HH:mm:ss")
                        + "  Room: " + eventManager.getIndividualRoom(event)
                        + "  Capacity: " + eventManager.getIndividualCapacity(event)
                        + "  Currently Enrolled: " + eventManager.getIndividualEnrolledNumber(event)
                        + "  Event Type: " + eventManager.getIndividualType(event)
                        + "  VIP only: " + eventManager.getIndividualVIP(event);
                Label label = new Label(labelText);
                labels.add(label);
            }
        }
        return labels;
    }

    /**
     * Helper Method for when the un-enroll button is clicked. Used to determine whether
     * the attendee is successfully un-enrolled from an event and to help generate message
     *
     * @param eventInput The input from the scene
     * @return An InputProcessResult enum that details what happened as a result of the given input
     */
    private InputProcessResult unEnrollEvent(String eventInput) {
        try {
            eventManager.removeUserFromEvent(eventInput, userManager.getCurrentlyLoggedIn());
        } catch (EventNotFoundException e) {
            return InputProcessResult.EVENT_NOT_FOUND;
        } catch (UserNotEnrolledInEventException e) {
            return InputProcessResult.USER_NEVER_ENROLLED;
        } catch (NumberFormatException e) {
            return InputProcessResult.INVALID_INPUT;
        }
        return InputProcessResult.SUCCESS;
    }
}

