package exceptions;

public class IncorrectPasswordException extends Exception{
    public IncorrectPasswordException(String username){
        super("Incorrect password for user: "+username);
    }

}
