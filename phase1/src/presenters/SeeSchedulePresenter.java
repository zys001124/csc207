package presenters;

import controllers.InputProcessResult;
import entities.Event;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SeeSchedulePresenter extends Presenter {
    private EventManager eventManager;
    private UserManager userManager;

    public SeeSchedulePresenter(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    public String getPreInputText(){
        return "Here are the event(s) you are enrolled in: \n"+getAttendeeEvents();
    }

    public String getAttendeeEvents(){
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

    public String getInputResponseText(InputProcessResult result) {
        return "";
    }

    public String outro(){
        return "Enter any key to return to main menu.";
    }
}
