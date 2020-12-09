package exceptions;

/**
 * An exception for when an object type is unsusable for some use
 */
public class IncorrectObjectTypeException extends Exception {

    public IncorrectObjectTypeException(String message) {
        super(message);
    }
}
