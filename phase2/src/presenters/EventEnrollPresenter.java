package presenters;

import controllers.InputProcessResult;
import entities.Event;
import useCaseClasses.EventManager;

import java.time.format.DateTimeFormatter;

/**
 * A presenter for generating strings to be displayed when the
 * Attendee is enrolling in an event
 */
public class EventEnrollPresenter extends Presenter {
    private final EventManager manager;

    /**
     * Creates an EventEnrollPresenter with a given EventManager
     *
     * @param manager The EventManager this presenter will use
     */
    public EventEnrollPresenter(EventManager manager) {
        this.manager = manager;
    }

    /**
     * Generates the String that is to be displayed right before user makes any inputs
     *
     * @return A string that is to be displayed right before any inputs are made
     */
    public String getPreInputText() {
        return "Please Enter the event name that you want to enroll in.\n Type 'back' to go back to the" +
                "Main Menu";
    }

    /**
     * Generates a input prompt
     *
     * @return A string of input prompt
     */
    public String getEventNumberInputPrompt() {
        return "Event Number: ";
    }

    /**
     * Generates a list of all events available, in which contains the index, datetime,
     * room number, capacity, number of attendees currently enrolled and type of the
     * event.
     *
     * @return A string that contains all events available and each of their
     * detail information
     */
    public String getAllEvents() {
        String result;
        result = "";
        for (int i = 0; i < manager.getEvents().size(); i++) {
            Event event = manager.getEvents().get(i);
            result = result.concat(i + 1 + ". ").concat(event.getEventTitle() + " ")
                    .concat(event.getEventTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .concat("  Room " + event.getEventRoom())
                    .concat("  Capacity " + event.getEventCapacity())
                    .concat("  Currently Enrolled " + event.getEventEnrolledNumber())
                    .concat("  Event Type " + event.getEventType())
                    .concat("  VIP only: " + event.getViponly()+ "\n");
        }
        return result;
    }

    /**
     * Generates the state of the system after the user's input
     *
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return A string that shows the state of the system
     */
    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.SUCCESS) {
            return "Enroll Successful!";
        } else if(result == InputProcessResult.BACK){
            return "Returning back to Main Menu";
        } else if (result == InputProcessResult.EVENT_NOT_FOUND) {
            return "Event not found. Please try again.";
        } else if (result == InputProcessResult.EVENT_FOR_VIPONLY) {
            return "Event is for VIP only and you are not a VIP. Please try again.";
        } else if (result == InputProcessResult.EVENT_IS_FULL){
            return "Event is currently full. Please try another event.";
        } else if (result == InputProcessResult.USER_ALREADY_ENROLLED) {
            return "You are already enrolled in this event. Please try again.";
        } else {
            return "Invalid Input. Please try again.";
        }
    }
}
