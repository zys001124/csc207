package controllers;

import entities.Event;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChangeEventCapacityController {

    private final EventManager eManager;


    public ChangeEventCapacityController(EventManager manager) {
        eManager = manager;
    }

    public InputProcessResult eventCapacityChange(String input){
        if (input.equals("back")) {
            return InputProcessResult.BACK;
        }
        String[] parameters = input.split(",");


        int newCapacity = Integer.parseInt(parameters[2]);
        if (newCapacity > 60){
            return InputProcessResult.CAPACITY_OVER;
        }

        LocalDateTime sDateTime = getLocalDateTime(parameters[0]);

        int roomNum = Integer.parseInt(parameters[1]);

        for (Event event:eManager.getEvents()){
            if (event.getEventRoom() == roomNum){
                if (event.getEventTime().equals(sDateTime)){
                    event.setEventCapacity(newCapacity);
                    return InputProcessResult.SUCCESS;
                }
            }
        }

        return InputProcessResult.EVENT_DOES_NOT_EXIST;

    }

    private LocalDateTime getLocalDateTime(String parameter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(parameter, formatter);
    }
}
