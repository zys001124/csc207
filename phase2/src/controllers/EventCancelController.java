package controllers;

import entities.Event;
import entities.User;
import presenters.EventCancelPresenter;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;


/**
 * A controller for handling inputs when an organizer try
 * to cancel an existing event
 */
public class EventCancelController {

    private final EventManager Emanager;
    private final UserManager Umanager;

    /**
     * Creates an EventCancelController with the given EventManager, EventCancelPresenter and UserManager
     *
     * @param Emanager  the EventManager this controller will use
     * @param Umanager  the UserManager this controller will use
     */

    public EventCancelController(EventManager Emanager, UserManager Umanager) {
        this.Emanager = Emanager;
        this.Umanager = Umanager;
    }

    /**
     * Handle the input given by the user and decides which screen menu to show given the input
     *
     * @param input - the input from user
     * @return an InputProcessResult enum that details what happened as a result of the given input
     */

    public InputProcessResult getNextScreen(String input) {

        if (input.equals("back")) {
            return InputProcessResult.BACK;
        }

        if (!Emanager.eventTitleExists(input)) {
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }

        Event currentEvent = Emanager.getEvent(input);
        User currentUser = Umanager.getCurrentlyLoggedIn();

        if (!Emanager.hasOrganizedEvent(currentUser, currentEvent) &&
                !Umanager.getCurrentlyLoggedIn().getType().equals(User.UserType.ADMIN)) {
            return InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT;
        }

        Emanager.removeEvent(currentEvent.getId());

        return InputProcessResult.SUCCESS;
    }

}
