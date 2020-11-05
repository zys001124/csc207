package exceptions;

public class IncorrectNumberOfParametersException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Wrong number of parameters passed in array";
    }
}
