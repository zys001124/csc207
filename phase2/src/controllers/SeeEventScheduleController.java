package controllers;

import entities.Event;
import entities.User;
import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import useCaseClasses.EventManager;

import java.net.URL;
import java.util.*;

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

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        if (userManager.getCurrentlyLoggedIn().getType().equals(User.UserType.ATTENDEE) ||
                userManager.getCurrentlyLoggedIn().getType().equals(User.UserType.VIP)) {
            setSceneView(SceneNavigator.SceneViewType.ATTENDEE_MAIN_MENU);
        } else { //They are a speaker
            setSceneView(SceneNavigator.SceneViewType.SPEAKER_MAIN_MENU);
        }

    }

    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);
        if (userManager.getCurrentlyLoggedIn().getType().equals(User.UserType.SPEAKER)) {
            textBox.setText("Here is the list of events you are hosting:");
        }

        setEventListField();
        eventManager.addObserver((o, changes, addedOrChanged, retrievedFromDatabase) -> setEventListField());
    }

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
