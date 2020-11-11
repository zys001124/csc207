package exceptions;

public class EventNotFoundException extends Exception{
    public EventNotFoundException (int eventNumber){
        super("Event "+ eventNumber + " is not found");
    }
}
