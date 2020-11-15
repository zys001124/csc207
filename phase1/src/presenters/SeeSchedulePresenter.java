package presenters;

import controllers.InputProcessResult;
import entities.Event;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A presenter for generating strings to be displayed when the
 * Attendee is viewing his/her schedule
 */
public class SeeSchedulePresenter extends Presenter {
    private EventManager eventManager;
    private UserManager userManager;

    /**
     * Creates an SeeSchedulePresenter with a given EventManager and UserManager
     * @param eventManager The EventManager this presenter will use
     * @param userManager The UserManager this presenter will use
     */
    public SeeSchedulePresenter(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    /**
     * Generates the String that is to be displayed right before user makes any inputs,
     * including the intro sentence and the schedule of the attendee who is currently
     * logged in in the order of the datetime
     * @return A string that is to be displayed right before any inputs are made
     */
    public String getPreInputText(){
        return "Here are the event(s) you are enrolled in: \n"+getAttendeeEvents();
    }

    private String getAttendeeEvents(){
        ArrayList<Event> sortedEvents = eventManager.eventSortTime();
        String result = "";
        for(int i = 1; i <= sortedEvents.size(); i++){
            Event e = sortedEvents.get(i-1);
            if(e.hasAttendee(userManager.getCurrentlyLoggedIn().getId())){
                result = result.concat(e.getEventTitle()+", ")
                        .concat(e.getEventTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+", ")
                        .concat("Room " + e.getEventRoom()+'\n');
            }
        }
        return result;
    }

    /**
     * Generates a response after after the user's input
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return An empty String
     */
    public String getInputResponseText(InputProcessResult result) {
        return "";
    }

    /**
     * Generates an outro sentence/prompt
     * @return A string of input prompt
     */
    public String outro(){
        return "Enter any key to return to main menu.";
    }
}
