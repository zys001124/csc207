package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import useCaseClasses.EventManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * A controller for handling inputs when the Organizer or Admin is canceling
 * an Event
 */
public class EventCancelController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventListView"
    private ListView<Label> eventListView; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="createMessageLabel"
    private Label createMessageLabel; // Value injected by FXMLLoader

    /**
     * Initializes the scene buttons and labels and text boxes
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert eventListView != null : "fx:id=\"eventListView\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert createMessageLabel != null : "fx:id=\"createMessageLabel\" was not injected: check your FXML file 'Cancel Event.fxml'.";

    }

    /**
     * Method that sends the user back to their corresponding main menu
     */
    @FXML
    void onBackButtonClicked() {
        if (!checkUserType()) {
            setSceneView(SceneViewType.ADMIN_MAIN_MENU);
        }
        if (checkUserType()) {
            setSceneView(SceneViewType.ORGANIZER_MAIN_MENU);
        }
    }

    /**
     * Method that handles the program input when the change button is clicked and
     * cancel the event
     */
    @FXML
    void onCancelButtonClicked() {

        String label = "";

        String eventname = eventListView.getSelectionModel().getSelectedItem().getText().split(" on")[0];

        InputProcessResult result = cancelEvent(eventname);

        if (result == InputProcessResult.SUCCESS) {
            label = "Event canceled successfully.";
        } else if (result == InputProcessResult.EVENT_DOES_NOT_EXIST) {
            label = "This event does not exist. Try again.";
        } else if (result == InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT) {
            label = "This event is not organized by you. Try again.";
        }
        createMessageLabel.setText(label);
    }

    private InputProcessResult cancelEvent(String eventName) {
        if (!eventManager.eventTitleExists(eventName)) {
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }

        if (!eventManager.hasOrganizedEvent(userManager.getCurrentlyLoggedIn(), eventManager.getEvent(eventName)) &&
                !checkUserType()) {
            return InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT;
        }

        eventManager.removeEvent(eventManager.getEventId(eventManager.getEvent(eventName)));
        return InputProcessResult.SUCCESS;
    }

    private boolean checkUserType() {
        return userManager.checkOrganizer(userManager.getCurrentlyLoggedIn()); //False if Admin, True if Organizer
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
        eventManager.addObserver((o, changes, addedOrChanged) -> setEventList());
    }

    private List<Label> getEventLabels() {
        ArrayList<Label> result = new ArrayList<>();
        List<String> labels = eventManager.getEventCancelLabels();
        int i = 0;
        List<List<UUID>> speakers = eventManager.getSpeakersId();
        while (i < labels.size()) {
            String labelText = labels.get(i);
            for (int x = 0; x < speakers.get(i).size(); x++) {
                labelText = labelText + " " + userManager.getName(speakers.get(i).get(x));
                if (x != speakers.get(i).size() - 1) {
                    labelText = labelText + ",";
                }
            }

            result.add(new Label(labelText));
            i++;
        }

        return result;
    }

    private void setEventList() {
        eventListView.getItems().setAll(getEventLabels());
    }
}

