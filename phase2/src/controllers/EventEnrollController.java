package controllers;

import exceptions.EventFullException;
import exceptions.EventNotFoundException;
import exceptions.InvalidUserTypeException;
import exceptions.UserAlreadyEnrolledException;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

/**
 * A controller for handling inputs when the Attendee is enrolling
 * in an Event
 */
public class EventEnrollController {
    private final EventManager eventManager;
    private final UserManager userManager;

    /**
     * Creates an EventEnrollController with the given EventManager and UserManager
     *
     * @param eventManager The EventManager this controller will use
     * @param userManager  The UserManager this controller will use
     */
    public EventEnrollController(EventManager eventManager, UserManager userManager) {
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    /**
     * Handles the input given by the user
     *
     * @param eventInput The user input
     * @return An InputProcessResult enum that details what happened as a result of the given input
     */
    public InputProcessResult enrollEvent(String eventInput) {
        if(eventInput.equals("back")){
            return InputProcessResult.BACK;
        }
        try {
            int parsedInput = Integer.parseInt(eventInput);
            eventManager.addUserToEvent(parsedInput, userManager.getCurrentlyLoggedIn());
        } catch (EventNotFoundException e) {
            return InputProcessResult.EVENT_NOT_FOUND;
        }catch (InvalidUserTypeException e) {
            return InputProcessResult.EVENT_FOR_VIPONLY;
        }catch (EventFullException e) {
            return InputProcessResult.EVENT_IS_FULL;
        } catch (NumberFormatException e) {
            return InputProcessResult.INVALID_INPUT;
        } catch (UserAlreadyEnrolledException e) {
            return InputProcessResult.USER_ALREADY_ENROLLED;
        }
        return InputProcessResult.SUCCESS;
    }

}

