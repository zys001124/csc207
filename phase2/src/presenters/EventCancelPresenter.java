package presenters;


import controllers.InputProcessResult;
import entities.Event;
import useCaseClasses.EventManager;

/**
 * A presenter for generating strings that should be displayed
 * on the console when an organizer wants to cancel an existing event
 */

public class EventCancelPresenter extends Presenter {

    private final EventManager manager;

    /**
     * Creates an EventCancelPresenter with the given EventManager
     *
     * @param manager the EventManager this controller will use
     */

    public EventCancelPresenter(EventManager manager) {
        this.manager = manager;
    }

    /**
     * Generate the String that should be displayed before the users type in their input
     *
     * @return the String that should be displayed before the input is made
     */

    @Override
    public String getPreInputText() {
        return "Here is the list of events.\n" +
                "To cancel an event please type in the name of the event you wish to cancel \n" +
                "Type \"back\" to return to the menu";
    }

    /**
     * Generate the String that contains the list of names of all the
     * events in the given EventManager
     *
     * @return the String that contains the list of names of all the
     * events in the given EventManager
     */

    public String getAllEvents() {
        String events = "";
        for (Event e : manager.getEvents()) {
            events = events + e.getEventTitle() + '\n';
        }
        return events;
    }

    /**
     * Generate the String that should be displayed after the input is made
     *
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return the String that should be displayed after the input is made
     */

    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.BACK) {
            return "";
        } else if (result == InputProcessResult.EVENT_DOES_NOT_EXIST) {
            return "This event does not exist. Try again.\n";
        } else if (result == InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT) {
            return "This event is not organized by you. Try again.\n";
        } else {
            return "Event Canceled successfully";
        }

    }
}
