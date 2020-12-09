package exceptions;

/**
 * An exception for when a user type does not exist
 */
public class UserTypeDoesNotExistException extends Exception {

    /**
     * The constructor for UserTypeDoesNotExistException
     *
     * @param message - the error message
     */
    public UserTypeDoesNotExistException(String message) {
        super(message);
    }
}
