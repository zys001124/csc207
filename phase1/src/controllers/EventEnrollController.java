package controllers;

import useCaseClasses.UserManager;
import exceptions.EventNotFoundException;
import useCaseClasses.EventManager;

public class EventEnrollController {
    private EventManager eventManager;
    private UserManager userManager;

    public EventEnrollController(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    public InputProcessResult enrollEvent(String eventInput){
        try{
            int parsedInput = Integer.parseInt(eventInput);
            eventManager.addUserToEvent(parsedInput, userManager.getCurrentlyLoggedIn());
        }catch(EventNotFoundException e){
            return InputProcessResult.EVENT_NOT_FOUND;
        }catch(NumberFormatException e){
            return InputProcessResult.INVALID_INPUT;
        }
        return InputProcessResult.SUCCESS;
    }

}

