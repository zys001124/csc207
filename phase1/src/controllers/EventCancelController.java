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
    private final EventCancelPresenter presenter;
    private final UserManager Umanager;

    /**
     * Creates an EventCancelController with the given EventManager, EventCancelPresenter and UserManager
     *
     * @param Emanager  the EventManager this controller will use
     * @param presenter the EventCancelPresenter this controller will use
     * @param Umanager  the UserManager this controller will use
     */

    public EventCancelController(EventManager Emanager, EventCancelPresenter presenter, UserManager Umanager) {
        this.Emanager = Emanager;
        this.presenter = presenter;
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

        if (!presenter.getAllEvents().contains(input)) {
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }

        Event currentEvent = Emanager.getEvent(input);
        User currentUser = Umanager.getCurrentlyLoggedIn();

        if (!Emanager.hasOrganizedEvent(currentUser, currentEvent)) {
            return InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT;
        }

        Emanager.removeEvent(currentEvent.getId());

        return InputProcessResult.SUCCESS;
    }

}
