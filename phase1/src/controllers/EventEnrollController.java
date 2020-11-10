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

    public EnrollResult enrollEvent(String eventName){
        try{
            eventManager.addUserToEvent(eventName, userManager.getCurrentlyLoggedIn());
        }catch(EventNotFoundException e){
            return EnrollResult.EVENT_NOT_FOUND;
        }
        return EnrollResult.SUCCESS;
    }

    public enum EnrollResult{
        SUCCESS,
        EVENT_NOT_FOUND,
    }

}

