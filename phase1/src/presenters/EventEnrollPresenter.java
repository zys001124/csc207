package presenters;

import controllers.InputProcessResult;
import useCaseClasses.EventManager;
import java.time.format.DateTimeFormatter;

/**
 * A presenter for generating strings to be displayed when the
 * Attendee is enrolling in an event
 */
public class EventEnrollPresenter extends Presenter {
    private EventManager manager;

    /**
     * Creates an EventEnrollPresenter with a given EventManager
     * @param manager The EventManager this presenter will use
     */
    public EventEnrollPresenter(EventManager manager){
        this.manager = manager;
    }

    /**
     * Generates the String that is to be displayed right before user makes any inputs
     * @return A string that is to be displayed right before any inputs are made
     */
    public String getPreInputText(){
        return "Please Enter the event name that you want to enroll in.";
    }

    /**
     * Generates a input prompt
     * @return A string of input prompt
     */
    public String getEventNumberInputPrompt(){
        return "Event Number: ";
    }

    /**
     * Generates a list of all events available, in which contains the index
     * of the event, the name of the event, the datetime of the event, and
     * the location of the event for display
     * @return A string that contains all events available and each of their
     * detail information
     */
    public String getAllEvents(){
        String result = "";
        for(int i = 1; i <= manager.getEvents().size(); i++)
        {
            result = result.concat(i+". ").concat(manager.getEvents().get(i-1).getEventTitle()+", ")
            .concat(manager.getEvents().get(i-1).getEventTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+", ")
            .concat("Room "+ manager.getEvents().get(i-1).getEventRoom()+'\n');
        }
        return result;
    }

    /**
     * Generates the state of the system after the user's input
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return A string that shows the state of the system
     */
    public String getInputResponseText(InputProcessResult result){
        if(result == InputProcessResult.SUCCESS) {
            return "Enroll Successful!";
        } else if(result == InputProcessResult.EVENT_NOT_FOUND) {
            return "Event not found. Please try again.";
        }
        else if(result == InputProcessResult.USER_ALREADY_ENROLLED) {
            return "You are already enrolled in this event. Please try again.";
        }
        else{
            return "Invalid Input. Please try again.";
        }
    }
}
