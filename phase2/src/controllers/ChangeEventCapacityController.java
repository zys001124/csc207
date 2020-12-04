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

    @FXML // fx:id="timeField"
    private TextField timeField; // Value injected by FXMLLoader

    @FXML // fx:id="eventNameField"
    private TextField eventNameField; // Value injected by FXMLLoader

    @FXML // fx:id="roomNameField"
    private TextField roomNameField; // Value injected by FXMLLoader

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

        String time = timeField.getText();
        String room = roomNameField.getText();
        String capacity = capacityField.getText();
        String eventName = eventNameField.getText();

        InputProcessResult result = changeEventCapacity(time, room, capacity, eventName);

        if (result == InputProcessResult.SUCCESS) {
            label = "Capacity change successful.";
        } else if (result == InputProcessResult.EVENT_DOES_NOT_EXIST) {
            label = "This event does not exist. Try again.";
        } else if (result == InputProcessResult.INVALID_TIME){
            label = "Time is in the wrong format. Try again.";
        } else if (result == InputProcessResult.INVALID_INPUT){
            label = "Invalid input. Try again.";
        } else if (result == InputProcessResult.WRONG_ROOM){
            label = "This room is wrong. Try again.";
        } else if (result == InputProcessResult.CAPACITY_OVER) {
            label = "This event is over room capacity. Try again.";
        }
        createMessageLabel.setText(label);
    }


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert timeField != null : "fx:id=\"timeField\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert capacityField != null : "fx:id=\"capacityField\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert roomNameField != null : "fx:id=\"roomNameField\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert eventNameField != null : "fx:id=\"eventNameField\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert changeButton != null : "fx:id=\"changeButton\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert createMessageLabel != null : "fx:id=\"createMessageLabel\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
        assert eventListField != null : "fx:id=\"eventListField\" was not injected: check your FXML file 'Change Event Capacity.fxml'.";
    }

    private InputProcessResult changeEventCapacity(String time, String r, String c, String event) {
        if(time.equals("") || r.equals("") || c.equals("") || event.equals("")){
            return InputProcessResult.INVALID_INPUT;
        }

        if(checkIntFormat(r,c)){
            return InputProcessResult.INVALID_INPUT;
        }
        int room = Integer.parseInt(r);
        int capacity = Integer.parseInt(c);
        if (capacity > 60){
            return InputProcessResult.CAPACITY_OVER;
        }
        if(!eventManager.eventTitleExists(event)){
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }
        Event currentEvent = eventManager.getEvent(event);
        if(!checkDateFormat(time)){
            return InputProcessResult.INVALID_TIME;
        }
        LocalDateTime sDateTime = getLocalDateTime(time);
        if(room<0 || room>5){
            return InputProcessResult.WRONG_ROOM;
        }

        if(currentEvent.getEventRoom() == room && !currentEvent.getEventTime().equals(sDateTime)){
            return InputProcessResult.INVALID_TIME;
        }
        if(currentEvent.getEventRoom() != room && currentEvent.getEventTime().equals(sDateTime)){
            return InputProcessResult.WRONG_ROOM;
        }
        if(currentEvent.getEventRoom() != room && !currentEvent.getEventTime().equals(sDateTime)){
            return InputProcessResult.INVALID_INPUT;
        }

        currentEvent.setEventCapacity(capacity);
        return InputProcessResult.SUCCESS;
    }

    private LocalDateTime getLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(time,formatter);
    }

    private boolean checkDateFormat(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            formatter.parse(time.trim());
        } catch (DateTimeParseException pe) {
            return false;
        }
        return true;
    }

    private boolean checkIntFormat(String room, String capacity){
        try{
            Integer.parseInt(room);
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
