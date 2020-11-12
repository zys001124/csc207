package presenters;

import controllers.InputProcessResult;
import useCaseClasses.EventManager;

import java.time.format.DateTimeFormatter;

public class EventEnrollPresenter{
    private EventManager manager;

    public EventEnrollPresenter(EventManager manager){
        this.manager = manager;
    }

    public String enrollIntro(){
        return "Please Enter the event name that you want to enroll in.";
    }

    public String getEventNumberInputPrompt(){
        return "Event Number: ";
    }

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

    public String getEnrollResultMessage(InputProcessResult result){
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
