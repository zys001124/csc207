package exceptions;

public class NoMessageException extends Exception{
    public NoMessageException(String errorMessage){
        super(errorMessage);
    }
}
