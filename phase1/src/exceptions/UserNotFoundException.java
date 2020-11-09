package exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String username) {
        super("User: "+username+" not found");
    }
}
