package exceptions;

/**
 * An exception to be thrown when an attempt is made to book an event
 * where the start time is before or equal to the end time
 */
public class InvalidEventTimeRangeException extends Exception {

    /**
     * The constructor for an InvalidEventTimeRangeException
     */
    public InvalidEventTimeRangeException() {
        super("Start time must come before end time");
    }
}
