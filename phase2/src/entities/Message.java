package entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a message between two users at the tech conference
 */
public class Message implements Serializable, Comparable<Message> {

    private final UUID messageId;

    private final String messageText;
    private final UUID senderId;
    private final UUID recipientId;

    private final LocalDateTime timeSent;

    /**
     * Creates a Message with the given message content, UUID of the sender,
     * UUID of the recipient, and UUID (of the message)
     *
     * @param message   - the message text
     * @param sender    - the senders UUID
     * @param recipient - the recipitents UUID
     * @param id        - the messages UUID
     */
    public Message(String message, UUID sender, UUID recipient, UUID id) {
        messageText = message;
        senderId = sender;
        recipientId = recipient;
        messageId = id;
        Instant now = Instant.ofEpochMilli(System.currentTimeMillis());
        timeSent = LocalDateTime.ofInstant(now, ZoneId.of("America/New_York")); //Toronto time
    }

    /**
     * Creates a Message with the given message content, UUID of the sender,
     * UUID of the recipient, UUID (of the message), and time sent
     *
     * @param message   - the message text
     * @param sender    - the senders UUID
     * @param recipient - the recipitents UUID
     * @param id        - the messages UUID
     * @param timeSent  - the time the message was sent
     */
    public Message(String message, UUID sender, UUID recipient, UUID id, LocalDateTime timeSent) {
        messageText = message;
        senderId = sender;
        recipientId = recipient;
        messageId = id;
        this.timeSent = timeSent;
    }

    /**
     * Builds a Message object from a MessageData object
     *
     * @param data - The data to be used in the creation of a Message object
     * @return the constructed Message object
     */
    public static Message fromMessageData(MessageData data) {
        return new Message(data.messageText, UUID.fromString(data.senderId),
                UUID.fromString(data.recipientId), UUID.fromString(data.messageId),
                LocalDateTime.parse(data.timeSent));
    }

    /**
     * Gets the UUID of the User who sent the Message
     *
     * @return a UUID - the UUID of the User who sent the Message
     */
    public UUID getSenderId() {
        return senderId;
    }

    /**
     * Gets the UUID of the User who received the Message
     *
     * @return a UUID - the UUID of the User who received the Message
     */
    public UUID getRecipientId() {
        return recipientId;
    }

    /**
     * Gets the UUID of the Message
     *
     * @return the UUID of the Message
     */
    public UUID getId() {
        return messageId;
    }


    /**
     * Gets the time this message was sent
     *
     * @return a LocalDateTime - the time this message was sent
     */
    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    /**
     * This method is necessary to make Message's comparable by time
     *
     * @param o the Message this Message is being compared to
     * @return an integer representing the difference between this and <o>
     */
    @Override
    public int compareTo(Message o) {
        return timeSent.compareTo(o.timeSent);
    }

    /**
     * the message text string
     *
     * @return String of the message
     */
    @Override
    public String toString() {
        return messageText;
    }

    /**
     * Returns boolean on if the object is the same as a message
     *
     * @param o object to be checked
     * @return True if ti is a certain message, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId) &&
                Objects.equals(messageText, message.messageText) &&
                Objects.equals(senderId, message.senderId) &&
                Objects.equals(recipientId, message.recipientId) &&
                Objects.equals(timeSent, message.timeSent);
    }

    /**
     * Hashes the code of the format of each message
     *
     * @return an int of the hashed code
     */
    @Override
    public int hashCode() {
        return Objects.hash(messageId, messageText, senderId, recipientId, timeSent);
    }

    /**
     * gets the message data of this message
     *
     * @return the MessageData of this message
     */
    public MessageData getMessageData() {
        MessageData data = new MessageData();
        data.messageId = messageId.toString();
        data.messageText = messageText;
        data.senderId = senderId.toString();
        data.recipientId = recipientId.toString();
        data.timeSent = timeSent.toString();
        return data;
    }

    /**
     * The message data for a certain message in this conference system
     */
    public static class MessageData {
        public String messageId;

        public String messageText;
        public String senderId;
        public String recipientId;

        public String timeSent;
    }
}
