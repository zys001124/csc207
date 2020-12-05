package controllers;

import exceptions.EventNotFoundException;
import exceptions.UserNotEnrolledInEventException;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

/**
 * A controller for handling inputs when the Attendee is un-enrolling
 * from an Event
 */
public class EventUnEnrollController {
    private final EventManager eventManager;
    private final UserManager userManager;

    /**
     * Creates an EventUnEnrollController with the given EventManager and UserManager
     *
     * @param eventManager The EventManager this controller will use
     * @param userManager  The UserManager this controller will use
     */
    public EventUnEnrollController(EventManager eventManager, UserManager userManager) {
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    /**
     * Handles the input given by the user
     *
     * @param eventInput The user input
     * @return An InputProcessResult enum that details what happened as a result of the given input
     */
    public InputProcessResult unEnrollEvent(String eventInput) {
        try {
            //int parsedInt = Integer.parseInt(eventInput);
            eventManager.removeUserFromEvent(eventInput, userManager.getCurrentlyLoggedIn());
        } catch (EventNotFoundException e) {
            return InputProcessResult.EVENT_NOT_FOUND;
        } catch (NumberFormatException | UserNotEnrolledInEventException e) {
            return InputProcessResult.INVALID_INPUT;
        }
        return InputProcessResult.SUCCESS;
    }
}

