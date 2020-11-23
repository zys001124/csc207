package exceptions;

/**
 * An exception meant to be used when an EventManager is unable to find
 * a requested event
 */
public class EventFullException extends Exception {
    /**
     * Exception constructor for when a Event is currently full
     *
     * @param eventNumber The user input index
     */
    public EventFullException(int eventNumber) {
        super("Event " + eventNumber + " is full");
    }
}
