package exceptions;

import entities.User;

/**
 * An exception meant to be thrown when a user requests to
 * perform an action their UserType does not allow them to make
 */
public class InvalidUserTypeException extends Exception {

    /**
     * Creates an InvalidUserTypeException
     *
     * @param given the UserType of the user attempting to perform
     *              such an action
     */
    public InvalidUserTypeException(User.UserType given) {
        super("Invalid user type given: " + given.name());
    }
}
