package controllers;

import entities.Event;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import useCaseClasses.EventManager;

import java.net.URL;
import java.util.*;

/**
 * The class controller for seeing the schedule of events a user that is
 * either hosting or attending
 */
public class SeeEventScheduleController extends Controller {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventListView"
    private ListView<Label> eventListView; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML
    private Label textBox;

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'See Event Schedule.fxml'.";
        assert eventListView != null : "fx:id=\"eventListView\" was not injected: check your FXML file 'See Event Schedule.fxml'.";
        assert textBox != null : "fx:id=\"textBox\" was not injected: check your FXML file 'See Event Schedule.fxml'.";
    }

    /**
     * Takes the user back to their corresponding main menu
     */
    @FXML
    void onBackButtonClicked() {
        if (userManager.getCurrentlyLoggedIn().getType().equals(User.UserType.ATTENDEE) ||
                userManager.getCurrentlyLoggedIn().getType().equals(User.UserType.VIP)) {
            setSceneView(SceneViewType.ATTENDEE_MAIN_MENU);
        } else { //They are a speaker
            setSceneView(SceneViewType.SPEAKER_MAIN_MENU);
        }

    }

    /**
     * Sets the event manager for this system
     *
     * @param eventManager the eventManager to be set to
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);
        if (userManager.getCurrentlyLoggedIn().getType().equals(User.UserType.SPEAKER)) {
            textBox.setText("Here is the list of events you are hosting:");
        }

        setEventListField();
        eventManager.addObserver((o, changes, addedOrChanged) -> setEventListField());
    }

    /**
     * Auto update for the event list to be shown on the scene
     */
    private void setEventListField() {
        eventListView.getItems().clear();
        if (userManager.getCurrentlyLoggedIn().getType().equals(User.UserType.ATTENDEE) ||
                userManager.getCurrentlyLoggedIn().getType().equals(User.UserType.VIP)) {
            List<Event> events = eventManager.sortedEventsWithAttendees(userManager.getCurrentlyLoggedIn());
            eventListView.getItems().addAll(getEventLabelsAttendee(events));
        } else { //They are a speaker
            UUID currentUserId = userManager.getCurrentlyLoggedIn().getId();
            List<Event> events = eventManager.getEventsWithSpeaker(currentUserId);
            eventListView.getItems().addAll(getEventLabelsSpeaker(events));
        }
        eventListView.refresh();
    }

    /**
     * Gets the event labels to be shown on the scene scroll view
     *
     * @param events the events the user is either hosting or attending
     * @return List of labels for the scene
     */
    private List<Label> getEventLabelsAttendee(List<Event> events) {
        ArrayList<Label> labels = new ArrayList<>();
        for (Event event : events) {
            String labelText = eventManager.getIndividualTitle(event)
                    + "  Time: " + eventManager.getIndividualTime(event, "yyyy-MM-dd HH:mm:ss")
                    + "  Room: " + eventManager.getIndividualRoom(event)
                    + "  Event Type: " + eventManager.getIndividualType(event);
            Label label = new Label(labelText);
            labels.add(label);
        }
        return labels;
    }

    /**
     * Gets the label for the speaker hosting the event
     *
     * @param events the events to see which speaker(s) are hosting
     * @return List of speaker labels
     */
    private List<Label> getEventLabelsSpeaker(Collection<Event> events) {
        ArrayList<Label> labels = new ArrayList<>();
        for (Event event : events) {
            String labelText = eventManager.getIndividualTitle(event) + " on "
                    + eventManager.getIndividualTime(event, "MMMM dd, HH:mm") + ".";
            Label eventLabel = new Label(labelText);
            labels.add(eventLabel);
        }
        return labels;
    }
}
