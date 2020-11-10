package presenters;

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
        return "Welcome. Here are the UUID's of theEvents that you are hosting and you can message: \n" + getEvents(eventManager.idOfEventsHosting(userManager.getCurrentlyLoggedIn())) +
                "type \"back\" to return to the main menu.";
    }

    public String getEvents(List<UUID> events){
        String s = "";
        for(UUID e: events){
            s += e + "\n";
        }
        return s;
    }



    public void setInputResponse(String message) {
        inputResponse = message;
    }

    public String getInputResponse() {
        return inputResponse;
    }
}
