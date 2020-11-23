package controllers;

import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

/**
 * Controller for the message all attending event option
 */
public class MessageAllAttendingEventController {

    private final UserManager userManager;
    private final MessageManager messageManager;
    private final EventManager eventManager;


    /**
     * Sets all of the proper use case classes for the given system to run.
     *
     * @param am the corresponding UserManager class that will run for the program
     * @param mm the corresponding MessageManager class that will run for the program
     * @param em the corresponding EventManager class that will run for the program
     */
    public MessageAllAttendingEventController(UserManager am, MessageManager mm, EventManager em) {//, UUID eventId, MessageAllAttendingEventPresenter presenter, User user) {
        userManager = am;
        messageManager = mm;
        eventManager = em;


    }

    /**
     * Helper method to send the message that the speaker will type to all of the
     * users that are enrolled in this event
     * <p>
     * Called in handleInput if the event put in is found
     *
     * @param message the message that will be sent to all the users
     * @param event   the event that all the users are in that are getting sent a message
     */
    public void sendMessage(String message, String event) {
        messageManager.messageAllAttendingEvent(message, eventManager.getEvent(event),
                userManager.getCurrentlyLoggedIn().getId());
    }

    /**
     * Finds out if the speaker user put in a 'back' instead of the event for an input
     * Returns whether or not they put in back or not
     *
     * @param name the input to see if the speaker put in 'back'.
     * @return and InputProcessResult where if given BACK it will take the user back to the main menu
     * if SUCCESS the program proceeds
     */
    public InputProcessResult findBack(String name) {
        if (name.equals("back")) {
            return InputProcessResult.BACK;
        } else {
            return InputProcessResult.SUCCESS;
        }
    }

    /**
     * Takes in the name of the event and the message the speaker wishes to send out and determines if
     * the name can be found in the events they are hosting. If not It returns a InputProcessResult
     * that prompts them to put in a valid event. If SUCCESS then the message will be sent out to all
     * the users attending that event.
     *
     * @param name    the name of the event to message all users attending
     * @param message - the message that will go out to all users attending name.
     * @return an InputProcessResult which will be given to the UI to determine to either go back
     * to the main menu or prompt user again.
     */
    public InputProcessResult handleInput(String name, String message) {
        FindEvent verify = verifyEvent(name);
        if (verify == FindEvent.FAIL) {
            return InputProcessResult.INVALID_INPUT;
        } else {
            sendMessage(message, name);
            return InputProcessResult.SUCCESS;
        }
    }

    /**
     * Helper method called in handleInput to help determine if the speaker put in the right event
     * name to message the attendees
     * <p>
     * Returns a FindEvent
     *
     * @param name the name of the event the user entered to determine if it exists.
     * @return a FindEvent where SUCCESS will continue the program but FAIL will prompt user to put
     * input in again.
     */
    public FindEvent verifyEvent(String name) {
        //Should we be using userManager.getCurrentlyLoggedIn() or should we just use the user field?
        if (eventManager.listOfEventsHosting(userManager.getCurrentlyLoggedIn()).contains(name)) {
            return FindEvent.SUCCESS;
        } else {
            return FindEvent.FAIL;
        }
    }

    /**
     * returns a enum for verifyEvent where SUCCESS is when the event is found and FAIL is when
     * it is not found.
     */
    public enum FindEvent {
        SUCCESS,
        FAIL
    }


}
