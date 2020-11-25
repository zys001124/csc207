package useCaseClasses;

import entities.Event;
import entities.Message;
import entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents an messageManager that can store/create messages sent by users of the conference
 */
public class MessageManager {

    private final List<Message> messages;

    /**
     * Create a new MessageManager with no messages stored
     */
    public MessageManager() {
        messages = new ArrayList<>();
    }

    /**
     * Create a new MessageManager with all messages contained in <messages>
     */
    public MessageManager(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Adds a message to the list of messages
     *
     * @param sender   the UUID of the sender of the message
     * @param receiver the UUID of the recieves of the message
     * @param message  the message that is sent
     */
    public void sendMessage(UUID sender, UUID receiver, String message) {

        messages.add(new Message(message, sender, receiver, UUID.randomUUID()));
    }

    public void addMessage(UUID sender, UUID receiver, String message, LocalDateTime timeSent, UUID messageId) {

        messages.add(new Message(message, sender, receiver, messageId, timeSent));
    }

    /**
     * gets a list of all the messages given in this conference system.
     *
     * @return a list of messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    public void messageAllAttendingEvent(String message, Event e, UUID sender) {

        for (UUID userId : e) {
            messages.add(new Message(message, sender, userId, UUID.randomUUID()));
        }
    }

    /**
     * takes in two users and checks to see if a message was sent between the two
     *
     * @param sender   UUID of the sender user
     * @param receiver UUID of teh reciever user
     * @return Boolean of if a message is sent between two senders
     */
    public boolean messageSentBy(UUID sender, UUID receiver) {
        for (Message message : messages) {
            if (message.getSenderId().equals(sender) &&
                    message.getRecipientId().equals(receiver)) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets an ordered list of the messages sent between two users and returns that list
     *
     * @param a1 the first user in the messaging exchange
     * @param a2 the second user in the messaging exchange
     * @return an ordered list of the messages sent between a1 and a2
     */
    public List<Message> messagesBetweenTwo(User a1, User a2) {
        List<Message> theMessages = new ArrayList<>();

        for (Message m : messages) {
            if ((m.getSenderId().equals(a1.getId()) && m.getRecipientId().equals(a2.getId())) || (m.getSenderId().equals(a2.getId()) && m.getRecipientId().equals(a1.getId()))) {
                theMessages.add(m);
            }
        }

        Collections.sort(theMessages);
        return theMessages;
    }
}
