package presenters;


import controllers.InputProcessResult;
import entities.Event;
import useCaseClasses.EventManager;

public class EventCancelPresenter {

    private EventManager manager;
    private String inputResponse = "";

    public EventCancelPresenter(EventManager manager){
        this.manager = manager;
    }

    public String getIntro() {
        return "Here is the list of events.\n"+
                "To cancel an event please type in the name of the event you wish to cancel \n"+
                "Type \"back\" to return to the menu";
    }

    public void setInputResponse(String message) {
        inputResponse = message;
    }

    public String getAllEvents(){
        String events = "";
        for(Event e:manager.getEvents()){
            events = events + e.getEventTitle()+'\n';
        }
        return events;
    }

    public String getInputResponse(InputProcessResult result) {
        if (result == InputProcessResult.BACK){
            return null;
        }else if(result == InputProcessResult.EVENT_DOES_NOT_EXIST){
            return "This event does not exist. Try again.\n";
        }else if(result == InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT){
            return "This event is not organized by you. Try again.\n";
        }else{return "Event Canceled successfully";}

    }
}
