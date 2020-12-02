package exceptions;

/**
 * An exception meant to be thrown when a message being sent receives
 * input reqeusting it to be cancelled
 */
public class MessageCancelledException extends Exception {

    /**
     * Exception constructor that throws an exception when the message is cancelled
     *
     * @param sender the sender of the message
     */
    public MessageCancelledException(String sender) {
        super("Message with sender " + sender + " was cancelled.");
    }

}
