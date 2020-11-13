package exceptions;

public class InvalidUsernameException extends Exception{

    public InvalidUsernameException(String username) {
        super("Invalid username: " + username);
    }
}
