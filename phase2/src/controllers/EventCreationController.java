/**
 * Sample Skeleton for 'Create Event.fxml' Controller Class
 */

package controllers;

import entities.Event;
import entities.User;
import exceptions.UserNotFoundException;
import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;


public class EventCreationController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventTitleField"
    private TextField eventTitleField; // Value injected by FXMLLoader

    @FXML // fx:id="startTimeField"
    private TextField startTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="endTimeField"
    private TextField endTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="speakerUsernamesField"
    private TextField speakerUsernamesField; // Value injected by FXMLLoader

    @FXML // fx:id="roomNumberField"
    private TextField roomNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="eventCapacityField"
    private TextField eventCapacityField; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="createMessageLabel"
    private Label createMessageLabel; // Value injected by FXMLLoader

    @FXML // fx:id="vipOnlyCheck"
    private CheckBox vipOnlyCheck; // Value injected by FXMLLoader

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
    }

    @FXML
    void onCreateButtonClicked(ActionEvent event) {

        String label = "";

        String eventTitle = eventTitleField.getText();
        LocalDateTime sDateTime = getLocalDateTime(startTimeField.getText());
        LocalDateTime eDateTime = getLocalDateTime(endTimeField.getText());
        String[] speakersUserName;
        if(!speakerUsernamesField.getText().equals("")){
            speakersUserName = speakerUsernamesField.getText().split(":");
        }
        else{
            speakersUserName = new String[0];
        }
        int roomNum = Integer.parseInt(roomNumberField.getText());
        int capacity = Integer.parseInt(eventCapacityField.getText());
        Boolean vipSelected = vipOnlyCheck.isSelected();

        InputProcessResult result = createEvent(eventTitle, sDateTime, eDateTime, speakersUserName, roomNum,
                capacity, vipSelected);

        if (result == InputProcessResult.USER_NOT_FOUND) {
            label = "At least one of the speakers could not be found.";
        } else if (result == InputProcessResult.USER_NOT_SPEAKER) {
            label = "At least one of the speakers is not a speaker.";
        } else if(result == InputProcessResult.EVENT_NAME_TAKEN){
            label = "That event name is taken.";
        } else if (result == InputProcessResult.TIMESLOT_FULL) {
            label = "This room is occupied at this time.";
        } else if (result == InputProcessResult.SPEAKER_OCCUPIED) {
            label = "At least one of the speakers is occupied.";
        } else if (result == InputProcessResult.ROOM_FULL) {
            label = "That room is full.";
        } else if (result == InputProcessResult.CAPACITY_OVER){
            label = "The capacity can't be over 60.";
        } else {
            label = "Event created successfully.";
        }

        createMessageLabel.setText(label);
    }

    @FXML
    void onVipOnlyCheck(ActionEvent event) {
        if (vipOnlyCheck.isSelected()) {
            createMessageLabel.setText("Vip only selected.");
        } else {
            createMessageLabel.setText("Vip only de-selected.");
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert eventTitleField != null : "fx:id=\"eventTitleField\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert startTimeField != null : "fx:id=\"startTimeField\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert endTimeField != null : "fx:id=\"endTimeField\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert speakerUsernamesField != null : "fx:id=\"speakerUsernamesField\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert roomNumberField != null : "fx:id=\"roomNumberField\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert eventCapacityField != null : "fx:id=\"eventCapacityField\" was not injected: check your FXML file 'Create Event.fxml'.";
        //assert vipYesButton != null : "fx:id=\"vipYesButton\" was not injected: check your FXML file 'Create Event.fxml'.";
        //assert vipNoButton != null : "fx:id=\"vipNoButton\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert createMessageLabel != null : "fx:id=\"createMessageLabel\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert vipOnlyCheck != null : "fx:id=\"vipOnlyCheck\" was not injected: check your FXML file 'Create Event.fxml'.";

    }

    public InputProcessResult createEvent(String eventTitle, LocalDateTime startTime, LocalDateTime endTime, String[] speakersUserName,
                                          int roomNum, int roomCapacity, Boolean vip) {

        UUID eventID = getUuid();
        ArrayList<User> speakers = new ArrayList<>();


        try {
            for (String speaker : speakersUserName) {
                userManager.getUser(speaker);
                speakers.add(userManager.getUser(speaker));
            }
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        for (User speaker : speakers) {
            if (!(speaker.getType().equals(User.UserType.SPEAKER))) {
                return InputProcessResult.USER_NOT_SPEAKER;
            }
        }

        for(Event event : eventManager.getEvents()){
            if(event.getEventTitle().equals(eventTitle)){
                return InputProcessResult.EVENT_NAME_TAKEN;
            }
        }

        ArrayList<UUID> speakersID = getSpeakersID(speakers);
        UUID organizerID = userManager.getCurrentlyLoggedIn().getId();


        Boolean occupiedRoom = eventManager.availabilityInTime(startTime, endTime, roomNum);
        if (occupiedRoom) {
            return InputProcessResult.TIMESLOT_FULL;
        }

        for (User speaker : speakers) {
            boolean speakerOccupied = speakerOccupied(startTime, endTime, speaker);
            if (speakerOccupied) {
                return InputProcessResult.SPEAKER_OCCUPIED;
            }
        }

//        if (occupiedRoom.contains(roomNum)) {
//            return InputProcessResult.ROOM_FULL;
//        }

        if(roomCapacity > 60){
            return InputProcessResult.CAPACITY_OVER;
        }

        Event eventCreated = new Event(eventTitle, startTime, endTime, eventID, organizerID, speakersID,
                new ArrayList<>(), roomNum, roomCapacity, vip);


        eventManager.addEvent(eventCreated);
        return InputProcessResult.SUCCESS;

    }

    private LocalDateTime getLocalDateTime(String parameter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        formatter = formatter.withZone(ZoneId.of("UTC-5"));
        return LocalDateTime.parse(parameter, formatter);
    }

    private UUID getUuid() {
        ArrayList<UUID> eID = new ArrayList<>();
        ArrayList<UUID> uID = new ArrayList<>();
        for (Event e : eventManager.getEvents()) {
            eID.add(e.getId());
        }
        for (User u : userManager.getUsers()) {
            uID.add(u.getId());
        }

        boolean uuidExist = true;
        UUID eventID = UUID.randomUUID();
        while (uuidExist) {
            if (!(eID.contains(eventID) || uID.contains(eventID))) {
                uuidExist = false;
            } else {
                eventID = UUID.randomUUID();
            }
        }
        return eventID;
    }

    private ArrayList<UUID> getSpeakersID(ArrayList<User> speakers) {
        ArrayList<UUID> speakersID = new ArrayList<>();
        for (User speaker : speakers) {
            speakersID.add(speaker.getId());
        }
        return speakersID;
    }

    private boolean speakerOccupied(LocalDateTime sDateTime, LocalDateTime eDateTime, User speaker) {
        for (String e : eventManager.listOfEventsHosting(speaker)) {
            Event eventHosting = eventManager.getEvent(e);
            LocalDateTime startTime = eventHosting.getEventTime();
            LocalDateTime endTime = eventHosting.getEventETime();

            if (sDateTime.isBefore(startTime) && eDateTime.isAfter(startTime) |
                    (sDateTime.isBefore(endTime) && eDateTime.isAfter(endTime)) |
                    (sDateTime.isAfter(startTime) && eDateTime.isBefore(endTime))
            ) {
                return true;
            }
        }
        return false;
    }
}
//public class EventCreationController {
//
//    private final EventManager eManager;
//    private final UserManager uManager;
//
//    /**
//     * Creates a EventCreationController with the given UserManager and EventManager
//     *
//     * @param manager  - The EventManager this controller will use
//     * @param uManager - The UserManager this controller will use
//     */
//    public EventCreationController(EventManager manager, UserManager uManager) {
//        eManager = manager;
//        this.uManager = uManager;
//    }
//
//    /**
//     * Handles the input given by the user
//     *
//     * @param input the users input of string that
//     *              include information of eventTitle, Date, speakerUsername, and RoomNum
//     * @return an InputProcessResult enum that details what happened
//     * as a result of the given input
//     * <p>
//     * Precondition: the roomNum is between 0 to 5.
//     */
//    public InputProcessResult createEvent(String input){
//        if (input.equals("back")) {
//            return InputProcessResult.BACK;
//        }
//
//        String[] parametersForEvent = input.split(",");
//        if(parametersForEvent.length !=7) {
//            return InputProcessResult.INVALID_INPUT;
//        }
//
//        UUID eventID = getUuid();
//
//        String[] speakersUserName = parametersForEvent[3].split(":");
//        ArrayList<User> speakers = new ArrayList<>();
//
//        try{
//            for (String speaker:speakersUserName){
//                uManager.getUser(speaker);
//                speakers.add(uManager.getUser(speaker));
//            }
//        }catch(UserNotFoundException e) {
//            return InputProcessResult.USER_NOT_FOUND;
//        }
//
//        for (User speaker:speakers){
//            if (!(speaker.getType().equals(User.UserType.SPEAKER))) {
//                return InputProcessResult.USER_NOT_SPEAKER;
//            }
//        }
//
//        ArrayList<UUID> speakersID = getSpeakersID(speakers);
//        UUID organizerID = uManager.getCurrentlyLoggedIn().getId();
//
//        LocalDateTime sDateTime = getLocalDateTime(parametersForEvent[1]);
//        LocalDateTime eDateTime = getLocalDateTime(parametersForEvent[2]);
//
//        ArrayList<Integer> occupiedRoom = eManager.availabilityInTime(sDateTime, eDateTime);
//        if (6 == occupiedRoom.size()) {
//            return InputProcessResult.TIMESLOT_FULL;
//        }
//
//        for (User speaker:speakers){
//            boolean speakerOccupied = speakerOccupied(sDateTime, eDateTime, speaker);
//            if (speakerOccupied){
//                return InputProcessResult.SPEAKER_OCCUPIED;
//            }
//        }
//
//        int roomNum = Integer.parseInt(parametersForEvent[4]);
//
//        if (occupiedRoom.contains(roomNum)){return InputProcessResult.ROOM_FULL;}
//        int capacity = Integer.parseInt(parametersForEvent[5]);
//        if(VIPonlytypo(parametersForEvent[6])){
//            return InputProcessResult.INVALID_INPUT;
//        }
//        boolean VIPonly = Boolean.parseBoolean(parametersForEvent[6]);
//
//        int roomCapacity = 60;
//        if (capacity > roomCapacity){
//            return InputProcessResult.CAPACITY_OVER;
//        }
//        Event eventCreated = new Event(parametersForEvent[0], sDateTime, eDateTime, eventID, organizerID, speakersID,
//                new ArrayList<>(), roomNum, capacity, VIPonly);
//
//
//        eManager.addEvent(eventCreated);
//        return InputProcessResult.SUCCESS;
//
//    }
//
//    private LocalDateTime getLocalDateTime(String parameter) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        formatter = formatter.withZone(ZoneId.of("UTC-5"));
//        return LocalDateTime.parse(parameter, formatter);
//    }
//
//    private boolean VIPonlytypo(String b){
//        return !b.equals("true") && !b.equals("false");
//    }
//
//    private boolean speakerOccupied(LocalDateTime sDateTime, LocalDateTime eDateTime, User speaker) {
//        for (String e : eManager.listOfEventsHosting(speaker)) {
//            Event eventHosting = eManager.getEvent(e);
//            LocalDateTime startTime = eventHosting.getEventTime();
//            LocalDateTime endTime = eventHosting.getEventETime();
//
//            if (sDateTime.isBefore(startTime) && eDateTime.isAfter(startTime)|
//                    (sDateTime.isBefore(endTime) && eDateTime.isAfter(endTime))|
//                    (sDateTime.isAfter(startTime) && eDateTime.isBefore(endTime))
//            ){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private UUID getUuid() {
//        ArrayList<UUID> eID = new ArrayList<>();
//        ArrayList<UUID> uID = new ArrayList<>();
//        for (Event e : eManager.getEvents()) {
//            eID.add(e.getId());
//        }
//        for (User u : uManager.getUsers()) {
//            uID.add(u.getId());
//        }
//
//        boolean uuidExist = true;
//        UUID eventID = UUID.randomUUID();
//        while (uuidExist) {
//            if (!(eID.contains(eventID) || uID.contains(eventID))) {
//                uuidExist = false;
//            } else {
//                eventID = UUID.randomUUID();
//            }
//        }
//        return eventID;
//    }
//
//    private ArrayList<UUID> getSpeakersID(ArrayList<User> speakers) {
//        ArrayList<UUID> speakersID = new ArrayList<>();
//        for (User speaker : speakers) {
//            speakersID.add(speaker.getId());
//        }
//        return speakersID;
//    }
//}
