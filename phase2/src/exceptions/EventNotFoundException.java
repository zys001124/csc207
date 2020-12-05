package exceptions;

/**
 * An exception meant to be used when an EventManager is unable to find
 * a requested event
 */
public class EventNotFoundException extends Exception {
    /**
     * Exception constructor for when a event based on user input index does not exist
     *
     * @param eventInput The user input event name
     */
    public EventNotFoundException(String eventInput) {
        super("Event " + eventInput + " is not found");
    }
}
