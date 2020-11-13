package gateways.savers;

import entities.Message;

import java.io.IOException;

public class MessageSaver extends Saver<Message> {

    public MessageSaver(String filepath) throws IOException {
        super(filepath);
    }

    public void save(Message message) throws IOException {
        output.append(message.getMessageText());
        output.append(parameterSeparationChar);
        output.append(message.getSenderId().toString());
        output.append(parameterSeparationChar);
        output.append(message.getRecipientId().toString());
        output.append(parameterSeparationChar);
        output.append(message.getId().toString());
        output.append(parameterSeparationChar);

        output.flush();
    }

}
