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

    public InputProcessResult enrollEvent(String eventName){
        try{
            eventManager.addUserToEvent(eventName, userManager.getCurrentlyLoggedIn());
        }catch(EventNotFoundException e){
            return InputProcessResult.EVENT_NOT_FOUND;
        }
        return InputProcessResult.SUCCESS;
    }

}

