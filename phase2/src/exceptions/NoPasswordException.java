package exceptions;

/**
 * An exception meant to be thrown if someone attempts to put in nothing
 * to the password field when creating an account
 */
public class NoPasswordException extends Exception {
    public NoPasswordException(String message) {
        super(message);
    }
}
