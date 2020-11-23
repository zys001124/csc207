package exceptions;

/**
 * An exception meant to be thrown when an incorrect password
 * is entered for a given user at login
 */
public class IncorrectPasswordException extends Exception {

    /**
     * exception constructor for a password that does not match the username
     *
     * @param username String of the username for which the password does't match
     */
    public IncorrectPasswordException(String username) {
        super("Incorrect password for user: " + username);
    }

}
