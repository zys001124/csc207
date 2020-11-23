package exceptions;

/**
 * An exception meant to be thrown when a User is searched for
 * but is not found
 */
public class UserNotFoundException extends Exception {

    /**
     * Creates a UserNotFoundException
     *
     * @param username - The username that is being searched for
     */
    public UserNotFoundException(String username) {
        super("User: " + username + " not found");
    }
}
