package gateways.savers;

import entities.Message;

import java.io.IOException;

/**
 * A Saver for Message objects
 */
public class MessageSaver extends Saver<Message> {

    /**
     * Creates a MessageSaver object that will save Messages at the
     * given filepath
     *
     * @param filepath - the filepath to save Message objects
     * @throws IOException if the file does not exist at the filepath
     */
    public MessageSaver(String filepath) throws IOException {
        super(filepath);
    }

    /**
     * Saves an individual Message
     *
     * @param message - The Message to be saved
     * @throws IOException if the file does not exist at the filepath
     */
    public void save(Message message) throws IOException {
        output.append(message.getMessageText());
        output.append(parameterSeparationChar);
        output.append(message.getSenderId().toString());
        output.append(parameterSeparationChar);
        output.append(message.getRecipientId().toString());
        output.append(parameterSeparationChar);
        output.append(message.getId().toString());
        output.append(parameterSeparationChar);
        output.append(message.getTimeSent().toString());
        output.append('\n');
        output.flush();
    }

}
