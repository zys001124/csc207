package exceptions;

public class UserTypeDoesNotExistException extends Exception {
    public UserTypeDoesNotExistException(String message) {
        super(message);
    }
}
