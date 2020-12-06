package controllers;

import useCaseClasses.EventManager;
import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SeeEventScheduleController extends Controller{
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventListView"
    private ListView<Label> eventListView; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'See Event Schedule.fxml'.";
        assert eventListView != null: "fx:id=\"eventListView\" was not injected: check your FXML file 'See Event Schedule.fxml'.";
    }

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.ATTENDEE_MAIN_MENU);
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);
        setEventListField();
    }

    private void setEventListField() {
        eventListView.getItems().addAll(getEventLabels());
    }

    private List<Label> getEventLabels() {
        ArrayList<Label> labels = new ArrayList<>();
        for(int i=0; i<eventManager.sortedEventsWithAttendees(userManager.getCurrentlyLoggedIn()).size(); i++){
            String labelText = eventManager.getEventAttribute(i,
                    eventManager.sortedEventsWithAttendees(userManager.getCurrentlyLoggedIn()), "Title")
                    + "  Time: " + eventManager.getEventAttribute(i,
                    eventManager.sortedEventsWithAttendees(userManager.getCurrentlyLoggedIn()),"Time")
                    + "  Room: " + eventManager.getEventAttribute(i,
                    eventManager.sortedEventsWithAttendees(userManager.getCurrentlyLoggedIn()),"Room")
                    + "  Event Type: " + eventManager.getEventAttribute(i,
                    eventManager.sortedEventsWithAttendees(userManager.getCurrentlyLoggedIn()),"Type");
            Label label = new Label(labelText);
            labels.add(label);
        }
        return labels;
    }
}
