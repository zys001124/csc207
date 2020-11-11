package presenters;

import controllers.InputProcessResult;
import entities.Event;
import entities.User;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.List;
import java.util.UUID;

public class EventListForMessagingPresenter {

    private String inputResponse = "";
    private UserManager userManager;
    private EventManager eventManager;

    public EventListForMessagingPresenter(UserManager userManager, EventManager eventManager){
        this.userManager = userManager;
        this.eventManager = eventManager;
    }


    public String getIntro(){
        return "Welcome. Here are the event names of the events that you are hosting and you can message: \n" + getEvents(eventManager.ListOfEventsHosting(userManager.getCurrentlyLoggedIn())) +
                "type \"back\" to return to the main menu.";
    }

    public String getEvents(List<String> events){
        String s = "";
        for(String e: events){
            s += e + "\n";
        }
        return s;
    }



//    public void setInputResponse(String message) {
//        inputResponse = message;
//    }

    public String getInputResponse(InputProcessResult result) {
        if(result == InputProcessResult.SUCCESS) {
            return "";
        }
        else {
            return "Event Not found. Please try again.";
        }
    }
}
