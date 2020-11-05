package gateways.savers;

import entities.Message;

import java.io.IOException;

public class MessageSaver extends Saver<Message> {

    public MessageSaver(String filepath) throws IOException {
        super(filepath);
    }

    public void save(Message message) throws IOException {
        output.append(message.getMessageText());
        output.append(",");
        output.append(message.getSenderId().toString());
        output.append(",");
        output.append(message.getRecipientId().toString());
        output.append(",");
        output.append(message.getId().toString());
        output.append("\n");

        output.flush();
    }

}
