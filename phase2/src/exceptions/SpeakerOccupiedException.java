package exceptions;

/**
 * An exception to be thrown when an attempt is made to book
 * a speaker for an event during a time in which they are already
 * speaking at one
 */
public class SpeakerOccupiedException extends Exception {

    /**
     * The constructor for a SpeakerOccupiedException
     */
    public SpeakerOccupiedException() {
        super("Attempted to book speaker at two different events simultaneously");
    }
}
