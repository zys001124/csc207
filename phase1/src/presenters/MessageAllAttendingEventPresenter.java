package presenters;

import controllers.InputProcessResult;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

import java.util.List;

public class MessageAllAttendingEventPresenter extends Presenter {

    private EventManager eventManager;
    private UserManager userManager;

    public MessageAllAttendingEventPresenter(UserManager userManager, EventManager eventManager){
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    public String getPreInputText(){
        return "Welcome. Here are the event names of the events that you are hosting and you can message: \n" + getEvents(eventManager.listOfEventsHosting(userManager.getCurrentlyLoggedIn())) +
                "Please enter the event name of the event you would like to message all of the attendees for:\n" +
                "Enter \"back\" to go back to the main menu.";
    }

    public String getMessage(){
        return "Please enter the message you wish to send out to your users:";
    }


    private String getEvents(List<String> events){
        StringBuilder s = new StringBuilder();
        for(String e: events){
            s.append(e).append("\n");
        }
        return s.toString();
    }
    //public void setInputResponse(String s){ inputResponse = s;}

    public String getInputResponseText(InputProcessResult result){
        if(result == InputProcessResult.INVALID_INPUT) {
            return "Could Not understand your input. Staying on this screen";
        }
        else {
            return "Message successfully sent. \nReturning to Main Menu";
        }
    }
}
