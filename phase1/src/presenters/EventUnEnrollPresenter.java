package presenters;

import entities.Event;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;
import controllers.InputProcessResult;
import java.time.format.DateTimeFormatter;

/**
 * A presenter for generating strings to be displayed when the
 * Attendee is un-enrolling from an event
 */
public class EventUnEnrollPresenter extends Presenter {
    private EventManager eventManager;
    private UserManager userManager;

    /**
     * Creates an EventUnEnrollPresenter with a given EventManager and UserManager
     * @param eventManager The EventManager this presenter will use
     * @param userManager The UserManager this presenter will use
     */
    public EventUnEnrollPresenter(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    /**
     * Generates the String that is to be displayed right before user makes any inputs
     * @return A string that is to be displayed right before any inputs are made
     */
    public String getPreInputText(){
        return "Please Enter the event number that you want to un-enroll.";
    }

    /**
     * Generates a input prompt
     * @return A string of input prompt
     */
    public String getEventNumberInputPrompt(){
        return "Event Number: ";
    }

    /**
     * Generates a list of all events that the user who is currently logged
     * in are enrolled in, in which contains the index of the event, the name
     * of the event, the datetime of the event, and the location of the event
     * for display
     * @return A string that contains all events the currently logged in user
     * are enrolled in and each of their detail information
     */
    public String getAttendeeAllEvents(){
        String result = "";
        for(int i = 1; i <= eventManager.getEvents().size(); i++){
            Event e = eventManager.getEvents().get(i-1);
            if(e.hasAttendee(userManager.getCurrentlyLoggedIn().getId())){
                result = result.concat(i+". ").concat(e.getEventTitle()+", ")
                        .concat(e.getEventTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+", ")
                        .concat("Room " + e.getEventRoom()+'\n');
            }
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
        if(result == InputProcessResult.SUCCESS){
            return "Un-Enroll Successful!";
        }else if(result == InputProcessResult.EVENT_NOT_FOUND){
            return "Event not found. Please try again.";
        }else{
            return "Invalid Input. Please try again.";
        }
    }
}
