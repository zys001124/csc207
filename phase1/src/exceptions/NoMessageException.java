package exceptions;


/**
 * Exception class for when there is no message to be found.
 */
public class NoMessageException extends Exception{
    public NoMessageException(String errorMessage){
        super(errorMessage);
    }
}
