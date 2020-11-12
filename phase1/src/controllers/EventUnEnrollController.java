package controllers;

import exceptions.EventNotFoundException;
import exceptions.UserNotEnrolledInEventException;
import exceptions.UserNotFoundException;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

public class EventUnEnrollController {
    private EventManager eventManager;
    private UserManager userManager;

    public EventUnEnrollController(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    public InputProcessResult unEnrollEvent(String eventInput){
        try{
            int parsedInt = Integer.parseInt(eventInput);
            eventManager.removeUserFromEvent(parsedInt, userManager.getCurrentlyLoggedIn());
        }catch(EventNotFoundException e){
            return InputProcessResult.EVENT_NOT_FOUND;
        }catch(NumberFormatException | UserNotEnrolledInEventException e){
            return InputProcessResult.INVALID_INPUT;
        }
        return InputProcessResult.SUCCESS;
    }
}

