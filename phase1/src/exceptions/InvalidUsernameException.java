package exceptions;

public class InvalidUsernameException extends Exception{

    /**
     * exception constructor that throws an exception when a user can't be found
     * @param username the username that can not be found
     */
    public InvalidUsernameException(String username) {
        super("Invalid username: " + username);
    }
}
