/**
 * Sample Skeleton for 'Create Event.fxml' Controller Class
 */

package controllers;

import entities.Event;
import entities.User;
import exceptions.UserNotFoundException;
import handlers.SceneNavigator;
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

/**
 * Controller class that handles the input for the event creation scene that the user puts in to create
 * a new event
 */
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

    /**
     * Method that returns the user to their corresponding main menu scene
     */
    @FXML
    void onBackButtonClicked() {
        User.UserType currentUserType = userManager.getCurrentlyLoggedIn().getType();
        if (currentUserType == User.UserType.ORGANIZER) {
            setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
        } else if (currentUserType == User.UserType.ADMIN) {
            setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU);
        }
    }

    /**
     * Method that runs when user clicks the button to create a new event.
     * Will put a label on the scene on if the event is created or not and what went wrong if an
     * exception is found.
     *
     * @param event
     */
    @FXML
    void onCreateButtonClicked() {

        String label = "";

        String eventTitle = eventTitleField.getText();
        LocalDateTime sDateTime = getLocalDateTime(startTimeField.getText());
        LocalDateTime eDateTime = getLocalDateTime(endTimeField.getText());
        String[] speakersUserName;
        if (!speakerUsernamesField.getText().equals("")) {
            speakersUserName = speakerUsernamesField.getText().split(":");
        } else {
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
        } else if (result == InputProcessResult.EVENT_NAME_TAKEN) {
            label = "That event name is taken.";
        } else if (result == InputProcessResult.TIMESLOT_FULL) {
            label = "This room is occupied at this time.";
        } else if (result == InputProcessResult.SPEAKER_OCCUPIED) {
            label = "At least one of the speakers is occupied.";
        } else if (result == InputProcessResult.ROOM_FULL) {
            label = "That room is full.";
        } else if (result == InputProcessResult.CAPACITY_OVER) {
            label = "The capacity can't be over 60.";
        } else {
            label = "Event created successfully.";
        }

        createMessageLabel.setText(label);
    }

    /**
     * Button that sets a variable to decide if the event to be made is VIP only or not.
     *
     * @param event
     */
    @FXML
    void onVipOnlyCheck() {
        if (vipOnlyCheck.isSelected()) {
            createMessageLabel.setText("Vip only selected.");
        } else {
            createMessageLabel.setText("Vip only de-selected.");
        }
    }

    /**
     * Initializes the input fields for this controller
     */
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

    /**
     * Helper method for the create button when clicked. Used to return an input process result to help
     * the scene display the right text
     *
     * @param eventTitle       the event title for this event creation
     * @param startTime        the start time for this event creation
     * @param endTime          the end time for this event creation
     * @param speakersUserName the list of speakers for this event
     * @param roomNum          the room number for this event
     * @param roomCapacity     the capacity for this event
     * @param vip              boolean on if this event should be VIP only
     * @return an InputProcessResult to help the method decide what the textbox should say
     */
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

        for (Event event : eventManager.getEvents()) {
            if (event.getEventTitle().equals(eventTitle)) {
                return InputProcessResult.EVENT_NAME_TAKEN;
            }
        }

        ArrayList<UUID> speakersID = userManager.listOfID(speakers);
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

        if (roomCapacity > 60) {
            return InputProcessResult.CAPACITY_OVER;
        }

        Event eventCreated = new Event(eventTitle, startTime, endTime, eventID, organizerID, speakersID,
                new ArrayList<>(), roomNum, roomCapacity, vip);


        eventManager.addEvent(eventCreated);
        return InputProcessResult.SUCCESS;

    }

    /**
     * Helper method that gets the local date time of the input put into the time slot
     *
     * @param parameter the string value of the what the user put in the textbox
     * @return the LocalDateTime of the string input
     */
    private LocalDateTime getLocalDateTime(String parameter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        formatter = formatter.withZone(ZoneId.of("UTC-5"));
        return LocalDateTime.parse(parameter, formatter);
    }

    /**
     * Helper method that generates a UUID for this event
     *
     * @return a UUID for this event
     */
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


    /**
     * Helper method that determins if the speaker given is occupied during the given start time and
     * end time
     *
     * @param sDateTime the start time to be checked for the speaker
     * @param eDateTime the end time to be checked for the speaker
     * @param speaker   the speaker to be checked if bust
     * @return a boolean TRUE if occupied during timeframe. FALSE otherwise
     */
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

