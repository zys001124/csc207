package entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class Message implements Serializable, Comparable<Message> {

    private UUID messageId;

    private String messageText;
    private UUID senderId;
    private UUID recipientId;

    private LocalDateTime timeSent;

    public Message(String message, UUID sender, UUID recipient, UUID id) {
        messageText = message;
        senderId = sender;
        recipientId = recipient;
        messageId = id;


        Instant now = Instant.ofEpochMilli(System.currentTimeMillis());
        timeSent = LocalDateTime.ofInstant(now, ZoneId.of("America/New_York")); //Toronto time
    }

    public UUID getSenderId(){return senderId;}

    public UUID getRecipientId(){return recipientId;}

    public UUID getId(){return messageId;}

    public String getMessageText() {return messageText;}

    public LocalDateTime getTimeSent() {return timeSent;}

    @Override
    public int compareTo(Message o) {
        return timeSent.compareTo(o.timeSent);
    }
}
