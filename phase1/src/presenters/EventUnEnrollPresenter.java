package presenters;

import entities.Event;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;
import controllers.InputProcessResult;

import java.time.format.DateTimeFormatter;

public class EventUnEnrollPresenter extends Presenter {
    private EventManager eventManager;
    private UserManager userManager;

    public EventUnEnrollPresenter(EventManager eventManager, UserManager userManager){
        this.eventManager = eventManager;
        this.userManager = userManager;
    }

    public String getPreInputText(){
        return "Please Enter the event number that you want to un-enroll.";
    }

    public String getEventNumberInputPrompt(){
        return "Event Number: ";
    }

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
