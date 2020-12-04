package controllers;

import entities.Event;
import handlers.SceneNavigator;
import useCaseClasses.EventManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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
        setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
    }

    @FXML
    void onChangeButtonClicked(ActionEvent event) {

        String label = "";
        String inter = eventListField.getSelectionModel().getSelectedItem().getText().split("             Room")[0];
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
        if(checkIntFormat(c)){
            return InputProcessResult.INVALID_INPUT;
        }
        int capacity = Integer.parseInt(c);
        if (capacity > 60){
            return InputProcessResult.CAPACITY_OVER;
        }
        if(!eventManager.eventTitleExists(event)){
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }
        Event currentEvent = eventManager.getEvent(event);

        currentEvent.setEventCapacity(capacity);
        return InputProcessResult.SUCCESS;
    }

    private boolean checkIntFormat(String capacity){
        try{
            Integer.parseInt(capacity);
        }catch (NumberFormatException e) {
            return true;
        }
        return false;
    }


    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);

        setEventList();
    }

    private List<Label> getEventLabels(Collection<Event> events) {
        ArrayList<Label> labels = new ArrayList<>();
        for (Event event : events) {
            int room = event.getEventRoom();
            int capacity = event.getEventCapacity();
            labels.add(new Label("Event: " + event.getEventTitle()+"             Room: "
                    + Integer.toString(room) + "             Current Capacity: " + Integer.toString(capacity)));
        }
        return labels;
    }

    private void setEventList() {
        eventListField.getItems().addAll(getEventLabels(eventManager.getEvents()));
    }
}


//public class ChangeEventCapacityController {
//
//    private final EventManager eManager;
//
//
//    public ChangeEventCapacityController(EventManager manager) {
//        eManager = manager;
//    }
//
//    public InputProcessResult eventCapacityChange(String input){
//        if (input.equals("back")) {
//            return InputProcessResult.BACK;
//        }
//        String[] parameters = input.split(",");
//
//
//        int newCapacity = Integer.parseInt(parameters[2]);
//        if (newCapacity > 60){
//            return InputProcessResult.CAPACITY_OVER;
//        }
//
//        LocalDateTime sDateTime = getLocalDateTime(parameters[0]);
//
//        int roomNum = Integer.parseInt(parameters[1]);
//
//        for (Event event:eManager.getEvents()){
//            if (event.getEventRoom() == roomNum){
//                if (event.getEventTime().equals(sDateTime)){
//                    event.setEventCapacity(newCapacity);
//                    return InputProcessResult.SUCCESS;
//                }
//            }
//        }
//
//        return InputProcessResult.EVENT_DOES_NOT_EXIST;
//
//    }
//
//    private LocalDateTime getLocalDateTime(String parameter) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return LocalDateTime.parse(parameter, formatter);
//    }
//}
