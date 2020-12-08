package exceptions;

/**
 * An exception to be thrown when a user tries to make an account with nothing
 * inputted into the user box.
 */
public class NoUsernameException extends Exception {
    public NoUsernameException(String message) {
        super(message);
    }
}
