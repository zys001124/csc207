package exceptions;

/**
 * An exception meant to be thrown when a loader does not pass
 * the correct number of parameters for entity creation
 */
public class IncorrectNumberOfParametersException extends RuntimeException {
    /**
     * Gets the exception message
     *
     * @return a String - the exception message
     */
    @Override
    public String getMessage() {
        return "Wrong number of parameters passed in array";
    }
}
