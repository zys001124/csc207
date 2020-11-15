package exceptions;

public class MessageCancelledException extends Exception {

    public MessageCancelledException(String sender, String receiver) {
        super("Message with sender " + sender + " and receiver " + receiver + " was cancelled.");
    }

}
