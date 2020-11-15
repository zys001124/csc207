package exceptions;

public class EventNotFoundException extends Exception{
    /**
     * Exception constructor for when a event based on user input index does not exist
     * @param eventNumber The user input index
     */
    public EventNotFoundException (int eventNumber){
        super("Event "+ eventNumber + " is not found");
    }
}
