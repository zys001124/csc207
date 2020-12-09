package exceptions;

/**
 * An exception to be thrown when an attempt is made to
 * book two events in the same room at overlaping times
 */
public class EventBookingOverlapException extends Exception {

    /**
     * The constructor for an EventBookingOverlapException
     */
    public EventBookingOverlapException() {
        super("Two events cannot have overlaping times in the same room");
    }
}
