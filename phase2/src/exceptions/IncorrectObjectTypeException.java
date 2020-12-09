package exceptions;

/**
 * An exception for when an object type is unusable for some use
 */
public class IncorrectObjectTypeException extends Exception {
    /**
     * Constructor for IncorrectObjectTypeException
     *
     * @param message - the error message
     */
    public IncorrectObjectTypeException(String message) {
        super(message);
    }
}
