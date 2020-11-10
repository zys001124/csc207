package exceptions;

public class EventNotFoundException extends Exception{
    public EventNotFoundException (String eventName){
        super("Event: "+ eventName + " not found");
    }
}
