package presenters;


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

    public String getInputResponse() {
        return inputResponse;
    }
}
