package exceptions;

/**
 * An exception meant to be thrown when an invalid username
 * is entered during input
 */
public class InvalidUsernameException extends Exception {

    /**
     * Exception constructor that throws an exception when a username can't be used
     *
     * @param username the username that can not be used
     */
    public InvalidUsernameException(String username) {
        super("Invalid username: " + username);
    }
}
