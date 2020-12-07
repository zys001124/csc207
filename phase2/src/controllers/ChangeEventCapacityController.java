package controllers;

import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import useCaseClasses.EventManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A controller for handling inputs when the Organizer is changing
 * the capacity of  an Event
 */
public class ChangeEventCapacityController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventListField"
    private ListView<Label> eventListField; // Value injected by FXMLLoader

    @FXML // fx:id="capacityField"
    private TextField capacityField; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="changeButton"
    private Button changeButton; // Value injected by FXMLLoader

    @FXML // fx:id="createMessageLabel"
    private Label createMessageLabel; // Value injected by FXMLLoader

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        if (userManager.checkOrganizer(userManager.getCurrentlyLoggedIn())) {
            setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
        } else {
            setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU);
        }
    }

    @FXML
    void onChangeButtonClicked(ActionEvent event) {

        String label = "";
        Label eventLabel = eventListField.getSelectionModel().getSelectedItem();
        if (eventLabel == null) {
            label = "No event selected. Try again";
            createMessageLabel.setText(label);
            return;
        }
        String inter = eventLabel.getText().split("             Room")[0];
        String eventname = inter.split(": ")[1];
        String capacity = capacityField.getText();

        InputProcessResult result = changeEventCapacity(capacity, eventname);

        if (result == InputProcessResult.SUCCESS) {
            label = "Capacity change successful.";
        } else if (result == InputProcessResult.EVENT_DOES_NOT_EXIST) {
            label = "This event does not exist. Try again.";
        } else if (result == InputProcessResult.CAPACITY_OVER) {
            label = "This is over room capacity. Try again.";
        } else if (result == InputProcessResult.INVALID_INPUT) {
            label = "Invalid input. Try again.";
        }
        createMessageLabel.setText(label);
    }


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert capacityField != null : "fx:id=\"capacityField\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert changeButton != null : "fx:id=\"changeButton\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert createMessageLabel != null : "fx:id=\"createMessageLabel\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert eventListField != null : "fx:id=\"eventListField\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
    }

    private InputProcessResult changeEventCapacity(String c, String event) {
        if (checkIntFormat(c)) {
            return InputProcessResult.INVALID_INPUT;
        }
        int capacity = Integer.parseInt(c);
        if (capacity > 60) {
            return InputProcessResult.CAPACITY_OVER;
        }
        if (!eventManager.eventTitleExists(event)) {
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }
        eventManager.changeEventCapacity(event, capacity, false);
        return InputProcessResult.SUCCESS;
    }

    private boolean checkIntFormat(String capacity) {
        try {
            Integer.parseInt(capacity);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }


    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);

        setEventList();
        eventManager.addObserver((o, changes, addedOrChanged, retrievedFromDatabase) -> {
            setEventList();
        });
    }

    private List<Label> getEventLabels() {
        List<String> labels = eventManager.getEventCapacityLabels();
        ArrayList<Label> result = new ArrayList<>();
        for (String label : labels) {
            result.add(new Label(label));
        }
        return result;
    }

    private void setEventList() {
        eventListField.getItems().clear();
        eventListField.refresh();
        eventListField.getItems().setAll(getEventLabels());
        eventListField.refresh();
    }
}

