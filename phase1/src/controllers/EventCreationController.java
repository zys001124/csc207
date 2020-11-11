package controllers;

import entities.Event;
import entities.User;
import presenters.EventCreationPresenter;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class EventCreationController {

    private EventManager Emanager;
    private EventCreationPresenter presenter;
    private UserManager uManager;

    public EventCreationController(EventManager manager, EventCreationPresenter presenter, UserManager uManager){
        Emanager = manager;
        this.presenter = presenter;
        this.uManager = uManager;
    }


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
        for (User u:uManager.getusers()){
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
        for (User choice:uManager.getusers()){
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

        Event eventCreated = new Event(parametersForEvent[0], dateTime, eventID, organizerID, speakerID,
        new ArrayList<>(), roomNum);

        if (Emanager.addEvent(eventCreated)){
            return InputProcessResult.SUCCESS;
        }else{
            return InputProcessResult.ROOM_FULL;
        }
    }

}
