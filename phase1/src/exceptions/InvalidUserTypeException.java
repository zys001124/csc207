package exceptions;

import entities.User;

public class InvalidUserTypeException extends Exception {

    public InvalidUserTypeException(User.UserType required, User.UserType given) {
        super("Invalid user type. required: "+required.name()+", given: "+given.name());
    }
}
