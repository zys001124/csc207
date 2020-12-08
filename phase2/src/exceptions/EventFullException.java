package exceptions;

/**
 * An exception meant to be used when the requested event is full
 */
public class EventFullException extends Exception {
    /**
     * Exception constructor for when a Event is currently full
     *
     * @param eventInput The user input event name
     */
    public EventFullException(String eventInput) {
        super("Event " + eventInput + " is full");
    }
}
