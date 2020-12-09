package exceptions;

/**
 * An exception meant to be thrown if someone attempts to make a
 * user account with a username that another user already possesses
 */
public class UsernameAlreadyExistsException extends Exception {
    /**
     * The constructor for UsernameAlreadyExistsException
     *
     * @param message - the error message
     */
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
