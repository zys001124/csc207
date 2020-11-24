package controllers;

import entities.Event;
import entities.User;
import exceptions.UserNotFoundException;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

/***
 * A controller for handling input when an Organizer is
 * creating an Event.
 */
public class EventCreationController {

    private final EventManager eManager;
    private final UserManager uManager;

    /**
     * Creates a EventCreationController with the given UserManager and EventManager
     *
     * @param manager  - The EventManager this controller will use
     * @param uManager - The UserManager this controller will use
     */
    public EventCreationController(EventManager manager, UserManager uManager) {
        eManager = manager;
        this.uManager = uManager;
    }

    /**
     * Handles the input given by the user
     *
     * @param input the users input of string that
     *              include information of eventTitle, Date, speakerUsername, and RoomNum
     * @return an InputProcessResult enum that details what happened
     * as a result of the given input
     * <p>
     * Precondition: the roomNum is between 0 to 5.
     */
    public InputProcessResult createEvent(String input){
        if (input.equals("back")) {
            return InputProcessResult.BACK;
        }

        String[] parametersForEvent = input.split(",");

        UUID eventID = getUuid();

        String[] speakersUserName = parametersForEvent[3].split(":");
        ArrayList<User> speakers = new ArrayList<>();

        try{
            for (String speaker:speakersUserName){
                uManager.getUser(speaker);
                speakers.add(uManager.getUser(speaker));
            }
        }catch(UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        for (User speaker:speakers){
            if (!(speaker.getType().equals(User.UserType.SPEAKER))) {
                return InputProcessResult.USER_NOT_SPEAKER;
            }
        }

        ArrayList<UUID> speakersID = getSpeakersID(speakers);
        UUID organizerID = uManager.getCurrentlyLoggedIn().getId();

        String time = parametersForEvent[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime sDateTime = LocalDateTime.parse(time, formatter);

        String eTime = parametersForEvent[2];
        LocalDateTime eDateTime = LocalDateTime.parse(eTime, formatter);

        ArrayList<Integer> occupiedRoom = eManager.availabilityInTime(sDateTime, eDateTime);
        if (6 == occupiedRoom.size()) {
            return InputProcessResult.TIMESLOT_FULL;
        }

        for (User speaker:speakers){
            boolean speakerOccupied = speakerOccupied(sDateTime, eDateTime, speaker);
            if (speakerOccupied){
                return InputProcessResult.SPEAKER_OCCUPIED;
            }
        }

        int roomNum = Integer.parseInt(parametersForEvent[4]);

        if (occupiedRoom.contains(roomNum)){return InputProcessResult.ROOM_FULL;}
        int capacity = Integer.parseInt(parametersForEvent[5]);

        Event eventCreated = new Event(parametersForEvent[0], sDateTime, eDateTime, eventID, organizerID, speakersID,
                new ArrayList<>(), roomNum, capacity);


        eManager.addEvent(eventCreated);
        return InputProcessResult.SUCCESS;

    }

    private boolean speakerOccupied(LocalDateTime sDateTime, LocalDateTime eDateTime, User speaker) {
        for (String e : eManager.listOfEventsHosting(speaker)) {
            Event eventHosting = eManager.getEvent(e);
            LocalDateTime startTime = eventHosting.getEventTime();
            LocalDateTime endTime = eventHosting.getEventETime();

            if (sDateTime.isBefore(startTime) && eDateTime.isAfter(startTime)|
                    (sDateTime.isBefore(endTime) && eDateTime.isAfter(endTime))|
                    (sDateTime.isAfter(startTime) && eDateTime.isBefore(endTime))
            ){
                return true;
            }
        }
        return false;
    }

    private UUID getUuid() {
        ArrayList<UUID> eID = new ArrayList<>();
        ArrayList<UUID> uID = new ArrayList<>();
        for (Event e : eManager.getEvents()) {
            eID.add(e.getId());
        }
        for (User u : uManager.getUsers()) {
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
}
