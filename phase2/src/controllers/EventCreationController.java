package controllers;

import entities.Event;
import entities.User;
import exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
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
            setSceneView(SceneViewType.ORGANIZER_MAIN_MENU);
        } else if (currentUserType == User.UserType.ADMIN) {
            setSceneView(SceneViewType.ADMIN_MAIN_MENU);
        }
    }

    /**
     * Method that runs when user clicks the button to create a new event.
     * Will put a label on the scene on if the event is created or not and what went wrong if an
     * exception is found.
     */
    @FXML
    void onCreateButtonClicked() {

        String label;

        String eventTitle = eventTitleField.getText();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();

        String[] speakersUserName;
        if (!speakerUsernamesField.getText().equals("")) {
            speakersUserName = speakerUsernamesField.getText().split(":");
        } else {
            speakersUserName = new String[0];
        }
        int roomNum = Integer.parseInt(roomNumberField.getText());
        int capacity = Integer.parseInt(eventCapacityField.getText());
        Boolean vipSelected = vipOnlyCheck.isSelected();


        InputProcessResult result = createEvent(eventTitle, startTime, endTime, speakersUserName, roomNum,
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
        } else if (result == InputProcessResult.INVALID_TIME) {
            label = "End time is before start time.";
        } else if (result == InputProcessResult.INVALID_TIME_INPUT) {
            label = "Start time or end time input valid.";
        } else if (result == InputProcessResult.INVALID_INPUT) {
            label = "At least one of your inputs is incorrect. Please try again.";
        } else {
            label = "Event created successfully.";
        }


        createMessageLabel.setText(label);
    }

    /**
     * Button that sets a variable to decide if the event to be made is VIP only or not.
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
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert createButton != null : "fx:id=\"createButton\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert createMessageLabel != null : "fx:id=\"createMessageLabel\" was not injected: check your FXML file 'Create Event.fxml'.";
        assert vipOnlyCheck != null : "fx:id=\"vipOnlyCheck\" was not injected: check your FXML file 'Create Event.fxml'.";

    }

    /**
     * Helper method for the create button when clicked. Used to return an input process result to help
     * the scene display the right text
     * This Method is long but it has to be this way because there are so many checks when creating an event
     * Adding more helper methods to try and make the method shorter will just make the class look more messy
     * So we kept the method the way it is.
     *
     * @param eventTitle       the event title for this event creation
     * @param sTime            the start time for this event creation
     * @param eTime            the end time for this event creation
     * @param speakersUserName the list of speakers for this event
     * @param roomNum          the room number for this event
     * @param roomCapacity     the capacity for this event
     * @param vip              boolean on if this event should be VIP only
     * @return an InputProcessResult to help the method decide what the textbox should say
     */
    private InputProcessResult createEvent(String eventTitle, String sTime, String eTime, String[] speakersUserName,
                                           int roomNum, int roomCapacity, Boolean vip) {

        try {
            Event.EventData eventData = new Event.EventData();
            eventData.eventTitle = eventTitle;
            eventData.eventSTime = sTime.replace(" ", "T"); // For date time formatting
            eventData.eventETime = eTime.replace(" ", "T"); // For date time formatting
            eventData.speakerIds = getSpeakerIdStrings(speakersUserName);
            eventData.attendees = new ArrayList<>();
            eventData.eventRoom = String.valueOf(roomNum);
            eventData.eventCapacity = String.valueOf(roomCapacity);
            eventData.VIPonly = String.valueOf(vip);
            eventData.eventId = getUuid().toString();
            eventData.organizerId = userManager.getCurrentlyLoggedIn().getId().toString();

            eventManager.addEvent(Event.fromEventData(eventData));
        } catch (Exception e) {
            return parseException(e);
        }


        return InputProcessResult.SUCCESS;
    }

    private InputProcessResult parseException(Exception e) {

        if (e instanceof UserNotFoundException) {
            return InputProcessResult.USER_NOT_FOUND;
        } else if (e instanceof DateTimeException) {
            return InputProcessResult.INVALID_TIME_INPUT;
        } else if (e instanceof EventNameTakenException) {
            return InputProcessResult.EVENT_NAME_TAKEN;
        } else if (e instanceof InvalidEventTimeRangeException) {
            return InputProcessResult.INVALID_TIME;
        } else if (e instanceof EventBookingOverlapException) {
            return InputProcessResult.TIMESLOT_FULL;
        } else if (e instanceof SpeakerOccupiedException) {
            return InputProcessResult.SPEAKER_OCCUPIED;
        } else if (e instanceof EventCapacityExceedsRoomCapacityException) {
            return InputProcessResult.CAPACITY_OVER;
        } else if (e instanceof InvalidUserTypeException) {
            return InputProcessResult.USER_NOT_SPEAKER;
        } else {
            return InputProcessResult.INVALID_INPUT;
        }
    }

    private List<String> getSpeakerIdStrings(String[] usernames) throws UserNotFoundException, InvalidUserTypeException {
        List<String> speakers = new ArrayList<>();

        for (String speaker : usernames) {
            userManager.getUser(speaker);
            User user = userManager.getUser(speaker);
            if (!user.getType().equals(User.UserType.SPEAKER)) {
                throw new InvalidUserTypeException(user.getType());
            }
            speakers.add(user.getId().toString());
        }

        return speakers;
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
}

