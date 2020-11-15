package controllers;

import entities.Event;
import entities.User;
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

    private EventManager Emanager;
    private UserManager uManager;

    /**
     * Creates a EventCreationController with the given UserManager and EventManager
     * @param manager - The EventManager this controller will use
     * @param uManager - The UserManager this controller will use
     */
    public EventCreationController(EventManager manager, UserManager uManager){
        Emanager = manager;
        this.uManager = uManager;
    }

    /**
     * Handles the input given by the user
     * @param input the users input of string that
     *              include information of eventTitle, Date, speakerUsername, and RoomNum
     * @return an InputProcessResult enum that details what happened
     * as a result of the given input
     *
     *Precondition: the roomNum is between 0 to 5.
     */
    public InputProcessResult createEvent(String input){
        if(input.equals("back")) {
            return InputProcessResult.BACK;
        }

        String[] parametersForEvent = input.split(",");

        ArrayList<UUID> eID = new ArrayList<>();
        ArrayList<UUID> uID = new ArrayList<>();
        for (Event e:Emanager.getEvents()){
            eID.add(e.getId());
            }
        for (User u:uManager.getUsers()){
            uID.add(u.getId());
            }

        boolean uuidExist = true;
        UUID eventID = UUID.randomUUID();
        while (uuidExist){
            if (!(eID.contains(eventID) || uID.contains(eventID))){
                uuidExist = false;
            }else{
                eventID = UUID.randomUUID();
            }
        }

        boolean registeredUser = false;
        User speaker = uManager.getCurrentlyLoggedIn();
        for (User choice:uManager.getUsers()){
            if (choice.getUsername().equals(parametersForEvent[2])){
                registeredUser = true;
                speaker = choice;
            }
        }

        if (!registeredUser){
            return InputProcessResult.USER_NOT_FOUND;
        }

        if (!(speaker.getType().equals(User.UserType.SPEAKER))){
            return InputProcessResult.USER_NOT_SPEAKER;
        }


        UUID speakerID = speaker.getId();
        UUID organizerID = uManager.getCurrentlyLoggedIn().getId();

        String time = parametersForEvent[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);

        int roomNum = Integer.parseInt(parametersForEvent[3]);

        if (Emanager.availabilityInTime(dateTime)){
            return InputProcessResult.TIMESLOT_FULL;
        }
        for (Event e:Emanager.getEvents()){
            if (e.getEventTime().equals(dateTime)){
                if(e.getSpeakerId() == speakerID){
                    return InputProcessResult.SPEAKER_OCCUPIED;
                }
            }
        }

        Event eventCreated = new Event(parametersForEvent[0], dateTime, eventID, organizerID, speakerID,
        new ArrayList<>(), roomNum);

        if (Emanager.addEvent(eventCreated)){
            return InputProcessResult.SUCCESS;
        }else{
            return InputProcessResult.ROOM_FULL;
        }
    }

}
