package presenters;

import controllers.EventEnrollController;
import controllers.InputProcessResult;
import useCaseClasses.EventManager;

public class EventEnrollPresenter{
    private EventManager manager;

    public EventEnrollPresenter(EventManager manager){
        this.manager = manager;
    }

    public String enrollIntro(){
        return "Please Enter the event name that you want to enroll in.";
    }

    public String getEventNumberInputPrompt(){
        return "Event Name: ";
    }

    public String getAllEvents(){
        String result = "";
        for(int i = 0; i<manager.getEvents().size(); i++)
        {
            result = result.concat(i+". ").concat(manager.getEvents().get(i).getEventTitle()+'\n');
        }
        return result;
    }

    public String getEnrollResultMessage(InputProcessResult result){
        if(result == InputProcessResult.SUCCESS)
        {
            return "Enroll Successful!";
        }
        else
        {
            return "Event not found. Please try again";
        }
    }
}
