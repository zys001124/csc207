package controllers;

import entities.Event;
import exceptions.EventFullException;
import exceptions.EventNotFoundException;
import exceptions.InvalidUserTypeException;
import exceptions.UserAlreadyEnrolledException;
import holders.SceneNavigator;
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
 * A controller for handling inputs when the Attendee is enrolling
 * in an Event
 */
public class EventEnrollController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventListField"
    private ListView<Label> eventListView; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="enrollButton"
    private Button enrollButton; // Value injected by FXMLLoader

    @FXML // fx:id="enrollMessageLabel"
    private Label enrollMessageLabel; // Value injected by FXMLLoader

    @FXML
    void initialize() {
        assert eventListView != null : "fx:id=\"eventListView\" was not injected: check your FXML file 'Enroll Event.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Enroll Event.fxml'.";
        assert enrollButton != null : "fx:id=\"enrollButton\" was not injected: check your FXML file 'Enroll Event.fxml'.";
        assert enrollMessageLabel != null : "fx:id=\"enrollMessageLabel\" was not injected: check your FXML file 'Enroll Event.fxml'.";
    }

    @FXML
    void onBackButtonClicked() {
        setSceneView(SceneNavigator.SceneViewType.ATTENDEE_MAIN_MENU);
    }

    @FXML
    void onEnrollButtonClicked() {
        String eventTitle = eventListView.getSelectionModel().getSelectedItem().getText().split("  Time:")[0];
        String label;
        InputProcessResult result = enrollEvent(eventTitle);
        if (result == InputProcessResult.SUCCESS) {
            label = "Enroll Successful!";
        } else if (result == InputProcessResult.EVENT_NOT_FOUND) {
            label = "Event not found. Please try again.";
        } else if (result == InputProcessResult.EVENT_FOR_VIPONLY) {
            label = "Event is for VIP only and you are not a VIP. Please try again.";
        } else if (result == InputProcessResult.EVENT_IS_FULL) {
            label = "Event is currently full. Please try another event.";
        } else if (result == InputProcessResult.USER_ALREADY_ENROLLED) {
            label = "You are already enrolled in this event. Please try again.";
        } else {
            label = "Invalid Input. Please try again.";
        }
        enrollMessageLabel.setText(label);
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);
        setEventListField();
        eventManager.addObserver((o, changes, addedOrChanged, retrievedFromDatabase) -> setEventListField());
    }

    private void setEventListField() {
        //eventListView.getItems().clear();
        eventListView.getItems().setAll(getEventLabels(eventManager.getEvents()));
        eventListView.refresh();
    }

    private List<Label> getEventLabels(List<Event> events) {
        ArrayList<Label> labels = new ArrayList<>();
        for (Event event : events) {
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
        return labels;
    }

    /**
     * Handles the input given by the user
     *
     * @param eventInput The user input
     * @return An InputProcessResult enum that details what happened as a result of the given input
     */
    public InputProcessResult enrollEvent(String eventInput) {
        try {
            eventManager.addUserToEvent(eventInput, userManager.getCurrentlyLoggedIn());
        } catch (EventNotFoundException e) {
            return InputProcessResult.EVENT_NOT_FOUND;
        } catch (InvalidUserTypeException e) {
            return InputProcessResult.EVENT_FOR_VIPONLY;
        } catch (EventFullException e) {
            return InputProcessResult.EVENT_IS_FULL;
        } catch (NumberFormatException e) {
            return InputProcessResult.INVALID_INPUT;
        } catch (UserAlreadyEnrolledException e) {
            return InputProcessResult.USER_ALREADY_ENROLLED;
        }
        return InputProcessResult.SUCCESS;
    }

}

