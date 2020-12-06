package controllers;

import entities.Event;
import useCaseClasses.EventManager;
import exceptions.EventNotFoundException;
import exceptions.UserNotEnrolledInEventException;
import handlers.SceneNavigator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A controller for handling inputs when the Attendee is un-enrolling
 * from an Event
 */
public class EventUnEnrollController extends Controller{

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

    @FXML
    void initialize() {
        assert eventListView != null : "fx:id=\"eventListView\" was not injected: check your FXML file 'Unenroll Event.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Unenroll Event.fxml'.";
        assert unEnrollButton != null : "fx:id=\"unEnrollButton\" was not injected: check your FXML file 'Unenroll Event.fxml'.";
        assert unEnrollMessageLabel != null : "fx:id=\"unEnrollMessageLabel\" was not injected: check your FXML file 'Unenroll Event.fxml'.";
    }

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.ATTENDEE_MAIN_MENU);
    }

    @FXML
    void onUnEnrollButtonClicked(ActionEvent event){
        String eventTitle = eventListView.getSelectionModel().getSelectedItem().getText().split("  Time: ")[0];
        String label;
        InputProcessResult result = unEnrollEvent(eventTitle);
        if (result == InputProcessResult.SUCCESS){
            label = "Un-Enroll Successful!";
        } else if (result == InputProcessResult.EVENT_NOT_FOUND){
            label = "Event not found. Please try again.";
        } else {
            label = "You are not enrolled in this event. Please try again.";
        }
        unEnrollMessageLabel.setText(label);
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);
        setEventListField();
    }

    private void setEventListField() {
        eventListView.getItems().addAll(getEventLabels(eventManager.getEvents()));
    }

    private List<Label> getEventLabels(Collection<Event> events) {
        ArrayList<Label> labels = new ArrayList<>();
        for(Event event: events){
            if(event.hasAttendee(userManager.getCurrentlyLoggedIn().getId())){
                String labelText = event.getEventTitle()
                        + "  Time: " + event.getEventTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + "  Room: " + event.getEventRoom()
                        + "  Capacity: " + event.getEventCapacity()
                        + "  Currently Enrolled: " + event.getEventEnrolledNumber()
                        + "  Event Type: " + event.getEventType()
                        + "  VIP only: " + event.getViponly();
                Label label = new Label(labelText);
                labels.add(label);
            }
        }
        return labels;
    }

    /**
     * Handles the input given by the user
     *
     * @param eventInput The user input
     * @return An InputProcessResult enum that details what happened as a result of the given input
     */
    public InputProcessResult unEnrollEvent(String eventInput) {
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

