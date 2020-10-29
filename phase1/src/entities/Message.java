package entities;

import java.util.UUID;

public class Message {

    private UUID messageId;

    private String messageText;
    private UUID senderId;
    private UUID recipientId;

    public Message(String message, UUID sender, UUID recipient) {
        messageText = message;
        senderId = sender;
        recipientId = recipient;
        messageId = UUID.randomUUID();
    }

    public UUID getSenderId(){return senderId;}

    public UUID getRecipientId(){return recipientId;}

    public UUID getId(){return messageId;}
}
