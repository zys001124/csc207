package exceptions;

/**
 * An exception for when a user type does not exist
 */
public class UserTypeDoesNotExistException extends Exception {
    public UserTypeDoesNotExistException(String message) {
        super(message);
    }
}
